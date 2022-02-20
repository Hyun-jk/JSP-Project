package kr.my.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.my.dao.MyDAO;
import kr.my.vo.AmannerVO;

public class MannerWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		int user_num = (Integer)session.getAttribute("user_num");
		Integer seller_num = (Integer)session.getAttribute("seller_num");
		Integer aproduct_num = (Integer)session.getAttribute("aproduct_num");
		
		
		request.setCharacterEncoding("utf-8");
		MyDAO dao = MyDAO.getInstance();
		AmannerVO manner = new AmannerVO();
		manner.setAmember_num(seller_num);
		manner.setAproudct_num(aproduct_num);
		manner.setRate(Integer.parseInt(request.getParameter("rate")));
		manner.setReview(request.getParameter("review"));
		manner.setBuyer_num(user_num);
		
		dao.insertManner(manner);
		
		
		return "/WEB-INF/views/my/mannerWrite.jsp";
	}
	
}
