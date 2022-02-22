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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
</head>
<body>
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp"/>
				<div id="main-content">
					<div>
						<h3>찜한 상품</h3>
					</div>			
					<c:if test="${count == 0}">
					<div class="result-display">
						찜한 상품이 없습니다
					</div>
					</c:if>
					
					<c:if test="${count>0}">
					<table id="sellList">
						<tr>
							<th><input type="checkbox"></th>
							<th>상품사진</th>
							<th>상품명</th>
							<th>가격</th>
							<th>거래지역</th>
							<th>비고</th>
						</tr>
					<c:forEach var ="myProduct" items="${list}">
						<tr>
							<td><input type="checkbox" value="선택"></td>
							<td><img src="${pageContext.request.contextPath}/upload/${myProduct.product.photo1}" width="100"></td>
							<td><a href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${myProduct.aproduct_num}">${myProduct.product.title}</a></td>
							<td><fmt:formatNumber value="${myProduct.product.price}"/>원</td>
							<td>${myProduct.member.address}</td>
							<td><input type="button" value="물품삭제하기"></td>
						</tr>
					</c:forEach>
					</table>
					</c:if>
				</div>
			</div>
		</div>	
		<div class="page-footer">
			<div class="align-center">
				${pagingHtml}
			</div>
		</div>	
	</div>
</body>
</html>