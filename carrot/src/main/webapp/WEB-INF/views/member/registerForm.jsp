<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<form id="register" action="register.do" method="post">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
			</li>
			<li>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password">
			</li>
			<li>
				<label for="name">이름</label>
				<input type="text" name="name" id="name">
			</li>
			<li>
				<label for="nickname">별명</label>
				<input type="text" name="nickname" id="nickname">
			</li>
			<li>
				<label for="age">생년월일</label>
				<input type="date" name="age" id="age">
			</li>
			<li>
				<label for="phone">전화번호</label>
				<input type="text" name="phone" id="phone">
			</li>
			<li>
				<label for="address">동네</label>
				<input type="text" name="address" id="address">
			</li>
			<li>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="회원 가입">
			<input type="button" value="홈으로" onclick="location.href = '${pageContext.request.contextPath}/main/main.do';">
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	validateSubmit('register');
	validateBytesLength({
		id:30,
		password:30
	});
</script>
</body>
</html>