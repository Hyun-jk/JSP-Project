package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.ChatDAO;
import kr.product.vo.ChatVO;

public class SendChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else {
			ChatVO vo = new ChatVO();
			vo.setAproduct_num(Integer.parseInt(request.getParameter("aproduct_num")));
			vo.setAmember_num(user_num);
			vo.setOpponent_num(Integer.parseInt(request.getParameter("opponent_num")));
			vo.setContent(request.getParameter("content"));
			
			ChatDAO.getInstance().sendChat(vo);
			
			mapAjax.put("result", "success");
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}