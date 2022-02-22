package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class ModifyAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		Integer amember_num = (Integer)session.getAttribute("user_num");
		if(amember_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);	
		
		int aproduct_num = Integer.parseInt(multi.getParameter("aproduct_num"));
		String photo1 = multi.getFilesystemName("photo1");
		String photo2 = multi.getFilesystemName("photo2");
		String photo3 = multi.getFilesystemName("photo3");
		String photo4 = multi.getFilesystemName("photo4");
		String photo5 = multi.getFilesystemName("photo5");
		
		ProductDAO dao = ProductDAO.getInstance();
		ProductVO db_product = dao.getProduct(aproduct_num);
		
		ProductVO product = (ProductVO) new ProductVO();
		product.setAproduct_num(aproduct_num);
		product.setPhoto1(photo1);
		product.setPhoto2(photo2);
		product.setPhoto3(photo3);
		product.setPhoto4(photo4);
		product.setPhoto5(photo5);
		product.setTitle(multi.getParameter("title"));		
		product.setPrice(Integer.parseInt(multi.getParameter("price")));
		product.setContent(multi.getParameter("content"));
		product.setCategory(Integer.parseInt(multi.getParameter("category")));
	    product.setAmember_num(amember_num);
	    product.setStatus(Integer.parseInt(multi.getParameter("status")));
	
	    dao.updateProduct(product);
	    
	    if(photo1 != null) FileUtil.removeFile(request, db_product.getPhoto1());
		if(photo2 != null) FileUtil.removeFile(request, db_product.getPhoto2());
		if(photo3 != null) FileUtil.removeFile(request, db_product.getPhoto3());
		if(photo4 != null) FileUtil.removeFile(request, db_product.getPhoto4());
		if(photo5 != null) FileUtil.removeFile(request, db_product.getPhoto5());
		
		request.setAttribute("product", product);
		
		return"/WEB-INF/views/product/modify.jsp";
	}

	}
