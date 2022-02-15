package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String origin_password = request.getParameter("origin_password");
		String password = request.getParameter("password");
		String user_id = (String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = dao.checkMember(id);
		boolean check = false;
		
		//사용자가 입력한 아이디가 존재,로그인 아이디 일치 체크
		if(vo != null && id.equals(user_id)) {
			check = vo.checkPassword(origin_password);
		}
		if(check) {
			dao.updatePassword(password, user_num);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}
