package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.chat.dao.ChatDAO;

public class LinkChatRoomAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인되어 있는 경우
			ChatDAO dao = ChatDAO.getInstance();
			
			int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
			int seller_num = Integer.parseInt(request.getParameter("seller_num"));
			boolean exist = dao.existsChatRoom(aproduct_num, seller_num, user_num);
			if(exist==false) { // 이전에 채팅한 적이 없는 경우
				dao.insertChatRoom(aproduct_num, seller_num, user_num); // 채팅방 생성
			}
			
			int achatroom_num = dao.getChatRoom(aproduct_num, seller_num, user_num); // 채팅방 번호 구하기
			if(achatroom_num>0) { // 정상적인 채팅방 번호가 반환된 경우
				mapAjax.put("achatroom_num", achatroom_num);
				mapAjax.put("result", "success");
			}
			else {
				mapAjax.put("result", "wrongAccess");
			}
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}