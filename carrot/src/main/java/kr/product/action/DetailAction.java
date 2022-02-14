package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.CategoryVO;
import kr.product.vo.ProductVO;
import kr.util.StringUtil;

public class DetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = ProductDAO.getInstance().getProduct(Integer.parseInt(request.getParameter("aproduct_num")));
		
		ProductVO product = (ProductVO)list.get(0);
		MemberVO seller = (MemberVO)list.get(1);
		CategoryVO category = (CategoryVO)list.get(2);
		
		// 제목에 HTML 태그를 허용하지 않음
		product.setTitle(StringUtil.useNoHtml(product.getTitle()));
		// 내용에 줄바꿈을 제외한 HTML 태그를 허용하지 않음
		product.setContent(StringUtil.useBrNoHtml(product.getContent()));
		
		request.setAttribute("product", product);
		request.setAttribute("seller", seller);
		request.setAttribute("category", category);
		
		// JSP 경로 반환
		return "/WEB-INF/views/product/detail.jsp";
	}

}