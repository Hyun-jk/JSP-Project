<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찜한 상품</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp"/>
		<div id="My-content">
			<h2>판매중인 상품</h2>
			<hr size="1" noshade width="100%">
		<c:if test="${count == 0}">
		<div class="result-display">
			판매중인 상품이 없습니다.
		</div>
		</c:if>
		<c:if test="${count>0}">
		<table>
			<tr>
				<th><input type="checkbox">선택</th>
				<th>상품사진</th>
				<th>상품명</th>
				<th>가격</th>
				<th>상태</th>
				<th>등록일</th>
			</tr>
			<c:forEach var="sell" items="${list}">
			<tr>
				<td><input type="checkbox"></td>
				<td>
					<a href="location.href='${pageContext.request.contextPath}/'"></a>
					<img src="${pageContext.request.contextPath}/upload/${sell.photo1}" width="50">
				</td>
				<td>${sell.title}</td>
				<td><fmt:formatNumber value="${sell.price}"/>원</td>
				<td><c:if test="${sell.complete ==0}">판매중</c:if></td>
				<td>${sell.reg_date }</td>
			</tr>		
			</c:forEach>
		</table>
		</c:if>
			<div class="align-center">
				${pagingHtml}
			</div>
		</div>
	</div>
	
</body>
</html>