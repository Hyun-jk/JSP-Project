package kr.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
}





















