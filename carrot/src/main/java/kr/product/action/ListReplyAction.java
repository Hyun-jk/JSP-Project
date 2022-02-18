package kr.product.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.CommentDAO;
import kr.product.vo.CommentVO;

public class ListReplyAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		int acomment_parent = Integer.parseInt(request.getParameter("acomment_parent"));
		
		// 댓글 목록 불러오기
		List<CommentVO> replies = CommentDAO.getInstance().getListReply(aproduct_num, acomment_parent);

		if(replies==null) {
			replies = Collections.emptyList(); // 데이터가 없는 경우 null 대신 비어 있는 리스트를 반환
		}
		
		mapAjax.put("replies", replies);
		mapAjax.put("result", "success");
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
	
}