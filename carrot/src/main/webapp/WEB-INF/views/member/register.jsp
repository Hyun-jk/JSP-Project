<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="register">
		<div class="modal">
			<div class="modal-content flex-column">
				<i class="bi bi-person-check-fill"></i>
					<span>
					</span>
				<input type="button" class="point" value="확인">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	let modal = document.querySelector('.modal');
	openModal(modal, '회원 가입이 완료되었습니다!');

	// 모달 닫는 이벤트
	document.addEventListener('click', function(event) { // 동적 이벤트 바인딩
		if(event.target===modal || event.target==modal.querySelector('input')) { // 모달 배경 영역 또는 확인 버튼을 클릭하면
			modal.classList.remove('show'); // 모달 닫기
			location.href = '${pageContext.request.contextPath}/main/main.do';
		}
	}, false);
</script>
</body>
</html>