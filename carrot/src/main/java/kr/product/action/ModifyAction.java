package kr.product.action;

import java.util.List;

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
		List<Object> db_list = dao.getProduct(aproduct_num);
		
		ProductVO list = (ProductVO) new ProductVO();
		list.setAproduct_num(aproduct_num);
		list.setPhoto1(multi.getParameter("Photo1"));
		list.setPhoto2(multi.getParameter("Photo2"));
		list.setPhoto3(multi.getParameter("Photo3"));
		list.setPhoto4(multi.getParameter("Photo4"));
		list.setPhoto5(multi.getParameter("Photo5"));
		list.setTitle(multi.getParameter("title"));		
		list.setPrice(Integer.parseInt(multi.getParameter("price")));
		list.setContent(multi.getParameter("content"));
		list.setCategory(Integer.parseInt(multi.getParameter("category")));
	    list.setAmember_num(amember_num);
	
	    dao.updateProduct(list);
		
	    ProductVO product =(ProductVO) db_list.get(0);
	    
	    if(photo1 != null) FileUtil.removeFile(request, product.getPhoto1());
		if(photo2 != null) FileUtil.removeFile(request, product.getPhoto2());
		if(photo3 != null) FileUtil.removeFile(request, product.getPhoto3());
		if(photo4 != null) FileUtil.removeFile(request, product.getPhoto4());
		if(photo5 != null) FileUtil.removeFile(request, product.getPhoto5());
		
		request.setAttribute("aproduct_num", aproduct_num);
		
		return"/WEB-INF/views/product/modify.jsp";
	}

	}
