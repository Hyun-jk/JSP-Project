package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.CategoryVO;
import kr.product.vo.ProductVO;

public class ModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer amember_num = (Integer)session.getAttribute("user_num");
		if(amember_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		
		ProductDAO dao = ProductDAO.getInstance();
		ProductVO product = dao.getProduct(aproduct_num);
		MemberVO member = product.getMemberVO();
		CategoryVO category = product.getCategoryVO();
		
		request.setAttribute("product", product);
		request.setAttribute("member", member);
		request.setAttribute("category", category);
		
		return "/WEB-INF/views/product/modifyForm.jsp";
	}

}

	

