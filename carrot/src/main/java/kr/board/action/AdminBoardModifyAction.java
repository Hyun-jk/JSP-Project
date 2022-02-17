package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class AdminBoardModifyAction implements Action{

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
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = new BoardVO();
		int aboard_num = Integer.parseInt(request.getParameter("aboard_num"));
		
		board.setAboard_num(aboard_num);
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		dao.adminModifyBoard(board);
		
		return "redirect:/board/adminBoardDetail.do?aboard_num="+aboard_num;
	}

}