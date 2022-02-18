package kr.my.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.my.dao.MyDAO;
import kr.product.vo.ProductVO;
import kr.util.PagingUtil;

public class MemberProductListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인 되지 않은 경우
			return "rediredct:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		MyDAO dao = MyDAO.getInstance();
		int complete = Integer.parseInt(request.getParameter("complete"));
		int count = dao.getmyProductCount(complete, user_num);
		//페이지 처리
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"memberProduct.do");
		List<ProductVO> list = null;
		if(count > 0) {
			list = dao.getProductList(page.getStartCount(), page.getEndCount(), complete, user_num);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		request.setAttribute("user_num", user_num);
		if(complete == 0) { //판매중인 상품
		return "/WEB-INF/views/my/product_SellList.jsp";
		}else if(complete ==1) { //내가 구매한 상품
			return "/WEB-INF/views/my/product_BuyList.jsp";
		}else if(complete == 2) {//내가 판매한 상품
			return "/WEB-INF/views/my/product_CompleteList.jsp";
		}
		return "/WEB-INF/views/common/notice.jsp";
	}
	
}




















