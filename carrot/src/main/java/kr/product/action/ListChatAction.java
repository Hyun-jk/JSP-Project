package kr.product.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.ChatDAO;
import kr.product.vo.ChatVO;
import kr.util.PagingUtil;

public class ListChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인되어 있는 경우
			int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
			int opponent_num = Integer.parseInt(request.getParameter("opponent_num"));
			
			ChatDAO dao = ChatDAO.getInstance();
			
			// 페이지 처리
			ChatVO vo = new ChatVO();	
			vo.setAproduct_num(aproduct_num);
			vo.setAmember_num(user_num);
			vo.setRead(3);
			int count = dao.getCountChat(vo);
			int rowCount = 10;
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
			
			// 주고 받은 메시지 불러오기
			List<ChatVO> list = null;
			if(count>0) {
				list = dao.getListChat(aproduct_num, user_num, 3, page.getStartCount(), page.getEndCount());
			}
			else {
				list = Collections.emptyList(); // 데이터가 없는 경우 null 대신 비어 있는 리스트를 반환
			}
			
			// 채팅 중인 상대방 정보, 판매자 정보, 물품 정보 불러오기
			ChatVO header = dao.getChatVO(aproduct_num, opponent_num);
			
			mapAjax.put("count", count);
			mapAjax.put("rowCount", rowCount);
			mapAjax.put("list", list);
			mapAjax.put("header", header);
			mapAjax.put("result", "success");
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}