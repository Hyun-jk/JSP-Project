package kr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class MemberBoardFAQListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		//user_num 반환
		Integer user_num = (Integer)session.getAttribute("user_num");
		//자주묻는 질문 카테고리
		int category = 1;
		
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
	
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		//기몬페이지는 운영정책으로 시작
		String keyfield = request.getParameter("keyfield");
		if(keyfield == null) keyfield = "1";
		String keyword = request.getParameter("keyword");
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword, category);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"memBoardFAQ.do");
		
		List<BoardVO>list = null;
		if(count>0) {
			list = dao.getListBoard(page.getStartCount(), page.getEndCount(), keyfield, keyword, category);
		}

		request.setAttribute("count",count);
		request.setAttribute("list",list);
		request.setAttribute("pagingHtml",page.getPagingHtml());
		
		return"/WEB-INF/views/board/member_boardFAQ_list.jsp";
		
	}

}
