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
		String id = request.getParameter("id").toUpperCase(); // 사용자 입력 값을 대문자로 변환
		String password = request.getParameter("password").toUpperCase(); // 사용자 입력 값을 대문자로 변환
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_vo = dao.checkMember(id);
		boolean check = false;
		
		if(db_vo!=null) { // 아이디 존재
			// 비밀번호 일치 여부 체크
			check = db_vo.checkPassword(password);
			// 로그인 실패시 auth 값을 이용하기 위해 request 영역에 저장
			request.setAttribute("auth", db_vo.getAuth());
		}
		
		if(check) { // 인증 성공
			// 로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_num", db_vo.getAmember_num());
			session.setAttribute("user_id", db_vo.getId());
			session.setAttribute("user_nickname", db_vo.getNickname());
			session.setAttribute("user_auth", db_vo.getAuth());
			session.setAttribute("user_photo", db_vo.getPhoto());
			if(db_vo.getAddress_favor()!=null) session.setAttribute("user_address", db_vo.getAddress_favor());
			else session.setAttribute("user_address", db_vo.getAddress());
			
			// 인증 성공시 호출
			return "redirect:/main/main.do";
		}
		
		// 인증 실패시 호출
		return "/WEB-INF/views/member/login.jsp";
	}

}