<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	if(${product.status==2}) alert('정상적으로 수정되었습니다.');
	else if(${product.status==1}) alert('상품을 정상적으로 삭제했습니다.');
	location.href='detail.do?aproduct_num='+${product.aproduct_num};
</script>