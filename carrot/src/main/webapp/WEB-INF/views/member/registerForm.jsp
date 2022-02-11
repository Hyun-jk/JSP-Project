<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
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
				<span id="duplicated" class="caution"></span>
				<br><span id="word_only" class="caution"><i class="bi bi-exclamation-triangle"></i>
				4~30자의 영문자, 숫자만 사용 가능</span>
			</li>
			<li>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password">
				<span id="contain_chars" class="caution"></span>
				<br><span id="wrong_chars" class="caution"><i class="bi bi-exclamation-triangle"></i>
				6~30자의 영문자, 숫자, 특수문자 !@#$%^&*만 사용 가능</span>
			</li>
			<li>
				<label for="password_re">비밀번호 확인</label>
				<input type="password" id="password_re">
				<span id="identical" class="caution"></span>
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
				<input type="email" name="email" id="email" class="nullable">
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

	// 아이디 처리
	let id = document.getElementById('id');
	let duplicated = document.getElementById('duplicated');
	let isValidId = false;
	// 아이디 입력 제한	
	id.addEventListener('keydown', validateChars, false);
	// 아이디 중복 검사
	id.addEventListener('blur', function() {
		if(!id.value) {
			isValidId = false;
			duplicated.textContent = '';
			return isValidId;
		}
		
		$.ajax({
			url:'checkId.do',
			type:'post',
			data:{id:id.value},
			dataType:'json',
			cache:false,
			timeout:10000,
			success:function(param) {
				if(param.result=='idNotFound') {
					isValidId = true;
					duplicated.textContent = '사용 가능한 아이디';
					duplicated.style.color = 'blue';
				}
				else if(param.result=='idDuplicated') {
					isValidId = false;
					duplicated.textContent = '중복된 아이디';
					duplicated.style.color = 'red';
				}
				else {
					alert('아이디 중복 검사시 오류 발생!');
					isValidId = false;
					duplicated.textContent = '';
				}
			},
			error:function() {
				alert('네트워크 오류 발생!');
				isValidId = false;
				duplicated.textContent = '';
			}
		}); // end of ajax
	}, false); // end of addEventListener
	
	// 비밀번호 처리
	let password = document.getElementById('password');
	let password_re = document.getElementById('password_re');
	let isValidPassword = false;
	// 비밀번호 입력 제한
	password.addEventListener('keydown', validateChars, false);
	password.addEventListener('blur', hasSpecialChars, false);
	// 비밀번호와 비밀번호 확인 대조
	password.addEventListener('keyup', checkPassword, false);
	password_re.addEventListener('keyup', checkPassword, false);	
	
	// 유효성 검증
	document.getElementById('register').onsubmit = function() {
		let isValid = true;
		
		// Not Null 여부 처리
		isValid = validateNotNull(event);

		// 아이디 및 비밀번호 최소 길이 처리
		if(id.value.length<4 && isValid) {
			document.getElementById('word_only').style.color = 'red';
			document.getElementById('duplicated').textContent = '';
			id.focus();
			isValid = false;
		}
		if(password.value.length<6 && isValid) {
			document.getElementById('wrong_chars').style.color = 'red';
			document.getElementById('contain_chars').textContent = '';
			password.focus();
			isValid = false;		
		}
		
		// 아이디 중복 검사 처리
		if(!isValidId && isValid) {
			alert('이미 사용 중이거나 탈퇴한 아이디입니다!')
			id.focus();
			isValid = isValidId;
		}
		
		// 비밀번호 특수문자 포함 여부 처리
		if(!isValidPassword && isValid) {
			alert('비밀번호에 특수문자가 1개 이상 포함되어야 합니다!')
			password.focus();
			isValid = isValidPassword;
		}
		
		// 비밀번호와 비밀번호 확인 대조 처리
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