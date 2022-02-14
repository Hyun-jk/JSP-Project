<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <h2>중고 거래 매물</h2>
<!-- 동네 선택, 카테고리 선택, 검색 시작 -->
	<form id="search" action="main.do" method="get">
		<ul class="search-main">
			<li>
				<select name="address">
					<c:forEach var="addr" items="${listAddress}">
					<option value="${addr.address}" <c:if test="${param.address==addr.address}">selected</c:if>>${addr.address}</option>
					</c:forEach>
				</select>
			</li>
			<li>
				<select name="category">
					<c:forEach var="cat" items="${listCategory}">
					<option value="${cat.category}" <c:if test="${param.category==cat.category}">selected</c:if>>${cat.name}</option>
					</c:forEach>
				</select>
			</li>
			<li>
				<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form>
<!-- 동네 선택, 카테고리 선택, 검색 끝 -->
	<div class="list-space align-right">
		<input type="button" value="목록" onclick="location.href = 'main.do';">
		<input type="button" value="물품 등록" onclick="location.href = '${pageContext.request.contextPath}/product/writeForm.do';" <c:if test="${empty user_num}">disabled</c:if>>
	</div>
<!-- 목록 출력 시작 -->
	<c:if test="${count==0}">
	<div class="result-display">
		검색된 물품이 없습니다.
	</div>
	</c:if>
	<c:if test="${count>0}">
	<ul class="list-main">
		<c:forEach var="product" items="${list}">
		<li>
			<a href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${product.aproduct_num}">
			<img src="${pageContext.request.contextPath}/upload/${product.photo1}">
			<div class="title">${product.title}</div>
			<div class="price">
				<c:if test="${product.price==0}">
				나눔
				</c:if>
				<c:if test="${product.price>0}">
				<fmt:formatNumber value="${product.price}"/>원
				</c:if>
			</div>
			<div class="address">${product.address}</div>
			<div class="info">
				관심 ${product.likes} · 댓글 ${product.replies} · 채팅 ${product.chats}
			</div>
			</a>
		</li>
		</c:forEach>
	</ul>
	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
<!-- 목록 출력 끝 -->	
</div>
<script type="text/javascript">
	document.getElementsByName('address')[0].onchange = function() {
		document.getElementById('search').submit();
	}
	document.getElementsByName('category')[0].onchange = function() {
		document.getElementById('search').submit();
	}
</script>
</body>
</html>