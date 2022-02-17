package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.product.dao.ChatDAO;
import kr.product.vo.ChatVO;
import kr.product.vo.ProductVO;

public class ChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		// 로그인되어 있는 경우
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		int opponent_num = Integer.parseInt(request.getParameter("opponent_num"));
		
		ChatDAO dao = ChatDAO.getInstance();
		
		// 채팅 중인 상대방 및 물품 정보 가져오기
		ChatVO header = dao.getChatVO(aproduct_num, opponent_num);		
		MemberVO opponent = header.getOpponentVO();
		ProductVO product = header.getProductVO();

		request.setAttribute("opponent", opponent);
		request.setAttribute("product", product);
		
		// JSP 경로 반환
		return "/WEB-INF/views/product/chat.jsp";
	}

}