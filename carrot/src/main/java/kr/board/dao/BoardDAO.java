package kr.board.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BoardDAO {

	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		
		return instance;
	};
	private BoardDAO() {}
	
	
	//멤버 공지사항 및 자주 묻는 질문 총 목록 수,일대일 채팅
	public int getBoardCount(String keyfield, String keyword, int category) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();

			//1:운영정책,2:구매/판매,3:거래매너,4:이용제재
			//공지사항이 -->FAQ(1)인 경우
			if(category == 1) {
				//카테고리 설정하고, 검색을 안 한 경우
				if(keyfield != null ) {//
					sub_sql = " WHERE b.Aboard_category_num = ? ";
				}
				/*if(keyfield !=null && !"".equals(keyword)){
					//카테고리를 설정하고, 검색을 한 경우
					sub_sql = "WHERE c.Aboard_category_num = ? AND b.title LIKE = ? ";
				}
				*/
			}
			
			//공지사항 -->공지사항(0) or 일대일 문의인경우(2)
			if(category==0 || category == 2) {
				sub_sql = "WHERE b.category = ?";
			}
			
			
			sql = "SELECT COUNT(*) FROM (SELECT * FROM aboard b LEFT JOIN aboard_category c ON b.aboard_category_num = c.aboard_category_num "
					+ " LEFT JOIN amember m ON b.amember_num = m.amember_num "+ sub_sql + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			//공지사항이 -->FAQ인 경우
			if(category == 1) {
				//카테고리 설정하고, 검색을 안 한 경우
				if(keyfield != null ) {//&& "".equals(keyword)
					pstmt.setString(++cnt, keyfield);
				}/*
				if(keyfield !=null && !"".equals(keyword)){
					//카테고리를 설정하고, 검색을 한 경우
					pstmt.setString(++cnt, keyfield);
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				*/
			}
			//공지사항 -->공지사항 or 일대일 문의인경우
			if(category == 0 || category == 2 ) {
				pstmt.setInt(++cnt, category);
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
		
	}
	
	//멤버 공지사항, 자주 묻는 질문 총목록보기
	public List<BoardVO> getListBoard(int startRow, int endRow, String keyfield, String keyword, Integer category)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			//1:운영정책,2:구매/판매,3:거래매너,4:이용제재
			//공지사항이 -->FAQ(1)인 경우
			if(category == 1) {
				//카테고리 설정하고, 검색을 안 한 경우
				if(keyfield != null ) {//&& "".equals(keyword)
					sub_sql = "WHERE b.Aboard_category_num = ? ";
				}
				/*if(keyfield !=null && !"".equals(keyword)){
					//카테고리를 설정하고, 검색을 한 경우
					sub_sql = "WHERE b.Aboard_category_num = ? AND b.title LIKE =? ";
				}
				*/
			}
			//공지사항 -->공지사항(0) or 일대일 문의인경우(2)
			if(category == 0 || category == 2 ) {
				sub_sql = "WHERE b.category = ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM aboard b LEFT JOIN aboard_category c ON b.aboard_category_num = c.aboard_category_num"
					+" LEFT JOIN amember m ON b.amember_num = m.amember_num "+ sub_sql +" ORDER BY aboard_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			//공지사항이 -->FAQ인 경우
			if(category == 1) {
				//카테고리 설정하고, 검색을 안 한 경우
				if(keyfield != null ) {//&& "".equals(keyword)
					pstmt.setString(++cnt, keyfield);
				}/*
				if(keyfield !=null && !"".equals(keyword)){
					//카테고리를 설정하고, 검색을 한 경우
					pstmt.setString(++cnt, keyfield);
					pstmt.setString(++cnt, "%"+keyword+"%");
				}*/
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
			}
			
			//공지사항 -->공지사항 or 일대일 문의인경우
			if(category == 0 || category == 2 ) {
				pstmt.setInt(++cnt, category);
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
			}
			
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setAboard_num(rs.getInt("Aboard_num"));
				board.setAmember_num(rs.getInt("Amember_num"));
				board.setCategory(rs.getInt("category"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setReply_num(rs.getInt("reply_num"));
				board.setAuth_num(rs.getInt("auth_num"));
				board.setAboard_category_num(rs.getInt("aboard_category_num"));
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//멤버 공지사항 및 자주 묻는 질문 글 상세보기
	public BoardVO admingetBoard(int aboard_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM aboard WHERE aboard_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, aboard_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setAboard_num(rs.getInt("aboard_num"));
				board.setAmember_num(rs.getInt("amember_num"));
				board.setCategory(rs.getInt("category"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReg_date(rs.getDate("reg_Date"));
				board.setReply_num(rs.getInt("reply_num"));
				board.setAuth_num(rs.getInt("auth_num"));
				board.setAboard_category_num(rs.getInt("aboard_category_num"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return board;
	}
	
	
	//관리자 공지사항,일대일 문의사항, 자주 묻는 질문 입력
	public void adminInsertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		String sub_sql2 = "";
		//자주묻는 질문
		if(board.getCategory()==1) {
			sub_sql = ",Aboard_category_num";
			sub_sql2 = ",?";
		}else if(board.getCategory()==2) {//일대일 질문
			sub_sql = ",reply_num";
			sub_sql2 = ",?";
		}
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO aboard(Aboard_num,Amember_num,category,title,content,reg_date,auth_num "+ sub_sql+")"
					+ " VALUES(Aboard_seq.nextval,?,?,?,?,SYSDATE,? "+ sub_sql2+")";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getAmember_num());
			pstmt.setInt(2, board.getCategory());
			pstmt.setString(3,board.getTitle());
			pstmt.setString(4, board.getContent());
			pstmt.setInt(5, board.getAuth_num());
			
			//자주묻는 질문
			if(board.getCategory()==1) {
				pstmt.setInt(6, board.getAboard_category_num());
			}else if(board.getCategory()==2) {//일대일 질문
				pstmt.setInt(6, board.getReply_num());
			}
			pstmt.executeUpdate();
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//관리자 공지사항, 자주 묻는 질문 수정
	public void adminModifyBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		//자주 묻는 질문일때
		if(board.getCategory() ==1) {
			sub_sql = ", aboard_category_num = ? ";
		}
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE aboard SET title=?,content=?"+ sub_sql +" WHERE aboard_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt,board.getContent());
			if(board.getCategory() == 1) {
				pstmt.setInt(++cnt, board.getAboard_category_num());
			}
			pstmt.setInt(++cnt, board.getAboard_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//관리자 공지사항,자주 묻는질문, 일대일 문의사항 삭제(sql문 하나 더 사용해야한다)
	public void adminDeleteBoard(int aboard_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM aboard WHERE aboard_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,aboard_num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
}
