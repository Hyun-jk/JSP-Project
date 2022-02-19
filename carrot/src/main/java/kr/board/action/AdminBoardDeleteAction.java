package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class AdminBoardDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		int aboard_num = Integer.parseInt(request.getParameter("aboard_num"));
		
		if(user_num == null) { //로그인이 안되어있을때
			return "redirect:/member/loginForm.do";
		}
		if(user_auth < 2 || user_auth >3) {// 멤버 혹은 관리자가 아닐때
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.admingetBoard(aboard_num);
		//0 공지, 1 faq 2일대일 문의
		//auth 3 관리자 auth 2 멤버
		dao.adminDeleteBoard(aboard_num);
		if(user_auth == 2) {
			if(board.getCategory() == 2) { //멤버 일대일 문의
				return"redirect:/board/memberBoardInquery.do";
			}
		}
		
		if(user_auth == 3) {
			if(board.getCategory() == 0) {//관리자 공지사항
				return"redirect:/board/adminBoard.do";
			}else if(board.getAboard_category_num() == 1) {//관리자 fAQ
				return"redirect:/board/adminBoardFAQ.do";
			}else {
				return "/WEB-INF/views/common/notice.jsp";
			}
		}
		//잘못된 접속
		return "/WEB-INF/views/common/notice.jsp";
	}
}
