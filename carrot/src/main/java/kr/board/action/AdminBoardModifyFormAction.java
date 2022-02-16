package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class AdminBoardModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 3) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		int aboard_num = Integer.parseInt(request.getParameter("aboard_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO aboard = dao.admingetBoard(aboard_num);
		
		request.setAttribute("aboard", aboard);
		
		return "/WEB-INF/views/board/admin_board_modifyForm.jsp";
	}

}
