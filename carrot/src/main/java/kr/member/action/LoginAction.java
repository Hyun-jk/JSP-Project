package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class LoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 전송된 데이터 반환
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = dao.checkMember(id);
		boolean check = false;
		
		if(vo!=null) { // 아이디 존재
			// 비밀번호 일치 여부 체크
			check = vo.checkPassword(password);
			// 로그인 실패시 auth 값을 이용하기 위해 request 영역에 저장
			request.setAttribute("auth", vo.getAuth());
		}
		
		if(check) { // 인증 성공
			// 로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_num", vo.getAmember_num());
			session.setAttribute("user_id", vo.getId());
			session.setAttribute("user_auth", vo.getAuth());
			session.setAttribute("user_photo", vo.getPhoto());
			
			// 인증 성공시 호출
			return "redirect:/main/main.do";
		}
		
		// 인증 실패시 호출
		return "/WEB-INF/views/member/login.jsp";
	}

}