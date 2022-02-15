package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class WriteAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer amember_num =(Integer)session.getAttribute("user_num");
		if(amember_num == null) {//로그인 되지 않은 경우			
		    return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);	
		ProductVO product = new ProductVO();
		product.setPhoto1(multi.getFilesystemName("photo1"));
		product.setPhoto2(multi.getFilesystemName("photo2"));
		product.setPhoto3(multi.getFilesystemName("photo3"));
		product.setPhoto4(multi.getFilesystemName("photo4"));
		product.setPhoto5(multi.getFilesystemName("photo5"));
		product.setTitle(multi.getParameter("title"));		
	    product.setPrice(Integer.parseInt(multi.getParameter("price")));
	    product.setContent(multi.getParameter("content"));
	    product.setCategory(Integer.parseInt(multi.getParameter("category")));
	    product.setAmember_num(amember_num);
		
	    ProductDAO dao = ProductDAO.getInstance();
	    dao.registerProduct(product);
		
	    return "/WEB-INF/views/product/write.jsp";
	
	}

}
