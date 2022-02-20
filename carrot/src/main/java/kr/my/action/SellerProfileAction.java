package kr.my.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.my.dao.MyDAO;
import kr.my.vo.AmannerVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.PagingUtil;

public class SellerProfileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		//비로그인일 때 오류 발생
		Integer seller_num = Integer.parseInt(request.getParameter("seller_num"));
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(seller_num == user_num) {
			return "/WEB-INF/views//member/myPage.jsp";
		}
		
		MyDAO myDao = MyDAO.getInstance();
		
		//판매자 정보 보기
		MemberDAO memberDao = MemberDAO.getInstance();
		MemberVO sellerInfo = memberDao.getMember(seller_num);
		
		//판매중인 상품 표시
		int productCount = myDao.getmyProductCount(0, seller_num);
		ProductDAO pdao = ProductDAO.getInstance(); 
		String pageNum1 = request.getParameter("pageNum");
		if(pageNum1 == null) pageNum1 = "1";
		PagingUtil page1 = new PagingUtil(Integer.parseInt(pageNum1),productCount,10,10,"seller_Profile.jsp");
		List<ProductVO> sellerProduct = null;
		String category="";
		String keyword="";
		String address="";
		if(productCount > 0) {
			sellerProduct = pdao.getListProduct(page1.getStartCount(),page1.getEndCount(),category,keyword,address);
		}

		//매너점수 표시 및 거래후기 표시
		String pageNum2 = request.getParameter("pageNum");
		if(pageNum2 == null) pageNum2 = "1";
		
		int mannerCount = myDao.CountManner(seller_num);
		
		//페이지 처리
		PagingUtil page2 = new PagingUtil(Integer.parseInt(pageNum2),mannerCount,10,10,"seller_Profile.jsp");
		
		List<AmannerVO> mannerList = null;
		if(mannerCount >0) {
			mannerList = myDao.GetManner(page2.getStartCount(), page2.getEndCount(), seller_num);
		}
		
		request.setAttribute("mannerCount",mannerCount);
		request.setAttribute("productCount", productCount);
		request.setAttribute("mannerList",mannerList);
		request.setAttribute("sellerProduct",sellerProduct);
		request.setAttribute("sellerInfo", sellerInfo);
		request.setAttribute("pagingHtml1", page1.getPagingHtml());
		request.setAttribute("pagingHtml2",page2.getPagingHtml());
		
		//판매자가 상품목록에서 내꺼보는거
		//구매자가 상품목록에서 판매자꺼 보는거
		
		
		return "/WEB-INF/views/my/seller_Profile.jsp";
	}

}
