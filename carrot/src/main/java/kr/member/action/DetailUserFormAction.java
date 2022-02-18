package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class DetailUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session =request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 3) {
			return "/WEB-INF/view/common/notice.jsp";
		}
		//관리자로 로그인 한 경우
		int amember_num = Integer.parseInt(request.getParameter("amember_num"));
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = dao.getMember(amember_num);
		
		request.setAttribute("member", vo);
		
		return "/WEB-INF/views/member/detailUserForm.jsp";
	}

}
