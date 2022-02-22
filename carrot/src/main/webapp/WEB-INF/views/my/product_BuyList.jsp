<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매한 상품</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/side.jsp"/>
			<h2>내가 구매한 상품</h2>
			<div class="sell">	
		<c:if test="${count == 0}">
		<div class="result-display1">
			구매한 상품이 없습니다.
		</div>
		</c:if>
		<c:if test="${count>0}">
		<table id="sellList">
			<tr>
				<th><input type="checkbox">선택</th>
				<th>상품사진</th>
				<th>상품명</th>
				<th>가격</th>
				<th>상태</th>
				<th>거래완료일</th>
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
				<td><c:if test="${sell.complete ==1}">구매완료</c:if></td>
				<td>${sell.modify_date }</td>
			</tr>		
			</c:forEach>
		</table>
		<div class="align-center">
		<input type="button" value="목록" onclick="location.href='memberProduct.do?complete=1'">
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
			<div class="pagenum">
				${pagingHtml}
			</div>
		</c:if>
			</div>	</div>
</body>
</html>