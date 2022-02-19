package kr.chat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatRoomVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
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
		
		// 채팅 목록 불러오기
		List<ChatRoomVO> chatrooms = dao.getListChatRoom(user_num, request.getParameter("filter"));
		request.setAttribute("chatrooms", chatrooms);
		
		// 파라미터 값과 채팅 목록 길이를 이용하여 현재 채팅방 번호 지정
		String achatroom_num_p = request.getParameter("achatroom_num");
		int achatroom_num = -1;
		if(achatroom_num_p!=null) achatroom_num = Integer.parseInt(achatroom_num_p);
		else if(chatrooms.size()>0) achatroom_num = chatrooms.get(0).getAchatroom_num();
		
		// 현재 채팅방 정보 불러오기
		ChatRoomVO chatroom = null;
		ProductVO product = null;
		MemberVO opponent = null;
		if(achatroom_num>-1) {
			chatroom = dao.getChatRoom(achatroom_num);
			product = chatroom.getProductVO();
			if(user_num==chatroom.getBuyer_num()) { // 로그인한 회원이 물품 판매자인 경우
				opponent = chatroom.getSellerVO(); // 구매(희망)자 정보 불러오기
			}
			else { // 로그인한 회원이 물품 구매(희망)자인 경우
				opponent = chatroom.getBuyerVO(); // 판매자 정보 불러오기
			}
		}
		
		request.setAttribute("chatroom", chatroom);
		request.setAttribute("product", product);
		request.setAttribute("opponent", opponent);
		
		// JSP 경로 반환
		return "/WEB-INF/views/chat/chat.jsp";
	}

}