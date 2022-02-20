package kr.my.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class MannerWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "/WEB-INF/views/member/login.jsp";
		}
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		int seller_num = Integer.parseInt(request.getParameter("seller_num"));
		int buyer_num = Integer.parseInt(request.getParameter("buyer_num"));
	
		if(user_num != buyer_num) {
			return "/WEB=INF/views/common/noitice.jsp";
		}
		//이미 매너평가를 했으면 못하게 해야한다.
		//매너평가 vo 빼와서 user_num buyer_num이랑 동일하면 그만하게
		
		
		session.setAttribute("seller_num", seller_num);
		session.setAttribute("aproduct_num",aproduct_num);
		
		System.out.println(seller_num);
		System.out.println(aproduct_num);
		
		return "/WEB-INF/views/my/mannerWriteForm.jsp";
	
	}

}
