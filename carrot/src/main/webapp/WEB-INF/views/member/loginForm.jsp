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
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
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
						<div class="caution hide flex-row justify-center">
							<i class="bi bi-exclamation-triangle"></i>
							<span>
								
							</span>
						</div>
					</li>
				</ul>
				<input type="submit" hidden> <%-- <input> 태그에서 엔터 입력시 submit 이벤트 발생 --%>
			</form>
		</li>
		<li class="flex-row justify-center">
			<input type="button" class="big point" id="login" value="로그인">
			<input type="button" class="big" value="회원 가입" onclick="location.href = 'registerForm.do';">
		</li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	// 바이트 길이 제한
	validateBytesLength({id:30, password:30});
	
	let login_btn = document.getElementById('login');
	let login = document.querySelector('.login form');
	
	// 로그인 버튼 클릭시 submit 이벤트 발생
	login_btn.addEventListener('click', function() {
		login.dispatchEvent(new Event('submit'));
	}, false);
	
	// 유효성 검증
	let id = document.getElementById('id');
	let password = document.getElementById('password');
	let caution = document.querySelector('.caution');
	
	login.addEventListener('submit', function(event) {
		let isValid = true;
		isValid = validateNN(id, caution, '아이디') && validateNN(password, caution, '비밀번호');
		if(!isValid) event.preventDefault();
	}, false);
	
	function validateNN(input, caution, label) {
		input.value = input.value.trim(); // 입력 필드의 양끝 공백 제거
		if(!input.value) { // 아무것도 입력하지 않은 경우
			let post = (label.charCodeAt(label.length-1) - '가'.charCodeAt(0)) % 28 > 0 ? '을' : '를';
			caution.querySelector('span').textContent = label + post + ' 입력하세요!';
			caution.classList.remove('hide'); // 경고 메시지 보이기
			caution.classList.add('called'); // 경고 메시지 강조
			input.focus(); // 현재 입력 필드에 포커스 설정
			return false;
		}
		return true;
	}
	
	document.addEventListener('keydown', function() { // 키로 입력하는 이벤트가
		if(event.target===id || event.target===password) { // id 또는 password에서 발생하면
			caution.classList.remove('called'); // 경고 메시지 강조 해제
		}
	}, false);
	
	document.addEventListener('focusout', function() { // 탭 키, 마우스 클릭 등으로 포커스 해제되는 이벤트가
		if(event.target===id || event.target===password) { // id 또는 password에서 발생하면
			if(!event.target.value.trim()) { // 아무것도 입력하지 않은 경우
				caution.classList.remove('hide'); // 경고 메시지 보이기
				caution.classList.add('called'); // 경고 메시지 강조
			}
			else caution.classList.add('hide'); // 경고 메시지 숨기기
		}
	}, false);
</script>
</body>
</html>