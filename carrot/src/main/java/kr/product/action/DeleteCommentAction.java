package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.CommentDAO;

public class DeleteCommentAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인한 경우
			int acomment_num = Integer.parseInt(request.getParameter("acomment_num"));
			
			CommentDAO dao = CommentDAO.getInstance();
			
			if(user_num!=dao.getMember(acomment_num)) { // 로그인한 회원과 댓글 작성자가 불일치하는 경우
				mapAjax.put("result", "wrongAccess");
			}
			else {
				dao.deleteComment(acomment_num);
				mapAjax.put("result", "success");
			}
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}