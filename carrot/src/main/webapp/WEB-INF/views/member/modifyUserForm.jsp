<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#modify_form').submit(function() {
			if ($('#nickname').val().trim() == '') {
				alert('닉네임을 입력하세요!');
				$('#nickname').val('').focus();
				return false;
			}
			if ($('#phone').val().trim() == '') {
				alert('전화번호를 입력하세요!');
				$('#phone').val('').focus();
				return false;
			}
			if ($('#email').val().trim() == '') {
				alert('이메일을 입력하세요!');
				$('#email').val('').focus();
				return false;
			}
			if ($('#address').val().trim() == '') {
				alert('주소를 입력하세요!');
				$('#address').val('').focus();
				return false;
			}
		})
	});
</script>
</head>
<body>
<div>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	  <div id="My-content">
	    <jsp:include page="/WEB-INF/views/common/side.jsp"/> 
	</div></div>
	<h2>회원정보 수정</h2>
	<form action="modifyUser.do" method="post" id="modify_form">
	<ul>
		<li>
		<label for="nickname">닉네임</label>
		<input type="text" name="nickname" id="nickname" value="${member.nickname}" maxlength="10">
		</li>
		<li>
			<label for="phone">전화번호</label>
			<input type="text" name="phone" id="phone" value="${member.phone}" maxlength="15">
		</li>
		<li>
			<label for="email">이메일</label>
			<input type="email" name="email" id="email" value="${member.email}" maxlength="50">
		</li>
		<li>
			<label for="address">주소</label>
			<input type="text" name="address" id="address" value="${member.address}" maxlength="30">
		</li>
		<li>
			<label for="address_favor">선호지역</label>
			<input type="text" name="address_favor" id="address_favor" value="${member.address_favor}" maxlength="30">
		</li>
	</ul>
	<div>
		<input type="submit" value="수정">
	</div>
	</form>
</body>
</html>