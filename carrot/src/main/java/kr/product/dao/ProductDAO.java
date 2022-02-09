package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.product.vo.ProductVO;

public class ProductDAO {
	//싱글턴 패턴
	private static ProductDAO instance = new ProductDAO();
	public static ProductDAO getInstance() {
		return instance;
	}
	private ProductDAO() {}

	//context.xml에서 설정정보를 읽어들여 커넥션풀로부터 커넥션을 할당 받음
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe");
		return ds.getConnection();

	}
	//자원정리
	private void executeClose(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {
		if(rs!= null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(conn!=null)try {conn.close();}catch(SQLException e) {}
	}
	//상품등록
	public void registerProduct(ProductVO product)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션 풀로부터 커넥션 할당
			conn= getConnection();
			//SQL문 작성
			sql = "INSERT INTO aproduct (Aproduct_num,Amember_num,photo1,photo2,photo3,photo4,photo5,"
					+ "title,price,content,category,nickname,reg_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";			    
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, product.getAproduct_num());
			pstmt.setInt(2, product.getAmember_num());
			pstmt.setString(3, product.getPhoto1());
			pstmt.setString(4, product.getPhoto2());
			pstmt.setString(5, product.getPhoto3());
			pstmt.setString(1, product.getPhoto4());
			pstmt.setString(2, product.getPhoto5());
			pstmt.setString(3, product.getTitle());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getContent());
			pstmt.setString(5, product.getCategory());
			pstmt.setString(5, product.getNickname());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e){
			throw new Exception(e);
		}finally {
			executeClose(null, pstmt, conn);
		}
	}
	//목록
	//상품상세정보
	//상품수정
	public void updateProduct(ProductVO product)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = getConnection();
			//SQL문 작성
			sql = "UPDATE aproduct SET Photo1=?, Photo2=?, Photo3=?, Photo4=?, "
					+ "Photo5=?, Title=?, Price=?, Content=?, Category=?  WHERE Amember_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터를 바인딩
			pstmt.setString(3, product.getPhoto1());
			pstmt.setString(4, product.getPhoto2());
			pstmt.setString(5, product.getPhoto3());
			pstmt.setString(1, product.getPhoto4());
			pstmt.setString(2, product.getPhoto5());
			pstmt.setString(3, product.getTitle());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getContent());
			pstmt.setString(5, product.getCategory());
			pstmt.setInt(1, product.getAmember_num());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			executeClose(null, pstmt, conn);

		}
	}
	//상품삭제



}
