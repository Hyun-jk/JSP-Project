<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
 
 $(function(){
    let photo_path = $('.my-photo1').attr('src');//처음 화면에 보여지는 이미지 읽기
    let my_photo1;
    
    $('#photo1_btn').click(function(){
       $('#photo1_choice').show();
     
    });
    
    //이미지 선택 및 이미지 미리보기
    $('#photo1').change(function(){
       my_photo1 = this.files[0];
       if(!my_photo1){
          $('.my-photo1').attr('src',photo_path);
           return;
       }
       
       if(my_photo1.size > 1024*1024){
          alert('1MB까지만 업로드 가능!');
          photo.value = '';
          return;
       }
       
       var reader = new FileReader();
       reader.readAsDataURL(my_photo1);
       
       reader.onload=function(){
          $('.my-photo1').attr('src',reader.result);
       };
    });
      

       $(function() {
      $('#write_form').submit(function() {
         if ($('#title').val().trim() == '') {
            alert('제목을 입력하세요!');
            $('#title').val('').focus();
            return false;
         }
         if ($('#price').val() == '') {
            alert('가격을 입력하세요!');
            $('#price').focus();
            return false;
         }
         if ($('#category').val() == '') {
            alert('카테고리를 설정하세요!');
            $('#category').focus();
            return false;
         }
         if ($('#photo1').val() == '') {
            alert('사진을 추가 해보세요!');
            $('#photo1').focus();
            return false;
         }
         if ($('#content').val().trim() == '') {
            alert('상품설명을 입력하세요!');
            $('#content').val('').focus();
            return false;
         }
      });
   });
         });         
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
   <div class="page-main">
      <form action="modify.do" method="post" enctype="multipart/form-data" id="modify_form">
      <input type="file" name="photo1" id="photo1" accept="image/gif,image/png,image/jpeg">
         <input type="hidden" name="aproduct_num" value="${product.aproduct_num}">
         <ul><li><label for="photo1" >상품사진1</label> 
               <img src="${pageContext.request.contextPath}/upload/${product.photo1}"
                  width="150" height="150" class="my-photo1">
                <div class="buttonC">
                        <P> <label for="photo1">CLICK HERE!</label></P>
                     </div>
            </li>
            <br>
            <br>
            <li><label for="photo2">상품사진2</label> 
            <input type="file" name="photo2" id="photo2" accept="image/gif,image/png,image/jpeg">
            <div class="buttonC">
                         <M> <label for="photo2">CLICK HERE!</label></M>
                     </div> 
            </li>
            <li><label for="photo3">상품사진3</label> <input type="file"
               name="photo3" id="photo3" accept="image/gif,image/png,image/jpeg">
               <div class="buttonC">
                         <M> <label for="photo3">CLICK HERE!</label></M>
                     </div>
            </li>
            <li><label for="photo4">상품사진4</label> <input type="file"
               name="photo4" id="photo4" accept="image/gif,image/png,image/jpeg">
               <div class="buttonC">
                         <M><label class="flex-container.column" for="photo4">CLICK HERE!</label></M>
                     </div>
            </li>
            <li><label for="photo5">상품사진5</label> <input type="file"
               name="photo5" id="photo5" accept="image/gif,image/png,image/jpeg">
               <div class="buttonC">
                         <M> <label for="photo5">CLICK HERE!</label></M>
                     </div>
            </li>
            <li><label for="title">제목*</label> <input type="text"
               name="title" id="title" maxlength="10" class="box" value="${product.title}">
            </li>
            <li><label for="price">가격*</label> <input type="number"
               name="price" id="price"  min="1"max="99999999" class="box" value="${product.price}">원
            </li>
            <li><label for="category">카테고리*</label> 
                <select name="category" maxlength="10" class="box">
                  <option value="1" <c:if test="${category.category == 1}">selected</c:if>>디지털기기</option>
                  <option value="2" <c:if test="${category.category == 2}">selected</c:if>>생활/가전</option>
                  <option value="3" <c:if test="${category.category == 3}">selected</c:if>>남성의류/잡화</option>
                  <option value="4" <c:if test="${category.category == 4}">selected</c:if>>여성의류/잡화/미용</option>
                  <option value="5" <c:if test="${category.category == 5}">selected</c:if>>유아동/유아도서</option>
                  <option value="6" <c:if test="${category.category == 6}">selected</c:if>>스포츠</option>
                  <option value="7" <c:if test="${category.category == 7}">selected</c:if>>생활/가구</option>
            </select>
            </li>
            <li><label for="content">상품설명*</label> <textarea name="content"
                  id="content" cols="30" rows="5" class="box">${product.content}</textarea>
             </li>
             <li>
            <label>상품 표시 여부</label>
            <input type="radio" name="status" value="1" id="status1" <c:if test="${product.status == 1}">checked</c:if>>미표시
            <input type="radio" name="status" value="2" id="status2" <c:if test="${product.status == 2}">checked</c:if>>표시   
         </li>
         </ul>
         <div class="align-center">
         <div style="display: inline-block">
            <input type="submit" value="수정하기" class="buttonA"></div>
         <div style="display: inline-block">
            <input type="button" value="삭제하기" class="buttonB" onclick="location.href='deleteForm.do?aproduct_num=${product.aproduct_num}'"></div>      
            
         </div>
      </form>
   </div>
</body>
</html>
            
