package kr.product.action;

import java.util.ArrayList;
import java.util.List;

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
		ChatDAO dao = ChatDAO.getInstance();
		
		// 채팅 중인 물품/상대방 목록 불러오기
		List<Integer> aproduct_nums = dao.getListChatByUser(user_num);
		List<ChatVO> listChat = null;
		if(aproduct_nums!=null) {
			listChat = new ArrayList<ChatVO>();
			for(int i : aproduct_nums) {
				ChatVO chat = dao.getLatestChat(i, user_num);
				MemberVO opponent = chat.getOpponentVO();
				String[] addrs = opponent.getAddress().split(" ");
				opponent.setAddress(addrs[addrs.length-1]);
				chat.setOpponentVO(opponent);
				listChat.add(chat);
			}
			
			request.setAttribute("listChat", listChat);
		}
		
		// 채팅 중인 물품/상대방 정보 가져오기
		Integer aproduct_num;
		Integer opponent_num;	
		if(request.getParameter("aproduct_num")!=null) {
			aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		}
		else {
			aproduct_num = listChat.get(0).getAproduct_num();
		}
		if(request.getParameter("opponent_num")!=null) {
			opponent_num = Integer.parseInt(request.getParameter("opponent_num"));
		}
		else {
			opponent_num = listChat.get(0).getOpponent_num();	
		}
		request.setAttribute("aproduct_num", aproduct_num);
		request.setAttribute("opponent_num", opponent_num);

		if(aproduct_num!=null && opponent_num!=null) {
			ChatVO header = dao.getChatVO(aproduct_num, opponent_num);		
			ProductVO product = header.getProductVO();
			MemberVO opponent = header.getOpponentVO();
			
			request.setAttribute("product", product);
			request.setAttribute("opponent", opponent);
		}
		
		// JSP 경로 반환
		return "/WEB-INF/views/product/chat.jsp";
	}

}