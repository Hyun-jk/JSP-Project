package kr.my.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.my.dao.MyDAO;
import kr.my.vo.AmannerVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.PagingUtil;

public class MyMannerProfileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
				//비로그인일 때 오류 발생
				HttpSession session = request.getSession();
				Integer user_num = (Integer)session.getAttribute("user_num");
				
				
				MyDAO myDao = MyDAO.getInstance();
				
				//판매자 정보 보기
				MemberDAO memberDao = MemberDAO.getInstance();
				MemberVO userInfo = memberDao.getMember(user_num);
				
				//판매중인 상품 표시
				int productCount = myDao.getmyProductCount(0, user_num);
				//내가 판매한 상품
				int sellProductCount = myDao.getmyProductCount(2, user_num);
				ProductDAO pdao = ProductDAO.getInstance(); 
				String pageNum1 = request.getParameter("pageNum");
				if(pageNum1 == null) pageNum1 = "1";
				PagingUtil page1 = new PagingUtil(Integer.parseInt(pageNum1),productCount,5,10,"myMannerProfile.do");
				List<ProductVO> mySellProduct = null;
				String category="";
				String keyword="";
				String address="";
				if(productCount > 0) {
					mySellProduct = pdao.getListProduct(page1.getStartCount(),page1.getEndCount(),category,keyword,address);
				}

				//매너점수 표시 및 거래후기 표시
				String pageNum2 = request.getParameter("pageNum");
				if(pageNum2 == null) pageNum2 = "1";
				
				int mannerCount = myDao.CountManner(user_num);
				
				//페이지 처리
				PagingUtil page2 = new PagingUtil(Integer.parseInt(pageNum2),mannerCount,10,10,"myMannerProfile.do");
				
				List<AmannerVO> mannerList = null;
				if(mannerCount >0) {
					mannerList = myDao.GetManner(page2.getStartCount(), page2.getEndCount(), user_num);
				}
				
				request.setAttribute("mannerCount",mannerCount);
				request.setAttribute("productCount", productCount);
				request.setAttribute("sellProductCount", sellProductCount);
				request.setAttribute("mannerList",mannerList);
				request.setAttribute("mySellProduct",mySellProduct);
				request.setAttribute("userInfo", userInfo);
				request.setAttribute("pagingHtml1", page1.getPagingHtml());
				request.setAttribute("pagingHtml2",page2.getPagingHtml());
		
				return "/WEB-INF/views/my/myManner_Profile.jsp";
				}

			}
