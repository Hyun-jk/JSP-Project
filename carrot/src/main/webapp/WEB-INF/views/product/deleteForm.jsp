<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 정보 삭제</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
	<h2>상품 정보 삭제</h2>
	<p class="align-center">
		<span>정말 삭제하시겠습니까?</span>
	</p>
	<form action="delete.do" method="post">
	<input type="hidden" name="aproduct_num" value="${aproduct_num}">
		<div class="align-center">
			<input type="submit" value="삭제하기">
		</div>
	</form>
</div>
</body>
</html>