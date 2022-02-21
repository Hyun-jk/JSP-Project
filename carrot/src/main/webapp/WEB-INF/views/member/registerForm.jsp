<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<ul class="register flex-column">
		<li class="title flex-row justify-center">
			회원 가입
		</li>
		<li>
			<form action="register.do" method="post">
				<ul class="flex-column">
					<li>
						<div>
							<label for="id">아이디</label>
							<input type="text" name="id" id="id" placeholder="4~30자의 영문자, 숫자를 사용하세요">
						</div>
					</li>
					<li>
						<div>
							<label for="password">비밀번호</label>
							<input type="password" name="password" id="password" placeholder="6~30자의 영문자, 숫자와 특수문자 !@#$%^&*를 사용하세요">
						</div>
					</li>
					<li>
						<div>
							<label for="password_re">비밀번호 확인</label>
							<input type="password" id="password_re">
						</div>
					</li>
					<li>
						<div>
							<label for="name">이름</label>
							<input type="text" name="name" id="name">
						</div>
					</li>
					<li>
						<div>
							<label for="nickname">별명</label>
							<input type="text" name="nickname" id="nickname">
						</div>
					</li>
					<li>
						<div>
							<label>생년월일</label>
							<input type="text" name="age" id="year" placeholder="년(4자)">
							<select name="age" id="month">
								<option value="" selected disabled>월</option>
							</select>
							<input type="text" name="age" id="day" placeholder="일">
						</div>
					</li>
					<li>
						<div>
							<label>휴대전화번호</label>
							<select name="phone" id="area_code">
								<option>010</option>
								<option>070</option>
								<option>직접 입력</option>
							</select>
							<input type="text" name="phone" id="phone2">
						</div>
					</li>
					<li>
						<div>
							<label for="address">동네</label>
							<input type="text" name="address" id="address" readonly>
							<input type="button" value="동네 찾기" onclick="sample3_execDaumPostcode();">
						</div>
						<div class="search">
							<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
								<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
							</div>
						</div>
					</li>
					<li>
						<div>
							<label for="email">이메일</label>
							<input type="email" name="email" id="email" class="nullable">
						</div>
					</li>
				</ul>
				<input type="submit" hidden> <%-- <input> 태그에서 엔터 입력시 submit 이벤트 발생 --%>
			</form>
			<div class="caution hide">
				<i class="bi bi-exclamation-triangle"></i>
				<span>
					
				</span>
			</div>
			<div class="modal">
				<div class="modal-content flex-column">
					<i class="bi bi-exclamation-triangle-fill"></i>
					<span>
					</span>
					<input type="button" class="point" value="확인">
				</div>
			</div>
		</li>
		<li class="flex-row justify-center">
			<input type="button" class="big point" value="가입하기" id="register">
			<input type="button" class="big" value="홈으로" onclick="location.href = '${pageContext.request.contextPath}/main/main.do';">
		</li>
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript">
	// 유효성 검증 및 가입 처리를 위한 변수 선언
	let register_btn = document.getElementById('register');
	let register = document.querySelector('.register form');
	let inputs = document.querySelectorAll('form input:not([type="button"]):not([type="submit"]):not(.nullable),form select');
	let id = document.getElementById('id');
	let password = document.getElementById('password');
	let password_re = document.getElementById('password_re');
	let month = document.getElementById('month');
	let year = document.getElementById('year');
	let day = document.getElementById('day');
	let caution = document.querySelector('.caution');
	let modal = document.querySelector('.modal');
	
	// 태그에 클래스 부여
	let labels = document.getElementsByTagName('label');
	for(let i=0;i<labels.length;i++) {
		labels[i].classList.add('subtitle');
	}
	let firstdivs = document.querySelectorAll('form > ul > li > div:first-child');
	for(let i=0;i<firstdivs.length;i++) {
		firstdivs[i].classList.add('flex-row', 'justify-start');
	}
	
	// 월 목록 생성
	for(let i=1; i<13; i++) {
		let option = new Option(i);
		month.options[i] = option;
	}
	
	// 휴대전화번호 입력 칸 동적 처리
	let area_code = document.getElementById('area_code');
	area_code.onchange = function() {
		if(this.value=='직접 입력') {
			let area_code_text = document.createElement('input'); // 태그 생성
			area_code_text.type = 'text';
			area_code_text.name = 'phone'; // 서버 전송용 식별자 부여
			area_code_text.id = 'phone1'; // 이벤트 연결용 식별자 부여
			this.parentNode.insertBefore(area_code_text, this.nextSibling);
			validateBytesLength({phone1:3}); // 국번이므로 길이를 3자로 제한
		}
		else if(this.nextSibling.id=='phone1') {
			this.nextSibling.remove(); // 태그 삭제
		}
	}
	
	// 바이트 길이 제한 처리
	validateBytesLength({
		id:30,
		password:30,
		name:30,
		nickname:30,
		year:4,
		day:2,
		phone2:8,
		address:90,
		email:50
	});

	// <input> 태그에서 엔터 입력시 가입 처리
	register.addEventListener('submit', function(event) {
		getRegister(event);
	}, false);
	// 가입하기 버튼 클릭시 가입 처리
	register_btn.addEventListener('click', function() {
		getRegister();
	})
	
	// 모달 닫는 이벤트
	document.addEventListener('click', function(event) { // 동적 이벤트 바인딩
		if(event.target===modal || event.target==modal.querySelector('input')) { // 모달 배경 영역 또는 확인 버튼을 클릭하면
			modal.classList.remove('show'); // 모달 닫기
		}
	}, false);
	
	// 아이디 입력 필드에서 포커스를 해제하면 아이디 중복 검사 실행
	let isValidId;
	id.addEventListener('blur', function() {
		if(!validateNN(caution, id, true)) {
			isValidId = false;
			return isValidId; // 함수 실행 중단
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
					setCaution(caution, '사용 가능한 아이디입니다!', id, true);
					isValidId = true;
				}
				else if(param.result=='idDuplicated') {
					setCaution(caution, '중복된 아이디입니다!', id, true, true, true);
					isValidId = false;
				}
				else {
					openModal(modal, '아이디 중복 검사에 실패했습니다!', id);
					isValidId = false;
				}
			},
			error:function() {
				openModal(modal, '네트워크 오류가 발생했습니다!', id);
				isValidId = false;
			}
		}); // end of ajax
	}, false);
	
	// 아이디 입력 제한
	id.addEventListener('keyup', function(event) {
		validateChars(event, caution);
	}, false);
	
	// 비밀번호 입력 제한
	let isValidPassword = false;
	password.addEventListener('keyup', function(event) {
		validateChars(event, caution);
	}, false);
	
	// 비밀번호 특수문자 포함 여부 검사
	password.addEventListener('keyup', function(event) {
		isValidPassword = hasSpecialChars(event, caution);
	}, false);

	// 비밀번호와 비밀번호 확인 대조
	document.addEventListener('keyup', function(event) {
		if(event.target===password) {
			password.value = password.value.trim();
			password_re.value = '';
		}
		else if(event.target===password_re) {
			password_re.value = password_re.value.trim();
			if(!password_re.value) caution.classList.add('hide');
			else if(password.value!=password_re.value) setCaution(caution, '비밀번호 불일치!', password_re, true, true, true);
			else setCaution(caution, '비밀번호 일치', password_re, true, false, false);
		}
	}, false);
	
	// 가입 처리하는 함수 정의
	function getRegister(event) {
		let isValid = true; // 유효성 검증 결과를 보관할 변수 선언
		
		// 아이디 및 비밀번호 최소 길이 처리
		if(id.value.length<4 && isValid) {
			setCaution(caution, id.placeholder, id, true, true, true);
			isValid = false;
		}
		if(password.value.length<6 && isValid) {
			setCaution(caution, password.placeholder, password, true, true, true);
			isValid = false;
		}

		// Not Null 여부 검사
		for(let i=0;i<inputs.length;i++) {
			if(!isValid) break;
			isValid = validateNN(caution, inputs[i], true);
		} // end of for

		// 아이디 중복 검사
		if(!isValidId && isValid) {
			openModal(modal, '이미 사용 중이거나 탈퇴한 아이디입니다!', id);
			id.focus();
			isValid = isValidId;
		}
		
		// 비밀번호 특수문자 포함 여부 검사
		if(!isValidPassword && isValid) {
			openModal(modal, '비밀번호에 특수문자가 1개 이상 포함되어야 합니다!', password);
			password.focus();
			isValid = isValidPassword;	
		}
		
		// 비밀번호와 비밀번호 확인 대조
		if(password.value!=password_re.value && isValid) {
			openModal(modal, '비밀번호와 비밀번호 확인이 불일치합니다!', password, password_re);
			password.focus();
			isValid = false;
		}
		
		// 생년월일 유효성 검증
		if((year.value<1900 || new Date(year.value + '-' + month.value + '-'+ day.value)=='Invalid Date') && isValid) {
			setCaution(caution, '유효하지 않은 생년월일입니다!', year, true, true, true);
			year.value = '';
			day.value = '';
			isValid = false;
		}
		
		if(isValid) register.submit(); // 유효성 검증을 통과하면 submit 이벤트 발생
		else if(event) event.preventDefault(); // 유효성 검증을 통과하지 못하고 함수가 event 객체를 전달받은 경우 기본 이벤트 제거
		
		return isValid;
	} // end of getRegister
