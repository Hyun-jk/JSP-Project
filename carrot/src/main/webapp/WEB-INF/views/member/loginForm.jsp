<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인/회원 가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>로그인/회원 가입</h2>
	<form id="login" action="login.do" method="post">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
			</li>
			<li>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="로그인">
			<input type="button" value="회원 가입" onclick="location.href = 'registerForm.do';">
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	validateSubmit('login');
	validateBytesLength({id:30, password:30});
</script>
</body>
</html>