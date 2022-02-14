package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.product.vo.AddressVO;
import kr.product.vo.CategoryVO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class ProductDAO {
	//싱글턴 패턴
	private static ProductDAO instance = new ProductDAO();
	public static ProductDAO getInstance() {
		return instance;
	}
	private ProductDAO() {}

	//상품등록
	public void registerProduct(ProductVO product)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO aproduct (Aproduct_num,Amember_num,photo1,photo2,photo3,photo4,photo5,"
					+ "title,price,content,category,reg_date) VALUES(aproduct_seq.nextval,?,?,?,?,?,?,?,?,?,?,SYSDATE)";			    
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, product.getAmember_num());
			pstmt.setString(2, product.getPhoto1());
			pstmt.setString(3, product.getPhoto2());
			pstmt.setString(4, product.getPhoto3());
			pstmt.setString(5, product.getPhoto4());
			pstmt.setString(6, product.getPhoto5());
			pstmt.setString(7, product.getTitle());
			pstmt.setInt(8, product.getPrice());
			pstmt.setString(9, product.getContent());
			pstmt.setInt(10, product.getCategory());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//상품수정
	public void updateProduct(ProductVO product)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE aproduct SET Photo1=?, Photo2=?, Photo3=?, Photo4=?, "
					+ "Photo5=?, Title=?, Price=?, Content=?, Category=?  WHERE Aproduct_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setString(1, product.getPhoto1());
			pstmt.setString(2, product.getPhoto2());
			pstmt.setString(3, product.getPhoto3());
			pstmt.setString(4, product.getPhoto4());
			pstmt.setString(5, product.getPhoto5());
			pstmt.setString(6, product.getTitle());
			pstmt.setInt(7, product.getPrice());
			pstmt.setString(8, product.getContent());
			pstmt.setInt(9, product.getCategory());
			pstmt.setInt(10, product.getAproduct_num());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);

		}
	}
	//상품삭제
		public void deleteProduct(int num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM aproduct WHERE Aproduct_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, num);

				//SQL문 실행
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

	// 동네 목록
	public List<AddressVO> getListAddress() throws Exception {
		List<AddressVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT address, "
					// 광역자치단체
					+ "SUBSTR(address, 1, INSTR(address, ' ')-1) AS sido, "
					// 시군구
					+ "SUBSTR(address, INSTR(address, ' ')+1, INSTR(address, ' ', 1, 2)-1-INSTR(address, ' ')) AS sigungu, "
					// 읍면동
					+ "SUBSTR(address, INSTR(address, ' ', 1, 2)+1) AS bname "
				+ "FROM (SELECT DISTINCT(address) FROM aproduct JOIN amember_detail USING(amember_num)) "
				+ "ORDER BY address";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AddressVO>();
			while(rs.next()) {
				AddressVO vo = new AddressVO();
				vo.setAddress(rs.getString("address"));
				vo.setSido(rs.getString("sido"));
				vo.setSigungu(rs.getString("sigungu"));
				vo.setBname(rs.getString("bname"));
				
				list.add(vo);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

	// 카테고리 목록
	public List<CategoryVO> getListCategory() throws Exception {
		List<CategoryVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM acategory ORDER BY category";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<CategoryVO>();
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCategory(rs.getInt("category"));
				vo.setName(rs.getString("name"));
				
				list.add(vo);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

	
	// 물품 수
	public int getCountProduct(String category, String keyword, String address) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int bind = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			// 검색 처리
			if(category!=null && !category.isEmpty()) sub_sql += "AND category = ? ";
			if(keyword!=null && !keyword.isEmpty()) sub_sql += "AND title LIKE ? ";
			if(address!=null && !address.isEmpty()) sub_sql += "AND address LIKE ? ";

			sql = "SELECT COUNT(*) FROM aproduct JOIN amember_detail USING(amember_num) "
				+ "WHERE status=2 AND complete=0 " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			
			if(category!=null && !category.isEmpty()) pstmt.setInt(++bind, Integer.parseInt(category));
			if(keyword!=null && !keyword.isEmpty()) pstmt.setString(++bind, "%" + keyword + "%");
			if(address!=null && !address.isEmpty()) pstmt.setString(++bind, "%" + address + "%");
			
			rs = pstmt.executeQuery();	
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	// 물품 목록
	public List<ProductVO> getListProduct(int startCount, int endCount, String category, String keyword, String address) throws Exception {
		List<ProductVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int bind = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			// 검색 처리
			if(category!=null && !category.isEmpty()) sub_sql += "AND category = ? ";
			if(keyword!=null && !keyword.isEmpty()) sub_sql += "AND title LIKE ? ";
			if(address!=null && !address.isEmpty()) sub_sql += "AND address LIKE ? ";
			
			sql = "SELECT * FROM (SELECT r.*, ROWNUM AS rnum "
				// 판매자 동네 결합
				+ "FROM (SELECT * FROM (SELECT p.*, d.address "
					+ "FROM aproduct p JOIN amember_detail d ON p.amember_num=d.amember_num) "
					// 상품별 채팅 수 계산
					+ "JOIN (SELECT aproduct.aproduct_num, COUNT(achat.aproduct_num) AS chats "
						+ "FROM aproduct LEFT JOIN achat "
						+ "ON aproduct.aproduct_num=achat.aproduct_num "
						+ "GROUP BY aproduct.aproduct_num) "
					+ "USING(aproduct_num) "
					// 상품별 댓글 수 계산
					+ "JOIN (SELECT aproduct.aproduct_num, COUNT(acomment.aproduct_num) AS replies "
						+ "FROM aproduct LEFT JOIN acomment "
						+ "ON aproduct.aproduct_num=acomment.aproduct_num "
						+ "GROUP BY aproduct.aproduct_num) "
					+ "USING(aproduct_num) "
					// 상품별 관심 수 계산
					+ "JOIN (SELECT aproduct.aproduct_num, COUNT(amyproduct.aproduct_num) AS likes "
						+ "FROM aproduct LEFT JOIN amyproduct "
						+ "ON aproduct.aproduct_num=amyproduct.aproduct_num "
						+ "GROUP BY aproduct.aproduct_num) "
					+ "USING(aproduct_num) "
				// 검색 처리
				+ "WHERE status=2 AND complete=0 " + sub_sql
				// 정렬
				+ "ORDER BY aproduct_num DESC) r)"
				// 페이지 처리
				+ "WHERE rnum >= ? AND rnum <=?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(category!=null && !category.isEmpty()) pstmt.setInt(++bind, Integer.parseInt(category));
			if(keyword!=null && !keyword.isEmpty()) pstmt.setString(++bind, "%" + keyword + "%");
			if(address!=null && !address.isEmpty()) pstmt.setString(++bind, "%" + address + "%");
			pstmt.setInt(++bind, startCount);
			pstmt.setInt(++bind, endCount);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ProductVO>();
			while(rs.next()) {
				ProductVO vo = new ProductVO();
				vo.setAproduct_num(rs.getInt("aproduct_num"));
				vo.setAmember_num(rs.getInt("amember_num"));
				vo.setPhoto1(rs.getString("photo1"));
				vo.setTitle(rs.getString("title"));
				vo.setPrice(rs.getInt("price"));
				vo.setCategory(rs.getInt("category"));
				vo.setAddress(rs.getString("address"));
				vo.setChats(rs.getInt("chats"));
				vo.setReplies(rs.getInt("replies"));
				vo.setLikes(rs.getInt("likes"));
				vo.setReg_date(rs.getDate("reg_date"));
				vo.setModify_date(rs.getDate("modify_date"));
				
				list.add(vo);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 물품 상세 정보
	public List getProduct(int aproduct_num) throws Exception {
		List list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT p.*, m.nickname, m.photo, m.address, m.rate, c.name AS cname, "
				+ "ch.chats, cmt.replies, my.likes FROM aproduct p "
				// 판매자 정보 결합
				+ "JOIN amember_detail m ON p.amember_num=m.amember_num "
				// 상품 분류명 결합
				+ "JOIN acategory c ON p.category=c.category "
				// 채팅 수 계산
				+ "JOIN (SELECT aproduct.aproduct_num, COUNT(achat.aproduct_num) AS chats "
					+ "FROM aproduct LEFT JOIN achat "
					+ "ON aproduct.aproduct_num=achat.aproduct_num "
					+ "GROUP BY aproduct.aproduct_num) ch "
				+ "ON p.aproduct_num=ch.aproduct_num "
				// 댓글 수 계산
				+ "JOIN (SELECT aproduct.aproduct_num, COUNT(acomment.aproduct_num) AS replies "
					+ "FROM aproduct LEFT JOIN acomment "
					+ "ON aproduct.aproduct_num=acomment.aproduct_num "
					+ "GROUP BY aproduct.aproduct_num) cmt "
				+ "ON p.aproduct_num=cmt.aproduct_num "
				// 관심 상품 수 계산
				+ "JOIN (SELECT aproduct.aproduct_num, COUNT(amyproduct.aproduct_num) AS likes "
					+ "FROM aproduct LEFT JOIN amyproduct "
					+ "ON aproduct.aproduct_num=amyproduct.aproduct_num "
					+ "GROUP BY aproduct.aproduct_num) my "
				+ "ON p.aproduct_num=my.aproduct_num "
				// 조건절
				+ "WHERE p.aproduct_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList();
			if(rs.next()) {
				// 물품 상세 정보
				ProductVO product = new ProductVO();
				product.setAproduct_num(aproduct_num);
				product.setAmember_num(rs.getInt("amember_num"));
				// 사진
				product.setPhoto1(rs.getString("photo1"));
				product.setPhoto2(rs.getString("photo2"));
				product.setPhoto3(rs.getString("photo3"));
				product.setPhoto4(rs.getString("photo4"));
				product.setPhoto5(rs.getString("photo5"));
				// 판매글 정보
				product.setTitle(rs.getString("title"));
				product.setContent(rs.getString("content"));
				product.setReg_date(rs.getDate("reg_date"));
				product.setModify_date(rs.getDate("modify_date"));
				product.setPrice(rs.getInt("price"));
				// 채팅, 댓글, 관심
				product.setChats(rs.getInt("chats"));
				product.setReplies(rs.getInt("replies"));
				product.setLikes(rs.getInt("likes"));
				// 판매글 상태
				product.setStatus(rs.getInt("status"));
				product.setComplete(rs.getInt("complete"));
				product.setBuyer_num(rs.getInt("buyer_num"));
				list.add(product);
				
				// 판매자 정보
				MemberVO member = new MemberVO();
				member.setNickname(rs.getString("nickname"));
				member.setAddress(rs.getString("address"));
				member.setPhoto(rs.getString("photo"));
				member.setRate(rs.getDouble("rate"));
				list.add(member);
				
				// 카테고리 정보
				CategoryVO category = new CategoryVO();
				category.setCategory(rs.getInt("category"));
				category.setName(rs.getString("cname"));
				list.add(category);
			}
		}
		catch(Exception e) {
			throw new Exception();
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

		//사진등록
		public void updateMyPhoto(String photo1, Integer amember_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();

				//SQL문 작성
				sql = "UPDATE amember_detail SET photo1=? WHERE amember_num=?";

				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, photo1);
				pstmt.setInt(2, amember_num);

				//SQL문 실행
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	}