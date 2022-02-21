<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<title>나의 매너 점수</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/side.jsp" />
		<h2>${userInfo.id}님의매너점수</h2>
		<div class="content-body">
			<div class="mypage-phototo">
				<h3>[${userInfo.id}]님의 프로필 사진</h3>
				<ul>
					<li><c:if test="${empty userInfo.photo}">
							<img src="${pageContext.request.contextPath}/images/face.png"
								width="300" height="250" class="my-photo">
						</c:if> <c:if test="${!empty userInfo.photo}">
							<img
								src="${pageContext.request.contextPath}/upload/${userInfo.photo}"
								width="300" height="250" class="user_photo">
						</c:if></li>
				</ul>
			</div>

			<div class="mypage-id">
				<h3>[${userInfo.id}]님의 정보</h3>
				<table id="mypage-id">
					<tr>
						<th>별명</th>
						<td>${userInfo.nickname}</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${userInfo.phone}</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${userInfo.email}</td>
					</tr>
					<c:if test="${empty mannerList.get(0).totalRate}">
						<tr>
							<th>매너점수</th>
							<td>표시할 점수가 없습니다.</td>
						</tr>
					</c:if>
					<c:if test="${!empty mannerList.get(0).totalRate}">
						<tr>
							<th>매너점수</th>
							<td><fmt:formatNumber value="${mannerList.get(0).totalRate}"
									pattern=".00" /></td>
						</tr>
					</c:if>
					<tr>
						<th>총거래수</th>
						<td>${sellProductCount}건</td>
					</tr>
					<tr>
						<th>받은 평가수</th>
						<td>${mannerCount}건</td>
					</tr>
				</table>
				<br>
				<br>
				<br>
			</div>
		</div>
		<div class="align-left">
			<ul>
			<li><h2>거래후기</h2></li>
				<c:forEach var="buyer" items="${mannerList}">
					<c:if test="${empty buyer.member.photo}">
						<li><img
							src="${pageContext.request.contextPath}/images/face.png"
							width="50" height="60" class="my-photo"> <span>${buyer.member.nickname}</span>
							: ${buyer.review}</li>
					</c:if>
					<c:if test="${!empty buyer.member.photo}">
						<li><img
							src="${pageContext.request.contextPath}/upload/${buyer.member.photo}"
							width="50" height="60" class="user_photo"> ${buyer.review}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>









