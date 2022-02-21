<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#detail_form').submit(function(){
			if($('#name').val().trim()==''){
				alert('이름을 입력하세요!');
				$('#name').val('').focus();
				return false;
			}
			if($('#phone').val().trim()==''){
				alert('전화번호를 입력하세요!');
				$('email').val('').focus();
				return false;
			}
			if($('#email').val().trim()==''){
				alert('이메일을 입력하세요!');
				$('email').val('').focus();
				return false;
			}
			if($('#address').val().trim()==''){
				alert('동네를 입력하세요!');
				$('#address').val('').focus();
				return false;
			}
		});
	});
	$(function(){
	    
        $("#address").removeAttr("readonly");       // readonly 삭제
    });
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">

	<div id="My-content">
	    <jsp:include page="/WEB-INF/views/common/side.jsp"/> 
	</div>
	<h2>${member.id}의 정보 수정(관리자 전용)</h2>
	<form action="detailUser.do" method="post" id="detail_form">
		<input type="hidden" name="amember_num" value="${member.amember_num}">
		<table>
			<tr>
				<th><label>등급</label></th>
				<td><c:if test="${member.auth != 3}">
				<input type="radio" name="auth" value="1" id="auth1" <c:if test="${member.auth == 1}">checked</c:if>>정지
				<input type="radio" name="auth" value="2" id="auth2" <c:if test="${member.auth == 2}">checked</c:if>>일반
				</c:if>
				<c:if test="${member.auth == 3}">
				<input type="radio" name="auth" value="3" id="auth3" checked>관리
				</c:if></td>
			</tr>
			<tr>
				<th><label for="name">이름</label></th>
				<td><input type="text" name="name" id="name" value="${member.name}"
				                                     maxlength="10"></td>
			</tr>
			<tr>
				<th><label for="phone">전화번호</label></th>
				<td><input type="text" name="phone" id="phone" value="${member.phone}"
				                                    maxlength="15"></td>
			</tr>
			<tr>
				<th><label for="email">이메일</label></th>
				<td><input type="email" name="email" id="email" value="${member.email}"
				                                    maxlength="50"></td>
			</tr>
			<tr>
				<th><label for="address">동네</label></th>
				<td><input type="text" name="address" id="address" readonly value="${member.address}">
				<input type="button" value="동네 찾기" onclick="sample3_execDaumPostcode();">
				<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
					<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
				</div>  </td>        
			</tr>
		</table>
		<div class="align-center">
			<input type="submit" value="수정">
			<input type="button" value="목록" 
			                         onclick="location.href='memberList.do'">
		</div>
		</form>
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
</div>
</body>
</html>



