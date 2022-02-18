<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function (){
		$('#password_form').submit(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요!');
				$('#id').val('').focus();
			return false;
		}
		if($('#origin_password').val().trim()==''){
			alert('현재 비밀번호를 입력하세요!');
			$('#origin_password').val('').focus();
		     return false;
		}
		if($('#password').val().trim()==''){
			alert('새로운 비밀번호를 입력하세요!');
			$('#password').val('').focus();
			return false;
		}
		if($('#password').val().trim()==''){
			alert('새로운 비밀번호 확인을 입력하세요!');
			$('#password').val('').focus();
			return false;
		}
		if($('#password').val()!=$('#cpassword').val()){
			alert('새비밀번호와 새비밀번호 확인이 불일치');
			$('#cpassword').val('').focus();
			return false;
		}
	});//end of submit
	
	//
	$('#password').keyup(function(){
		$('#cpassword').val('');
		$('#message_cpassword').text('');
	});
	
	//
	$('#cpassword').keyup(function(){
		if($('#password').val()==$('#cpassword').val()){
			$('#message_cpassword').text('새비밀번호 일치');
		
		}else{
			$('#message_cpassword').text('');
		}
	});
});
</script>
</head>
<body> 
<jsp:include page="/WEB-INF/views/common/side.jsp"/>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>비밀번호 수정</h2>
	<form action="modifyPassword.do" method="post" id="password_form">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" maxlength="12">
			</li>
			<li>
				<label for="origin_password">현재 비밀번호</label>
				<input type="password" name="origin_password" id="origin_password" maxlength="12">
			</li>
			<li>
				<label for="password">새로운 비밀번호</label>
				<input type="password" name="password" id="password" maxlength="12">
			</li>
			<li>
				<label for="cpassword">새로운 비밀번호 확인</label>
				<input type="password" name="cpassword" id="cpassword" maxlength="12">
				<span id="message_cpassword"></span>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="비밀번호 수정">
		</div>
	</form>
</div>
</body>
</html>