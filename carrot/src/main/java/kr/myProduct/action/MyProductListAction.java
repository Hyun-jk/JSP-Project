package kr.myProduct.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.MyProductVO;
import kr.util.PagingUtil;

public class MyProductListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			HttpSession session = request.getSession();
			//user_num 반환
			Integer user_num = (Integer)session.getAttribute("user_num");
			
			if(user_num == null) {
				return "redirect:/member/loginForm.do";
			}
		
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null) pageNum = "1";
			
			ProductDAO dao = ProductDAO.getInstance();
			
			int count = dao.countMyproduct(user_num);
			
			//페이지처리
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,10,10,"myproduct_list.jsp");
			
			List<MyProductVO>list = null;
			if(count>0) {
				list = dao.getMyproduct(page.getStartCount(), page.getEndCount(),user_num);
			}
			request.setAttribute("count",count);
			request.setAttribute("list",list);
			request.setAttribute("pagingHtml",page.getPagingHtml());
			
			return"/WEB-INF/views/myProduct/myproduct_list.jsp";
	}

}
