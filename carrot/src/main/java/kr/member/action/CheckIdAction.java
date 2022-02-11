package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class CheckIdAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		MemberVO db_vo = MemberDAO.getInstance().checkMember(request.getParameter("id").toUpperCase()); // 사용자 입력 값을 대문자로 변환
		
		if(db_vo==null) {
			mapAjax.put("result", "idNotFound");
		}
		else {
			mapAjax.put("result", "idDuplicated");
		}
		
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}