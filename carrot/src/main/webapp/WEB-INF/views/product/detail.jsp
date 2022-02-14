<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 정보 : ${product.title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<ul class="detail">
		<li>
			<img src="${pageContext.request.contextPath}/upload/${product.photo1}">
		</li>
		<li class="seller">
			<div class="who">
				<c:if test="${empty seller.photo}">
				<img src="${pageContext.request.contextPath}/images/face.png">
				</c:if>
				<c:if test="${!empty seller.photo}">
				<img src="${pageContext.request.contextPath}/upload/${seller.photo}">
				</c:if>	
				<div>		
					<div>${seller.nickname}</div>
					<div>${seller.address}</div>
				</div>
			</div>
			<div class="manner">
				<div>
					<c:if test="${empty seller.rate}">
					정보가 없습니다.
					</c:if>
					<c:if test="${!empty seller.rate}">
						${seller.rate}
					</c:if>
				</div>
				<div>매너 평점</div>
			</div>
		</li>
		<hr>
		<li>
			<div class="bold">${product.title}</div>
			<div class="gray"><a>${category.name}</a> · ${product.reg_date}</div>
			<div class="bold"><fmt:formatNumber value="${product.price }"/>원</div>
			<div>${product.content}</div>
			<br>
			<div class="gray"><a>댓글 ${product.replies}</a> · 채팅 ${product.chats} · 관심 ${product.likes}</div>
		</li>
		<hr>
	</ul>
</div>
</body>
</html>