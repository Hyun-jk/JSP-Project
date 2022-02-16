package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class AdminBoardDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return"redirect:/member/loginForm.do";
		}
		int aboard_num = Integer.parseInt(request.getParameter("aboard_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.admingetBoard(aboard_num);
		
		board.setTitle(StringUtil.useNoHtml(board.getTitle()));
		board.setContent(StringUtil.userBrHtml(board.getContent()));
		
		request.setAttribute("board", board);
		request.setAttribute("user_num",user_num);
		
		return "/WEB-INF/views/board/admin_board_detail.jsp";
	}
}
