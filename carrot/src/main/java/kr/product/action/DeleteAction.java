package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer amember_num = (Integer)session.getAttribute("user_num");
		if(amember_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));

		ProductDAO dao = ProductDAO.getInstance();		 
		List<Object> db_list = dao.getProduct(aproduct_num);
		//상품 삭제
		dao.deleteProduct(aproduct_num);
        
		ProductVO product =(ProductVO) db_list.get(0);
		//상품 이미지 삭제
		FileUtil.removeFile(request, product.getPhoto1());
		FileUtil.removeFile(request, product.getPhoto2());
		FileUtil.removeFile(request, product.getPhoto3());
		FileUtil.removeFile(request, product.getPhoto4());
		FileUtil.removeFile(request, product.getPhoto5());
		
		request.setAttribute("aproduct_num", aproduct_num);

		//JSP 경로 반환
		return "/WEB-INF/views/product/delete.jsp";
	}

}
