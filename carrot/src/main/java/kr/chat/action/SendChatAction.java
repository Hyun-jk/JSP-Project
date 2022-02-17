package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;

public class SendChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8"); // content가 String이므로 필요
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인되어 있는 경우
			ChatVO chat = new ChatVO();
			chat.setAchatroom_num(Integer.parseInt(request.getParameter("achatroom_num")));
			chat.setAproduct_num(Integer.parseInt(request.getParameter("aproduct_num")));
			chat.setAmember_num(user_num);
			chat.setOpponent_num(Integer.parseInt(request.getParameter("opponent_num")));
			chat.setContent(request.getParameter("content"));
			
			ChatDAO.getInstance().sendChat(chat);
			
			mapAjax.put("result", "success");
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}