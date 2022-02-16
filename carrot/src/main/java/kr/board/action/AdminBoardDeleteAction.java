package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.controller.Action;

public class AdminBoardDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		int aboard_num = Integer.parseInt(request.getParameter("aboard_num"));
		
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth == null) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.adminDeleteBoard(aboard_num);
		
		return"redirect:/board/adminBoard.do";
	}

}