</script>
<!-- 동네 찾기 시작 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 찾기 화면을 넣을 element
    var element_wrap = document.getElementById('wrap');

    function foldDaumPostcode() {
        // iframe을 넣은 element를 숨김
        element_wrap.style.display = 'none';
    }

    function sample3_execDaumPostcode() {
        // 현재 scroll 위치를 저장
        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
        new daum.Postcode({
            oncomplete: function(data) {
            	// 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
            	// 각 주소의 노출 규칙에 따라 주소를 조합
            	let addr = data.sido + ' ' + data.sigungu + ' ';
            	if(data.bname1!=='') addr += data.bname1; // 법정리(읍/면) 지역인 경우
            	else addr += data.bname; // 법정동 지역인 경우
            	
                // 읍면동까지의 정보를 동네 필드에 입력
                document.getElementById('address').value = addr;

                // iframe을 넣은 element를 숨김
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않음)
                element_wrap.style.display = 'none';

                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌림
                document.body.scrollTop = currentScroll;
            },
            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분; iframe을 넣은 element의 높이값을 조정
            onresize : function(size) {
                element_wrap.style.height = size.height+'px';
            },
            width : '100%',
            height : '100%'
        }).embed(element_wrap, {
        	autoClose: true // 검색 결과 선택 후 자동으로 레이어가 사라짐
        });

        // iframe을 넣은 element를 보이게 한다.
        element_wrap.style.display = 'block';
    }
</script>
<!-- 동네 찾기 끝 -->
</body>
</html>