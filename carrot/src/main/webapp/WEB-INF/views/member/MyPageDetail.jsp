 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPageDetail</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		let photo_path = $('.my-photo').attr('src');
		let my_photo;
		
		$('#photo_btn').click(function(){
			$('#photo_choice').show();
			$(this).hide();
		});
		
		$('#photo_reset').click(function(){
			
			$('.my-photo').attr('src',photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show();
		});
		
		//이미지 선택 및 이미지 미리보기
		$('#photo').change(function(){
			my_photo = this.files[0];
			if(!my_photo){
				$('.my-photo').attr('src',photo_path);
				return;
			}
			
			if(my_photo.size > 1024*1024){
				alert('1MB까지만 업로드 가능!');
				photo.value='';
				return;
			}
			
			var reader = new FileReader();
			reader.readAsDataURL(my_photo);
			
			reader.onload=function(){
				$('.my-photo').attr('src',reader.result);
			};
		});
		
		$('#photo_submit').click(function(){
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			$.ajax({
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false,
				processData:false,
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('프로필 사진이 수정되었습니다.');
						photo_path=$('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
						
					}else{
						alert('파일 전송 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
					
				}
			});
		
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class=page-main>	
	<jsp:include page="/WEB-INF/views/common/side.jsp"/> 
	    
	    <h2>회원정보</h2>	
	    <div class = "mypage-phototo">
		
		<h3>프로필 사진</h3> 
		<ul>
			<li>
				<c:if test="${empty member.photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" 
					          width="300" height="300" class="my-photo">
				</c:if>
				<c:if test="${!empty member.photo }">
					<img src="${pageContext.request.contextPath}/upload/${member.photo}"
					          width="300" height="300" class="user_photo">
				</c:if>
			</li>
			<li>
				<div class="align-center">
					<input type="button" value="프로필 사진 수정" id="photo_btn">
				</div>
				<div id="photo_choice" style="display:none;">
					<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
					<input type="button" value="설정하기" id="photo_submit">
					<input type="button" value="취소하기" id="photo_reset">
				</div>
			</li>
		</ul>
		<h3>비밀번호 수정</h3>
		<ul>
			<li>
				<input type="button" value="비밀번호 수정"
				onclick="location.href='modifyPasswordForm.do'">
			</li>
		</ul>	
		</div>
	<div class="mypage-id">
	<h3>나의 정보</h3>
	<table id="mypage-id">
			<tr><th>이름</th><td>${member.name}</td></tr>
		    <tr><th>별명</th><td>${member.nickname}</td></tr>
		    <tr><th>연락처</th><td>${member.phone}</td></tr>
		    <tr><th>이메일</th><td>${member.email}</td></tr>
		    <tr><th>동네</th><td>${member.address}</td></tr>
		    <tr><th>관심동네</th><td>${member.address_favor}</td></tr>
		    <tr><th>생년월일</th><td>${member.age}</td></tr>
		    <tr><th>매너점수</th><td>${member.rate}</td></tr>
		    <tr><th>가입일</th><td>${member.reg_date}</td></tr>
		</table>	
				<input type="button" value="나의 정보 수정"
				onclick="location.href='modifyUserForm.do'">
  </div></div>
</body>
</html>