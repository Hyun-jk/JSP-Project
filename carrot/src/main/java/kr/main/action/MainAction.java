package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.BoardDAO;
import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.CategoryVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<CategoryVO> category = ProductDAO.getInstance().getListCategory();
		if(category!=null) {
			request.setAttribute("category", category);
		}
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}
