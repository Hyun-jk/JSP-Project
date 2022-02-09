<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${auth<2}">
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>로그인 정보</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
	</head>
	<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<h2>회원 정보</h2>
		<div class="result-display">
			<div class="align-center">
				<c:if test="${auth==1}">
				정지된 회원 아이디입니다.
				</c:if>
				<c:if test="${auth==0}">
				탈퇴한 회원 아이디입니다.
				</c:if>				
				<p>
				<input type="button" value="홈으로" onclick="location.href = '${pageContext.request.contextPath}/main/main.do';">
			</div>
		</div>
	</div>
	</body>
	</html>		
	</c:when>
	<c:otherwise>
	<script type="text/javascript">
		alert('아이디 또는 비밀번호가 불일치합니다!');
		history.go(-1);
	</script>
	</c:otherwise>
</c:choose>