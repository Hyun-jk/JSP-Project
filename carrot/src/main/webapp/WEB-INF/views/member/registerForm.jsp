<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>회원 가입</h2>
	<form id="register" action="register.do" method="post">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
				<input type="button" value="아이디 중복 검사" onclick="validateId();">
				<span id="notice_id"></span>
			</li>
			<li>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password">
			</li>
			<li>
				<label for="password_re">비밀번호 확인</label>
				<input type="password" id="password_re">
				<span id="notice_password"></span>
			</li>
			<li>
				<label for="name">이름</label>
				<input type="text" name="name" id="name">
			</li>
			<li>
				<label for="nickname">별명</label>
				<input type="text" name="nickname" id="nickname">
			</li>
			<li>
				<label for="age">생년월일</label>
				<input type="date" name="age" id="age">
			</li>
			<li>
				<label>전화번호</label>
				<select name="phone" id="area_code">
					<option>010</option>
					<option>070</option>
					<option>직접 입력</option>
				</select>
					- <input type="text" name="phone" id="phone2" size="4">
					- <input type="text" name="phone" id="phone3" size="4">
			</li>
			<li>
				<label for="address">동네</label>
				<input type="text" name="address" id="address">
				<input type="button" value="동네 찾기" onclick="">
			</li>
			<li>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="회원 가입">
			<input type="button" value="홈으로" onclick="location.href = '${pageContext.request.contextPath}/main/main.do';">
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	// 바이트 길이 제한 처리
	validateBytesLength({
		id:30,
		password:30,
		name:30,
		nickname:30,
		phone2:4,
		phone3:4,
		address:90,
		email:50
	});
	
	// 휴대전화번호 입력 칸 동적 처리
	let area_code = document.getElementById('area_code');
	area_code.onchange = function() {
		if(this.value=='직접 입력') {
			let area_code_text = document.createElement("input"); // 태그 생성
			area_code_text.type = 'text';
			area_code_text.name = 'phone'; // 서버 전송용 식별자 부여
			area_code_text.id = 'phone1'; // 이벤트 연결용 식별자 부여
			area_code_text.size = 3;
			this.parentNode.insertBefore(area_code_text, this.nextSibling);
			validateBytesLength({phone1:3}); // 국번이므로 길이를 3자로 제한
		}
		else if(this.nextSibling.id=='phone1') {
			this.nextSibling.remove(); // 태그 삭제
		}
	}
	
	// 아이디 중복 검사 처리
	function validateId() {
		
	}
	
	// 비밀번호 확인 처리
	let password = document.getElementById('password');
	let password_re = document.getElementById('password_re');
	let notice_password = document.getElementById('notice_password');
	password.addEventListener('keyup', function() {
		notice_password.textContent = '';
		password_re.value = '';
	}, false);
	password_re.addEventListener('keyup', function() {
		password_re.value = password_re.value.trim();
		if(!password_re.value) { // 아무것도 입력하지 않은 경우
			notice_password.textContent = '';
		}
		else if(password.value!=password_re.value) { // 비밀번호와 비밀번호 확인이 불일치하는 경우
			notice_password.textContent = '비밀번호 불일치!';
			notice_password.style.color = 'red';
		}
		else {
			notice_password.textContent = '비밀번호 일치';
			notice_password.style.color = 'blue';
		}
	}, false);
	
	// 유효성 검증
	document.getElementById('register').onsubmit = function() {
		let isValid = true;
		
		// Not Null 여부 처리
		isValid = validateNotNull(event);
		
		// 비밀번호와 비밀번호 확인 일치 여부 처리
		if(password.value!=password_re.value && isValid) {
			alert('비밀번호와 비밀번호 확인이 불일치합니다!');
			password_re.value = '';
			password_re.focus();
			isValid = false;
		}
		
		return isValid;
	}
</script>
</body>
</html>