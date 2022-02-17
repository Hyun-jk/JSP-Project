<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<h4>찜한 상품</h4>
			<hr size="1" noshade width="100%">
		<c:if test="${count == 0}">
		<div class="result-display">
			찜한 상품이 없습니다
		</div>
		</c:if>
		<c:if test="${count>0}">
		<table>
			<tr>
				<th>상품사진</th>
				<th>상품명</th>
				<th>가격</th>
				<th>거래지역</th>
			</tr>
			<c:forEach var ="myProduct" items="${list}">
			<tr>
				<td><img src="${pageContext.request.contextPath}/upload/${myProduct.product.photo1}" width="100"></td>
				<td><a href="detail.do?aboard_num=${myProduct.aproduct_num}">${myProduct.product.title}</a></td>
				<td>${myProduct.product.price}</td>
				<td>${myProduct.member.address}</td>
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