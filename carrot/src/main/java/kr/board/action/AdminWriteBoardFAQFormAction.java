package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class AdminWriteBoardFAQFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 3) {
			return "redirect:/common/notice.jsp";
		}
		
		
		return "/WEB-INF/views/board/admin_boardFAQ_writeForm.jsp";
	}

}
