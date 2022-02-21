<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<ul class="header">
	<li class="header-title">
		<a href="${pageContext.request.contextPath}/main/main.do">번개 맞은 당근 나라</a>
	</li>
	<li class="header-menu">
		<ul>
			<li>
				<c:if test="${!empty user_num}">
				<a href="${pageContext.request.contextPath}/member/logout.do">
					<div>로그아웃</div>
				</a>
				</c:if>
				<c:if test="${empty user_num}">
				<a href="${pageContext.request.contextPath}/member/loginForm.do">
					<div>로그인/회원 가입</div>
				</a>
				</c:if>
			</li>
			<li>
			<a class="header-who" href="${pageContext.request.contextPath}/member/myPage.do">
				<div>MY 페이지</div>
				<c:if test="${!empty user_num && !empty user_photo}">
				<img class="header-profile" src="${pageContext.request.contextPath}/upload/${user_photo}">
				</c:if>
				<c:if test="${!empty user_num && empty user_photo}">
				<img class="header-profile" src="${pageContext.request.contextPath}/images/face.png">
				</c:if>
			</a>
			</li>
			<c:if test="${!empty user_num && user_auth == 3}">
			<li>
			<a href="${pageContext.request.contextPath}/member/myPage.do">
				<div>관리자 페이지</div>
			</a>
			</li>
			</c:if>
			<li>
				<a class="header-chat" href="${pageContext.request.contextPath}/chat/chat.do">
					<c:if test="${empty user_num}">
					<i class="bi bi-chat-fill disabled"></i>
					</c:if>
					<c:if test="${!empty user_num}">
					<i class="bi bi-chat-fill"></i>
					</c:if>
				</a>
			</li>
		</ul>
	</li>
</ul>
<!-- header 끝 -->







