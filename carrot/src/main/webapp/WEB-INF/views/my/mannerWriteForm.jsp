<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매너 평가 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dain.css">
<style type="text/css">
	.manner-write {
		margin: 75px auto 0;
		padding: 0;
		width: 720px;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
	div.page-main ul.manner-write * {
		text-indent: 0; /* dain.css와 충돌 */
	}
	.manner-write > li {
		margin: 10px;
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.manner-write li form {
		margin: 0 auto;
		padding: 0;
		width: 100%;
		border: none;	
	}
	.manner-write li form ul {
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
	.manner-write li form ul > li {
		margin: 10px;
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.manner-title { /* <h3> 등 h 태그는 기본 여백이 지정되어 있어서 사용하지 않는 편이 정렬하기 편함 */
		font-size: 1.2rem;
		font-weight: bold;
	}
	.manner-stars {
		width: 200px;
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.manner-stars i.bi {
		margin: 0 -2px;
		padding: 0;
		pointer-events: auto; /* dain.css와 충돌; 물품 상세 정보/채팅에서 pointer-events: none으로 둔 것은 클릭을 막기 위함; 매너 평가 작성 페이지에서는 별을 클릭할 수 있어야 하므로 none이면 안 됨 */
		transition: unset; /* 불필요한 부드러운 움직임 제거 */
	}
	.manner-stars i.bi.gray {
		color: var(--light-gray-hover);
	}
	
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<ul class="manner-write">
		<li>
			<div class="gray underline text3 manner-title">아래 점수를 눌러 판매자님과의 거래를 평가해 주세요</div>
			<br><br><br>
		</li>
		<li>
			<div class="text3 icon">
				<i class="bi bi-emoji-frown"></i> 별로에요 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<i class="bi bi-emoji-smile"></i> 좋아요!<br>
				<div class="text3"> <--------&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--------></div>
			</div>
		</li>
		<li>
			<div class="manner-stars icon">
				<i class="bi bi-star-fill gray"></i>
				<i class="bi bi-star-fill gray"></i>
				<i class="bi bi-star-fill gray"></i>
				<i class="bi bi-star gray"></i>
				<i class="bi bi-star gray"></i>
			</div>
		</li>
		<br><br><br>
		<li>
			<form id="manner" action="MannerWrite.do" method="post">
				<input type="hidden" name="rate">
				<ul class= "align-center text2"> 
					<li>
						<div class="text3">
							<div class= "gray underline manner-title">판매자님께 따뜻한 후기를 남겨보세요!</div>
						</div>
					</li>
					<li>
						<textarea rows="6" cols="30" name="review"></textarea>
					</li>
					<li>
						<input class="buttonD" type="submit" value="저장하기" >
					</li>
				</ul>
			</form>
		</li>
	</ul>
</div>
<script type="text/javascript">
	let stars = document.querySelectorAll('.manner-stars i.bi'); // 별 아이콘 목록
	let rate = document.querySelector('input[name="rate"]'); // 서버 전송용 식별자가 rate인 input 태그 선택
	let review = document.querySelector('textarea[name="review"]'); // 서버 전송용 식별자가 review인 textarea 태그 선택
	
	for(let i=0;i<stars.length;i++) {
		stars[i].addEventListener('click', function(event) { // 현재 별(=i번째)의 click 이벤트 핸들러 등록; stars[i].onclick = function() {}과 같은 기능
			for(let j=0;j<stars.length;j++) {
				if(j<=i) { // 현재 클릭한(=i) 별보다 번호가 앞선(=왼쪽에 있는) 별들을 채워진 별로 변경
					stars[j].classList.replace('bi-star', 'bi-star-fill');
				}
				else { // 현재 클릭한(=i) 별보다 번호가 나중인(=오른쪽에 있는) 별들을 비어 있는 별로 변경
					stars[j].classList.replace('bi-star-fill', 'bi-star');
				}
				
				stars[j].classList.remove('gray'); // 별 아이콘의 클릭 전 기본값은 회색인데 별이 하나라도 클릭되면 모든 별의 색을 변경
			} // end of inner loop
		
			rate.value = i+1; // 별 아이콘 번호는 0에서 4지만 rate 값은 1에서 5여야 함
		}, false); // end of addEventListener
	} // end of outer loop
	
	document.querySelector('form').addEventListener('submit', function(event) {
		if(!rate.value) { // rate 값이 없으면
			event.preventDefault(); // 기본 이벤트를 제거하여 서버 전송을 막음
			alert('매너 점수를 선택해주세요!');
			return false; // 함수 실행 중지; alert 두 번 뜨는 것 방지하기 위함
		}
		if(!review.value) { // rate 값이 없으면
			event.preventDefault(); // 기본 이벤트를 제거하여 서버 전송을 막음
			alert('매너 평가를 작성해주세요!');
			return false; // 함수 실행 중지
		}
	}, false); // end of addEventListener
</script>
</body>
</html>