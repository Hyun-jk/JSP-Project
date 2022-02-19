package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.controller.Action;

public class LatestChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인되어 있는 경우
			// 가장 최근에 받은 메시지 번호 구하기
			int latest_chat = ChatDAO.getInstance().getLatestChat(user_num);
			
			mapAjax.put("latest_chat", latest_chat);
			mapAjax.put("result", "success");
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}