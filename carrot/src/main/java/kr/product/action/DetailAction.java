package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.CategoryVO;
import kr.product.vo.MyProductVO;
import kr.product.vo.ProductVO;
import kr.util.StringUtil;

public class DetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductDAO dao = ProductDAO.getInstance();
		
		int aproduct_num = Integer.parseInt(request.getParameter("aproduct_num"));
		
		// 상품 상세 정보
		ProductVO product = dao.getProduct(aproduct_num);
		
		MemberVO seller = product.getMemberVO();
		CategoryVO category = product.getCategoryVO();
		
		// 제목에 HTML 태그를 허용하지 않음
		product.setTitle(StringUtil.useNoHtml(product.getTitle()));
		// 내용에 줄바꿈을 제외한 HTML 태그를 허용하지 않음
		product.setContent(StringUtil.useBrNoHtml(product.getContent()));
		
		request.setAttribute("product", product);
		request.setAttribute("seller", seller);
		request.setAttribute("category", category);
		
		// 관심 상품 정보
		Integer user_num = (Integer)request.getSession().getAttribute("user_num");
		if(user_num!=null) { // 로그인한 경우
			MyProductVO my = new MyProductVO();
			my.setAproduct_num(aproduct_num);
			my.setAmember_num(user_num);
			boolean exist = dao.existsMyProduct(my);
			request.setAttribute("exist", exist);
		}
		
		// 실시간 중고 더보기
		List<ProductVO> listProduct = dao.getListProduct(1, 6, null, null, null);
		request.setAttribute("listProduct", listProduct);
		request.setAttribute("user_num",user_num);
		
		// JSP 경로 반환
		return "/WEB-INF/views/product/detail.jsp";
	}

}