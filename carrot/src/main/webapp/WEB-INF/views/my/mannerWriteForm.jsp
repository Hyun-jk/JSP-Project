<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매너점수 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<form id="login" action="MannerWrite.do" method="post">
		<ul>
			<li>
				<h2>판매자님과의 거래가 어떠셨나요? 평가해주세요!</h2>
				<input type="checkbox" name="rate" value="1">1점
				<input type="checkbox" name="rate" value="2">2점
				<input type="checkbox" name="rate" value="3">3점
				<input type="checkbox" name="rate" value="4">4점
				<input type="checkbox" name="rate" value="5">5점
			</li>
			<li>
				<h2>판매자님께 따뜻한 후기를 남겨보세요!</h2>
				<textarea rows="10" cols="50" name="review"></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="저장하기">
		</div>
	</form>
</div>
</body>
</html>