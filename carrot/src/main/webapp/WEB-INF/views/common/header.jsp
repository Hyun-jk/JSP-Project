<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<h1 class="align-center"><a href="${pageContext.request.contextPath}/main/main.do">번개 맞은 당근 나라</a></h1>
<div class="align-right">
	<c:if test="${!empty user_num && !empty user_photo }">
		<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
	</c:if>
	<c:if test="${!empty user_num && empty user_photo}">
		<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
	</c:if>
	<c:if test="${!empty user_num}">
		[<span>${user_id}</span>]
		<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
	</c:if>
	<c:if test="${empty user_num}">
		<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인/회원 가입</a>
	</c:if>
	<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
	<c:if test="${!empty user_num && user_auth == 3}">
		<a href="${pageContext.request.contextPath}/member/myPage.do">관리자 페이지</a>
	</c:if>
	<a href="${pageContext.request.contextPath}/chat/chat.do"><i class="bi bi-chat-fill"></i>채팅</a>
</div>
<!-- header 끝 -->







