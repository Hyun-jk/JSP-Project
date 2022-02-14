<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
 
 $(function(){
    let photo_path = $('.my-photo1').attr('src');//처음 화면에 보여지는 이미지 읽기
    let my_photo1;
    
    $('#photo1_btn').click(function(){
       $('#photo1_choice').show();
     
    });
    
    //이미지 선택 및 이미지 미리보기
    $('#photo1').change(function(){
       my_photo1 = this.files[0];
       if(!my_photo1){
          $('.my-photo1').attr('src',photo_path);
           return;
       }
       
       if(my_photo1.size > 1024*1024){
          alert('1MB까지만 업로드 가능!');
          photo.value = '';
          return;
       }
       
       var reader = new FileReader();
       reader.readAsDataURL(my_photo1);
       
       reader.onload=function(){
          $('.my-photo1').attr('src',reader.result);
       };
    });
      

	    $(function() {
		$('#write_form').submit(function() {
			if ($('#title').val().trim() == '') {
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if ($('#price').val() == '') {
				alert('가격을 입력하세요!');
				$('#price').focus();
				return false;
			}
			if ($('#category').val() == '') {
				alert('카테고리를 설정하세요!');
				$('#category').focus();
				return false;
			}
			if ($('#photo1').val() == '') {
				alert('사진을 추가 해보세요!');
				$('#photo1').focus();
				return false;
			}
			if ($('#content').val().trim() == '') {
				alert('상품설명을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
			});			
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<h2>상품 등록</h2>
		<form action="write.do" method="post" enctype="multipart/form-data"
			id="write_form">
			<ul>
				<li><label for="photo1">상품이미지*</label> 
				<c:if test="${empty member.photo1}">
						<img src="${pageContext.request.contextPath}/images/photo.png"
							width="100" height="100" class="my-photo1">
					    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    &nbsp;&nbsp;<input type="file" name="photo1" id="photo1" accept="image/gif,image/png,image/jpeg">	
					</c:if></li>	
				<li>
				    <label for="photo2">상품이미지2</label> 
					<input type="file" name="photo2" id="photo2" accept="image/gif,image/png,image/jpeg">	
				</li>	
				<li>
				    <label for="photo3">상품이미지3</label> 
					<input type="file" name="photo3" id="photo3" accept="image/gif,image/png,image/jpeg">	
				</li>	
				<li>
				    <label for="photo4">상품이미지4</label> 
					<input type="file" name="photo4" id="photo4" accept="image/gif,image/png,image/jpeg">	
				</li>	
				<li>
				    <label for="photo5">상품이미지5</label> 
					<input type="file" name="photo5" id="photo5" accept="image/gif,image/png,image/jpeg">	
				</li>	
				<li><label for="title">제목*</label> <input type="text"
					name="title" id="title" maxlength="10"></li>
				<li><label for="price">가격*</label> <input type="number"
					name="price" id="price" min="1" max="99999999"></li>
				<li><label for="category">카테고리*</label> <select name="category">
						<option value="1">디지털기기</option>
						<option value="2">생활/가전</option>
						<option value="3">남성의류/잡화</option>
						<option value="4">여성의류/잡화/미용</option>
						<option value="5">유아동/유아도서</option>
						<option value="6">스포츠</option>
						<option value="7">생활/가구</option>
				</select></li>
				<li><label for="content">상품설명*</label> <textarea name="content"
						id="content" cols="30" rows="5"></textarea></li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록하기">
			</div>
		</form>
	</div>
</body>
</html>


