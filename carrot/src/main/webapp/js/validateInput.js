// 입력 필드가 비어 있는지 여부를 검증하는 함수 정의
function validateNotNull(event) {
	let list = document.getElementsByTagName('li'); // <li> 요소들을 선택
	for(let i=0;i<list.length;i++) {
		let inputs = list[i].querySelectorAll('input:not([type=button]),textarea'); // <li> 요소 하위 요소 중 button이 아닌 <input> 요소나 <textarea> 요소 선택
		for(input of inputs) {
			if(input.classList.contains('nullable')) continue; // 요소의 클래스 중 nullable이 있으면 빈 칸인지 여부를 확인하지 않음
			input.value = input.value.trim(); // 문자열 양끝 공백 제거
			if(!input.value) { // 아무것도 입력하지 않은 경우
				let word = list[i].querySelector('label').textContent; // <label> 요소 사이의 문자열 추출
				let post = (word.charCodeAt(word.length-1) - '가'.charCodeAt(0)) % 28 > 0 ? '을' : '를'; // 마지막 글자의 받침 유무에 따라 적절한 조사 선택
				alert(word + post + ' 입력하세요!');
				if(input.name=='address') list[i].querySelector('input[type=button]').focus(); // address의 경우 버튼에 포커스 가도록 처리
				else input.focus();
				event.preventDefault(); // submit의 기본 이벤트 제거
				return false; // for문의 반복을 멈추고 함수 실행 종료
			}
		}
	}
	return true;
}

// <form>의 submit 이벤트 발생시 입력 필드가 비어 있는지 여부를 검증하는 함수 정의
// 사용 예제: validateSubmit('register_form');
function validateSubmit(id) { // <form> 요소의 id를 인자로 전달
	document.getElementById(id).addEventListener('submit', validateNotNull, false);
}

// 주어진 문자열의 바이트 길이를 구하는 함수 정의
function getBytesLength(str) {
    let bytes = 0;
    for(let i=0;i<str.length;i++) {
        let unicode = str.charCodeAt(i);
        bytes += unicode >> 11 ? 3 : (unicode >> 7 ? 2 : 1); // 2^11=2048로 나누었을 때 몫이 있으면 3bytes, 그보다 작은 수이면서 2^7=128로 나누었을 때 몫이 있으면 2bytes, 그 외에는 1byte
    }
    return bytes;
}

// 바이트 길이를 태그의 id 속성에 기반해 제한하는 함수 정의
// 사용 예제: validateBytesLength({title:150,name:30,passwd:12});
function validateBytesLength(obj) { // 길이 제한이 필요한 <input> 요소의 id와 바이트 길이를 객체 형식 인자로 전달
	for(let key in obj) {
		document.getElementById(key).addEventListener('keyup', function() {
			while(getBytesLength(this.value)>obj[key]) {
				this.value = this.value.slice(0, -1);
			}
		}, false);
	}
}

// 바이트 길이를 태그의 name 속성에 기반해 제한하는 함수 정의
function validateBytesLengthByName(obj) {
	for(let key in obj) {
		document.addEventListener('keyup', function(event) {
			if(event.target && event.target.name==key) {
				while(getBytesLength(event.target.value)>obj[key]) {
					event.target.value = event.target.value.slice(0, -1);
				}				
			}
		}, false);
	}
}

