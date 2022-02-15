package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class DeleteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer amember_num = (Integer)session.getAttribute("user_num");
		if(amember_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		
		request.setAttribute("aproduct_num", aproduct_num);
		
		//JSP 경로 반환
		return "/WEB-INF/views/product/deleteForm.jsp";
	}

}
