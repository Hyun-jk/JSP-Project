package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.AddressVO;
import kr.product.vo.CategoryVO;
import kr.product.vo.ProductVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ProductDAO dao = ProductDAO.getInstance();
		
		// 동네 목록
		List<AddressVO> listAddress = dao.getListAddress();
		if(listAddress!=null) {
			request.setAttribute("listAddress", listAddress);
		}
		
		// 카테고리 목록
		List<CategoryVO> listCategory = dao.getListCategory();
		if(listCategory!=null) {
			request.setAttribute("listCategory", listCategory);
		}
		
		// 물품 목록
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");
		String sido = request.getParameter("sido");
		if(sido==null) sido = "";
		String sigungu = request.getParameter("sigungu");
		if(sigungu==null) sigungu = "";
		String bname = request.getParameter("bname");
		if(bname==null) bname = "";
		String address = "";
		if(!sido.isEmpty()) address += sido;
		if(!sigungu.isEmpty()) address += " " + sigungu;
		if(!bname.isEmpty()) address += " " + bname;
		if(address.isEmpty()) address = (String)request.getSession().getAttribute("user_address");
		
		int count = dao.getCountProduct(category, keyword, address);
		List<ProductVO> list = null;
		PagingUtil page = new PagingUtil(category, keyword, Integer.parseInt(pageNum), count, 10, 5, "main.do", "&address=" + address);		
				
		if(count>0) {
			list = dao.getListProduct(page.getStartCount(), page.getEndCount(), category, keyword, address);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}