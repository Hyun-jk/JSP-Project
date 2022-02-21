<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인/회원 가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<ul class="login flex-column">
		<li class="title flex-row justify-center">
			로그인/회원 가입
		</li>
		<li>
			<form action="login.do" method="post">
				<ul class="flex-column">
					<li>
						<div class="input-box flex-row">
							<i class="bi bi-person-circle"></i>
							<input type="text" name="id" id="id" placeholder="아이디">
						</div>
					</li>
					<li>
						<div class="input-box flex-row">
							<i class="bi bi-lock-fill"></i>
							<input type="password" name="password" id="password" placeholder="비밀번호">
						</div>
					</li>
					<li>
						<div class="caution hide">
							<i class="bi bi-exclamation-triangle"></i>
							<span>
								
							</span>
						</div>
					</li>
				</ul>
				<input type="submit" hidden> <%-- <input> 태그에서 엔터 입력시 submit 이벤트 발생 --%>
			</form>
			<div class="modal">
				<div class="modal-content flex-column">
					<i class="bi bi-exclamation-triangle-fill"></i>
					<span>
					</span>
					<input type="button" class="point" value="확인">
				</div>
			</div>
		</li>
		<li class="buttons flex-row justify-center">
			<input type="button" class="big point" id="login" value="로그인">
			<input type="button" class="big" value="회원 가입" onclick="location.href = 'registerForm.do';">
		</li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// 유효성 검증 및 ajax 통신용 변수 선언
	let login_btn = document.getElementById('login');
	let login = document.querySelector('.login form');
	let id = document.getElementById('id');
	let password = document.getElementById('password');
	let caution = document.querySelector('.caution');
	let modal = document.querySelector('.modal');
	
	// 바이트 길이 제한
	validateBytesLength({id:30, password:30});
	
	// <input> 태그에서 엔터 입력시 로그인
	login.addEventListener('submit', function(event) {
		event.preventDefault(); // 기본 이벤트 제거
		getLogin();
	}, false);
	// 로그인 버튼 클릭시 로그인
	login_btn.addEventListener('click', function() {
		getLogin();
	}, false);
	
	// 경고 메시지 UI 처리
	document.addEventListener('keydown', function() { // 키로 입력하는 이벤트가
		if(event.target===id || event.target===password) { // id 또는 password에서 발생하면
			caution.classList.remove('called'); // 경고 메시지 강조 해제
		}
	}, false);
	document.addEventListener('focusout', function() { // 탭 키, 마우스 클릭, blur() 등으로 포커스 해제되는 이벤트가
		if(event.target===id || event.target===password) { // id 또는 password에서 발생하면
			validateNN(caution, event.target);
		}
	}, false);
	
	// 모달 닫는 이벤트
	document.addEventListener('click', function(event) { // 동적 이벤트 바인딩
		if(event.target===modal || event.target==modal.querySelector('input')) { // 모달 배경 영역 또는 확인 버튼을 클릭하면
			modal.classList.remove('show'); // 모달 닫기
			id.focus();
		}
	}, false);
	
	// 로그인 처리하는 함수 정의
	function getLogin() {
		if(!validateNN(caution, id)) return;
		if(!validateNN(caution, password)) return;
		
		$.ajax({
			url:'login.do',
			type:'post',
			data:{
				id:id.value,
				password:password.value
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='success') {
					location.href = '${pageContext.request.contextPath}/main/main.do';
				}
				else if(param.result=='invalid') {
					if(param.auth===0) openModal(modal, '탈퇴한 회원 아이디입니다!', id, password);
					else if(param.auth===1) openModal(modal, '정지된 회원 아이디입니다!', id, password);
					else openModal(modal, '아이디 또는 비밀번호가 불일치합니다!', id, password);
				}
				else {
					openModal(modal, '로그인하는 데 실패했습니다!', id, password);
				}
			}, // end of success
			error:function() {
				openModal(modal, '네트워크 오류가 발생했습니다!', id, password);
			}
		}); // end of ajax
	} // end of getLogin
</script>
</body>
</html>