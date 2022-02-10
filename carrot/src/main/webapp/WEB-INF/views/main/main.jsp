<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<h2>메인 화면</h2>
	<form id="search_form" action="list.do" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<c:forEach var="i" items="${category}">
					<option value="${i.category}" <c:if test="${param.keyfield==i.category}">selected</c:if>>${i.name}</option>
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
	<div class="list-space align-right">
		<input type="button" value="목록" onclick="location.href = 'main.do';">
		<input type="button" value="물품 등록" onclick="location.href = '${pageContext.request.contextPath}/product/writeForm.do';" <c:if test="${empty user_num}">disabled</c:if>>
	</div>
	<c:if test="${totalCount==0}">
	<div class="result-display">
		검색된 물품이 없습니다.
	</div>
	</c:if>
	<c:if test="${totalCount>0}">

	<div class="align-center">
		${pagingHtml}
	</div>
	</c:if>
</div>
</body>
</html>