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
import kr.util.PagingUtil;

public class ListCommentAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		
		CommentDAO dao = CommentDAO.getInstance();
		
		// 페이지 처리
		int count = dao.getCountComment(aproduct_num);
		int rowCount = 5;
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, rowCount, 1, null);
			
		// 댓글 목록 불러오기
		List<CommentVO> comments = null;
		if(count>0) {
			comments = dao.getListComment(aproduct_num, page.getStartCount(), page.getEndCount());
		}
		else {
			comments = Collections.emptyList(); // 데이터가 없는 경우 null 대신 비어 있는 리스트를 반환
		}
		
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("comments", comments);
		mapAjax.put("result", "success");
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}