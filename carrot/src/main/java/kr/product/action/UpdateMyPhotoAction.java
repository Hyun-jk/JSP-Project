package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class UpdateMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer Amember_num = (Integer)session.getAttribute("Amember_num");
		if(Amember_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			ProductDAO dao = ProductDAO.getInstance();
			ProductVO db_product = (ProductVO)dao.getProduct(Amember_num).get(0); //이전 이미지 파일 정보 일기
				
			//전송된 파일 업로드 처리
			MultipartRequest multi = FileUtil.createFile(request);
			//서버에 저장된 파일명 반환
			String photo1 = multi.getFilesystemName("photo1");
			
			//프로필 수정       파일명    mem_num (회원번호)
			dao.updateMyPhoto(photo1, Amember_num);
			
			//세션에 저장된 프로필 사진 정보 갱신
			session.setAttribute("user_photo", photo1);
			
			//이전 프로필 이미지 삭제
			FileUtil.removeFile(request, db_product.getPhoto1());
			
			mapAjax.put("result", "success");			
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}











