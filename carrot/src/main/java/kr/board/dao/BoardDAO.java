package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;

public class BoardDAO {

	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		
		return instance;
	};
	private BoardDAO() {}
	
	
	
	//멤버 공지사항 및 자주 묻는 질문 총목록 수
	//멤버 공지사항 및 자주 묻는 질문 총목록보기
	//멤버 공지사항 및 자주 묻는 질문 목록디테일
	//일대일 문의 목록
	//일대일 문의 입력
	//일대일 문의 삭제
	
	//관리자 공지사항 입력
	public void adminInsertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO aboard(Aboard_num,title,content,reg_date,auth,reply_num,Amember_num,category)"
					+ "VALUESE(Aboard_seq.nextval,?,?,SYSDATE,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3,board.getAuth_num());
			pstmt.setInt(4, board.getReply_num());
			pstmt.setInt(5, board.getAmember_num());
			pstmt.setInt(6, board.getCategory());
			
			
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 공지사항 수정
	//관리자 공지사항 삭제

	
	
}
