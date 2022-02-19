package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class MemberBoardInqueryWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		int aboard_category_num = Integer.parseInt(request.getParameter("keyfield"));
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth < 2 && user_auth >= 0) {
			return "redirect:/common/notice.jsp";
		}
		
		//관리자로 로그인이 된 경우
		BoardVO board = new BoardVO();
		board.setAmember_num(user_num);
		//일대일 문의 2
		board.setCategory(2);
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setAuth_num(user_auth);
		board.setAboard_category_num(aboard_category_num);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.adminInsertBoard(board);
		
		return "/WEB-INF/views/board/member_boardInquery_write.jsp";
	}

}
