package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class AdminBoardWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 3) {
			return "redirect:/common/notice.jsp";
		}
		
		//관리자로 로그인이 된 경우
		BoardVO board = new BoardVO();
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setAuth_num(user_auth);
		board.setAmember_num(user_num);
		//공지사항-->0;
		board.setCategory(0);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.adminInsertBoard(board);
		
		return "/WEB-INF/views/board/adminWriteBoard.jsp";
	}

}
