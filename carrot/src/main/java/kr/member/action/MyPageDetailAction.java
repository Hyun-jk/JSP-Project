package kr.member.action; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class MyPageDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인이 안되었을 경우
				HttpSession session = request.getSession();
				Integer user_num = (Integer)session.getAttribute("user_num");
				if(user_num == null) {//로그인이 되지 않은 경우
					return "redirect:/member/loginForm.do";
				}
				
				//로그인이 된 경우
				MemberDAO dao = MemberDAO.getInstance();
				MemberVO member = dao.getMember(user_num);
				
				request.setAttribute("member", member);
				//JSP 경로 반환
				return "/WEB-INF/views/member/MyPageDetail.jsp";
	}

}