// 아이디 및 비밀번호 입력 제한하는 함수 정의
function validateChars(event, caution) {
	event.target.value = event.target.value.trim(); // 입력 필드에서 공백 제거
	if(!event.target.value) return false; // 아무것도 입력하지 않은 경우 함수 실행 종료
	
	let pattern = event.target.id==='id' ? new RegExp(/[^a-zA-Z0-9]/) : (event.target.id==='password' ? new RegExp(/[^a-zA-Z0-9!@#\$%\^&\*]/) : false); // 현재 입력 필드의 아이디에 따라 허용하는 문자 설정
	if(!pattern) return false; // 현재 입력 필드가 아이디나 비밀번호가 아니면 함수 실행 종료
	
	if(pattern.test(event.target.value)) { // 허용되지 않는 문자를 입력한 경우
		setCaution(caution, event.target.placeholder, event.target, true, true, true);
		event.target.value = ''; // 입력 값 초기화
	}
	else if(caution.querySelector('span').textContent == event.target.placeholder) {
		caution.classList.remove('called');
	}
}

// 특수문자 포함 여부 검사하는 함수 정의
function hasSpecialChars(event, caution) {
	if(!event.target.value) return false; // 아무것도 입력하지 않은 경우 함수 실행 종료
	if(event.target.id!=='password') return false; // 현재 입력 필드가 비밀번호가 아니면 함수 실행 종료
	
	let pattern = new RegExp(/[!@#\$%\^&\*]/);
	
	let isValidPassword = pattern.test(event.target.value); // 특수문자 포함하면 true
	
	let str = '특수문자를 1개 이상 포함해야 합니다!';
	
	if(!isValidPassword) setCaution(caution, str, event.target, true, true, true);
	else if(caution.querySelector('span').textContent == str) caution.classList.remove('called');
	
	return isValidPassword;
}

// 모달 여는 함수 정의
function openModal(modal, str, input) {
	if(arguments.length>2) { // 인자로 특정 입력 필드를 전달하면
		for(let i=2;i<arguments.length;i++) {
			arguments[i].blur(); // 해당 입력 필드 포커스 해제하고
			arguments[i].value = ''; // 입력 필드 값을 초기화
		}
	}
	modal.querySelector('span').textContent = str; // 모달 문구 변경
	if(document.documentElement.scrollWidth>=document.documentElement.clientWidth) {
		modal.style.width = document.documentElement.scrollWidth + 'px'; // 모달 배경 영역의 너비를 현재 문서 전체 너비로 변경
	}
	if(document.documentElement.scrollHeight>=document.documentElement.clientHeight) {
		modal.style.height = document.documentElement.scrollHeight + 'px'; // 모달 배경 영역의 높이를 현재 문서 전체 높이로 변경
		modal.querySelector('.modal-content').style.top = window.innerHeight*2/5 + 80 + 'px'; // 모달 내용 영역의 위치를 현재 화면 높이의 40% 높이로 변경
		window.scrollTo({top:modal.querySelector('.modal-content').offsetTop, behavior:'smooth'}); // 스크롤을 모달 내용 영역의 top 위치로 이동
	}
	modal.classList.add('show'); // 모달 열기
	modal.querySelector('input').focus(); // 확인 버튼에 포커스; 엔터 키로 모달 닫힘
}

// 경고 메시지 UI 처리하는 함수 정의
function setCaution(caution, str, input, append, focus, called) { // caution=클래스가 caution인 <div> 태그 객체; input=<input> 태그 객체; append=경고 메시지 위치 이동 여부 boolean; focus=현재 입력 필드에 포커스 설정 여부; called=경고 메시지 강조 여부 
	caution.querySelector('span').textContent = str; // 경고 문구 변경하고
	
	if(append) input.parentNode.parentNode.appendChild(caution); // 경고 메시지 위치를 입력 필드 바로 아래로 이동
		
	if(called) caution.classList.add('called'); // 경고 메시지 강조
	else caution.classList.remove('called');
	
	caution.classList.remove('hide'); // 경고 메시지 보이기
	
	if(focus) input.focus(); // 입력 필드에 포커스 설정
}

// 입력 필드가 비어 있는지 여부를 검증하고 경고 메시지 UI 처리하는 함수 정의
function validateNN(caution, input, append) { // caution=클래스가 caution인 <div> 태그 객체; input=<input> 태그 객체; append=경고 메시지 위치 이동 여부 boolean
	input.value = input.value.trim(); // 입력 필드의 양끝 공백 제거
	if(!input.value) { // 아무것도 입력하지 않은 경우
		let label;
		if(input.type=='select') label = input.options[input.selectedIndex].text; // <select> 태그인 경우 현재 선택된 <option> 태그 내부 문자열 가져오기
		else label = input.parentNode.querySelector('label') ? input.parentNode.querySelector('label').textContent : input.placeholder; // <input> 태그의 형제 태그 중 <label>이 있으면 <label> 내부 문자열을 가져오고, 없으면 <input> 태그의 placeholder 값 가져오기

		let post = (label.charCodeAt(label.length-1) - '가'.charCodeAt(0)) % 28 > 0 ? '을' : '를'; // 받침 유무에 따라 조사 처리
		post += input.type=='select' || input.name=='address' ? ' 선택하세요!' : ' 입력하세요!';
 
		setCaution(caution, label + post, input, append, true, true);
		
		return false;
	}
	caution.classList.add('hide'); // 경고 메시지 숨기기
	return true;
}