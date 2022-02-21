<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#delete_form').submit(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요!');
				$('#id').val('').focus();
				return false;
			}
			if($('#password').val().trim()==''){
				alert('비밀번호를 입력하세요!');
				$('#password').val('').focus();
				return false;
			}
			if($('#cpassword').val().trim()==''){
				alert('비밀번호 확인을 입력하세요!');
				$('#cpassword').val('').focus();
				return false;
			}
			if($('#password').val()!=$('#cpassword').val()){
				alert('비밀번호와 비밀번호 확인 불일치');
				$('#cpassword').val('').focus();
				return false;
			}
		});
		
		$('#password').keyup(function(){
			$('#cpassword').val('');
			$('#message_id').text('');
		});
			$('#cpassword').keyup(function(){
				if($('#password').val()==$('#cpassword').val()){
					$('#message_id').text('비밀번호 일치');
				}else{
					$('#message_id').text('');
				}
			});	
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	 <jsp:include page="/WEB-INF/views/common/side.jsp"/> 
	<h2>회원 탈퇴</h2>
	<form action="deleteUser.do" method="post" id="delete_form">
		<table>
			<tr>
				<th><label for="id">아이디</label></th>
				<td><input type="text" name="id" id="id" maxlength="12"></td>
			</tr>
			<tr>
				<th><label for="password">비밀번호</label></th>
				<td><input type="password" name="password" id="password" maxlength="12"></td>
			</tr>
			<tr>
				<th><label for="cpassword">비밀번호 확인</label></th>
				<td><input type="password" name="cpassword" id="cpassword" maxlength="12">
				<span id="message_id"></span></td>
			</tr>
		</table>
		<div class="align-center">
			<input type="submit" value="회원탈퇴">
			<input type="button" value="MyPage" onclick="location.href='myPageDetail.do'">
		</div>
		
	</form>
	</div>

</body>
</html>