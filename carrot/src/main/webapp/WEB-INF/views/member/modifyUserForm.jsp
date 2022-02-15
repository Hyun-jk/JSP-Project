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
				alert('동네를 입력하세요!');
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
				<label for="address">동네</label>
				<input type="text" name="address" id="address" readonly>
				<input type="button" value="동네 찾기" onclick="sample3_execDaumPostcode();">
				<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
					<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
				</div>
			</li>
		<li>
			<label for="address_favor">선호지역</label>
			<input type="text" name="address_favor" id="address_favor" value="${member.address_favor}" maxlength="30">
		</li>
	</ul>
	<div>
		<input type="submit" value="수정">
		<input type="button" value="회원탈퇴"  onclick="location.href='deleteUserForm.do'">
	</div>
	</form>
	<h3>회원탈퇴</h3>
		<ul>
			<li>
				<input type="button" value="회원탈퇴" 
				  onclick="location.href='deleteUserForm.do'">
			</li>
		</ul>
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