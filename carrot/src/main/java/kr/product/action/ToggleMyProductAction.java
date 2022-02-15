package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.MyProductVO;

public class ToggleMyProductAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { // 로그인되어 있지 않은 경우
			mapAjax.put("result", "logout");
		}
		else { // 로그인되어 있는 경우
			ProductDAO dao = ProductDAO.getInstance();
			
			MyProductVO vo = new MyProductVO();
			vo.setAproduct_num(Integer.parseInt(request.getParameter("aproduct_num")));
			vo.setAmember_num(user_num);
			
			if(dao.existsMyProduct(vo)) { // 찜한 상품에 있는 경우
				dao.deleteMyProduct(vo); // 찜한 상품 목록에서 삭제
				mapAjax.put("result", "delete");
			}
			else { // 찜한 상품에 없는 경우
				dao.insertMyProduct(vo); // 찜한 상품 목록에 추가
				mapAjax.put("result", "insert");
			}
		}
		String ajaxData = new ObjectMapper().writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}