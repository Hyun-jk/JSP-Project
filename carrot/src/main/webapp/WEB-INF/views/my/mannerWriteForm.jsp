<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매너점수 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.carousel.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dain.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
		<form id="login" action="MannerWrite.do" method="post">
		<ul><br><br>
		     <div class="manner flex-column justify-end align-end">
					<div class="manner-stars">
						<i class="bi bi-star-fill"></i>
						<i class="bi bi-star-fill"></i>
						<i class="bi bi-star-fill"></i>
						<i class="bi bi-star-fill"></i>
						<i class="bi bi-star-fill"></i>
					</div>
			  <div class= "gray underline text3"><h3>아래 점수를 눌러 판매자님과의 거래를 평가해 주세요</h3></div>
			  <br><br>
			<li><div class="text3">
			<i class="bi bi-emoji-frown"></i>별로에요 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<i class="bi bi-emoji-smile"></i>좋아요!<br>
			<div class="text3"> <-----&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-----></div>
			<br> 
			    <div class="manner flex-column justify-end align-end">
				<input type="checkbox" name="rate" value="1">1점
				<input type="checkbox" name="rate" value="2">2점
				<input type="checkbox" name="rate" value="3">3점
				<input type="checkbox" name="rate" value="4">4점
				<input type="checkbox" name="rate" value="5">5점
				</div>
				</div>
			</li>
			<br>
			<br>
			<li><div class="text3">
				<div class= "gray underline" ><h3>판매자님께 따뜻한 후기를 남겨보세요!</h3></div></div>
				<textarea rows="6" cols="30" name="review"></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input class="buttonD" type="submit" value="저장하기" >
			<br><br>
		</div>
	</form>
</div>
</body>
</html>