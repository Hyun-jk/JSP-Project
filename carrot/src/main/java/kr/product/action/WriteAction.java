package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;

public class WriteAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
				
		//자바빈 생성
		ProductVO product = new ProductVO();
		
		//전송된 데이터를 자비빈에 저장
		product.setAproduct_num(Integer.parseInt(request.getParameter("aproduct_num")));
		product.setAmember_num(Integer.parseInt(request.getParameter("amember_num")));
		product.setPhoto1(request.getParameter("Photo1"));
		product.setPhoto1(request.getParameter("Photo2"));
		product.setPhoto1(request.getParameter("Photo3"));
		product.setPhoto1(request.getParameter("Photo4"));
		product.setPhoto1(request.getParameter("Photo5"));
		product.setTitle(request.getParameter("title"));		
	    product.setPrice(Integer.parseInt(request.getParameter("price")));
	    product.setContent(request.getParameter("content"));
	    product.setCategory(Integer.parseInt(request.getParameter("category")));
	    product.setNickname(request.getParameter("nickname"));
	    
	   //ProductDAO 호출
	    ProductDAO dao = ProductDAO.getInstance();
	    dao.registerProduct(product);
	   		
	  	//JSP 경로 반환
	  	return "/WEB-INF/views/product/write.jsp";			
				
	}

}
