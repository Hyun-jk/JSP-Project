package kr.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.my.vo.AmannerVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class MyDAO {
	
	private static MyDAO instance = new MyDAO();
	public static MyDAO getInstance() {
		return instance;
	}
	private MyDAO() {}

	//판매중인 상품,거래중인 상품, 거래완료된 상품 카운트처리
	public int getmyProductCount(int complete, int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			if(complete ==0) {
				//판매중인 상품 complete 0/ amember_num = user_num
				sql = "SELECT COUNT(*) FROM aproduct WHERE complete = ? AND amember_num = ?";
			}else if(complete == 1){
				//내가 구매한 상품 complete 1 / buyer_num = user_num
				sql = "SELECT COUNT(*) FROM aproduct WHERE complete = ? AND buyer_num = ?";
			}else if(complete == 2) {
				//내가 판매한 상품 complete 2 /amember_num = user_num
				sql = "SELECT COUNT(*) FROM aproduct WHERE complete = ? AND amember_num = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			if(complete ==0) {
				//판매중인 상품 complete 0/ amember_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}else if(complete == 1){
				//내가 구매한 상품 complete 1 / buyer_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}else if(complete == 2) {
				//내가 판매한 상품 complete 2 /amember_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null,pstmt,conn);
		}
		
		return count;
	}
	//판매중인 상품, 거래중인 상품, 거래료된 상품 목록처리
	public List<ProductVO> getProductList(int startRow, int endRow, int complete, int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<ProductVO> list = null;
		try {
			conn = DBUtil.getConnection();
			if(complete == 0) {
				//판매중인 상품 complete 0/ amember_num = user_num
				sql = "SELECT * FROM(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM aproduct WHERE complete = ? AND amember_num = ?)a) "
						+ "WHERE rnum > ? AND rnum <= ?";
			}else if(complete == 1){
				//내가 구매한 상품 complete 1 / buyer_num = user_num
				sql = "SELECT * FROM(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM aproduct WHERE complete = ? AND buyer_num = ?)a) "
						+ "WHERE rnum > ? AND rnum <= ?";
			}else if(complete == 2) {
				//내가 판매한 상품 complete 2 /amember_num = user_num
				sql = "SELECT * FROM(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM aproduct WHERE complete = ? AND amember_num = ?)a) "
						+ "WHERE rnum > ? AND rnum <= ?";
			}else {
				System.out.println("검색한 값이 없습니다.");
			}
			
			pstmt = conn.prepareStatement(sql);
			if(complete ==0) {
				//판매중인 상품 complete 0/ amember_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}else if(complete == 1){
				//내가 구매한 상품 complete 1 / buyer_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}else if(complete == 2) {
				//내가 판매한 상품 complete 2 /amember_num = user_num
				pstmt.setInt(1, complete);
				pstmt.setInt(2, user_num);
			}
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ProductVO>();
			while(rs.next()) {
				ProductVO product = new ProductVO();
				product.setPhoto1(rs.getString("photo1"));
				product.setTitle(rs.getString("title"));
				product.setPrice(rs.getInt("price"));
				product.setModify_date(rs.getDate("modify_date"));
				product.setComplete(rs.getInt("complete"));
				product.setAproduct_num(rs.getInt("Aproduct_num"));
				product.setAmember_num(rs.getInt("amember_num"));
				product.setStatus(rs.getInt("status"));
				product.setBuyer_num(rs.getInt("buyer_num"));
				product.setReg_date(rs.getDate("reg_date"));
				list.add(product);
			}
			return list;
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs,pstmt,conn);
		}
	}
	
	//점수 및 매너 입력
	public void insertManner(AmannerVO manner, int seller_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		int mannerCount = 0;
		int rateSum = 0;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "SELECT COUNT(*)FROM amanner WHERE amember_num = ?";
			pstmt1 = conn.prepareStatement(sql);
			rs = pstmt1.executeQuery();
			if(rs.next()) {
				mannerCount = rs.getInt(1);
			}
			
			sql = "SELECT SUM(rate) FROM amanner WHERE amember_num = ?";
			pstmt2 = conn.prepareStatement(sql);
			rs = pstmt2.executeQuery();
			if(rs.next()) {
				rateSum = rs.getInt(1);
			}
			
			sql = "INSERT INTO amanner(amanner_num,amember_num,aproduct_num,rate,review, buyer_num)"
					+ "VALUES(amanner_seq.nextval,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, manner.getAmember_num());
			pstmt3.setInt(2, manner.getAproudct_num());
			pstmt3.setInt(3,  (rateSum + manner.getRate())/(mannerCount+1));
			pstmt3.setString(4, manner.getReview());
			pstmt3.setInt(5, manner.getBuyer_num());
			pstmt3.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt1, conn);
		}
		
	}
	
	//평가 후기 카운트
	public int CountManner(int seller_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM amanner WHERE amember_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,seller_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return 	count;
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//평가 후기 목록
	public List<AmannerVO> GetManner(int startRow, int endRow, int seller_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sql = null;
		List<AmannerVO> list = null;
		float totalRate =0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//매너점수 평균값 구하기
			sql = "SELECT TRUNC(SUM(rate)/COUNT(*),2) FROM amanner WHERE amember_num = ?";
			pstmt1 = conn.prepareStatement(sql);
			pstmt1.setInt(1, seller_num);
			rs1 = pstmt1.executeQuery();
			if(rs1.next()) {
				totalRate = rs1.getFloat(1);
			}
			
			//목록 구하기
			sql = "SELECT * FROM(SELECT a.*,rownum rnum FROM (SELECT * FROM amanner m JOIN amember_detail d "
					+ "ON m.buyer_num = d.amember_num "
					+ "WHERE m.amember_num = ?)a) "
					+ "WHERE rnum >= ? AND rnum < ?";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, seller_num);
			pstmt2.setInt(2, startRow);
			pstmt2.setInt(3, endRow);
			
			rs2 = pstmt2.executeQuery();
			list = new ArrayList<AmannerVO>();
			while(rs2.next()) {
				AmannerVO manner = new AmannerVO();
				manner.setAmanner_num(rs2.getInt("amanner_num"));
				manner.setRate(rs2.getInt("rate"));
				manner.setReview(rs2.getString("review"));
				manner.setBuyer_num(rs2.getInt("buyer_num"));
				manner.setTotalRate(totalRate);
				
				MemberVO member = new MemberVO();
				member.setNickname(rs2.getString("nickname"));
				member.setPhoto(rs2.getString("photo"));
				manner.setMember(member);
				list.add(manner);
			}
			conn.commit();
			return list;
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs1, pstmt2, null);
			DBUtil.executeClose(rs2, pstmt1, conn);
		}
		
	}
	
	//평가 후기 삭제
	public void deleteManner(AmannerVO manner)throws Exception{
		
	}
	
	//평가 후기 수정
	
}





















