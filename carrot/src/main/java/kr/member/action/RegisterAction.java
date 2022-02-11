package kr.member.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class RegisterAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 자바빈 생성
		MemberVO vo = new MemberVO();
		vo.setId(request.getParameter("id").toUpperCase()); // 아이디는 DB에 대문자로 저장
		vo.setPassword(request.getParameter("password").toUpperCase()); // 비밀번호는 DB에 대문자로 저장
		vo.setName(request.getParameter("name"));
		vo.setNickname(request.getParameter("nickname"));
		vo.setAge(Date.valueOf(request.getParameter("age")));
		String[] phones = request.getParameterValues("phone");
		String phone = "";
		for(String s : phones) {
			phone += s;
		}
		vo.setPhone(phone);
		vo.setAddress(request.getParameter("address"));
		vo.setEmail(request.getParameter("email"));
		
		MemberDAO.getInstance().insertMember(vo);
		
		// JSP 경로 반환
		return "/WEB-INF/views/member/register.jsp";
	}

}