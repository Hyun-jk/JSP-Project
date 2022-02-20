package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class LoginFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num!=null) { // 로그인되어 있는 경우
			return "redirect:/main/main.do";
		}

		// JSP 경로 반환
		return "/WEB-INF/views/member/loginForm.jsp";
	}

}