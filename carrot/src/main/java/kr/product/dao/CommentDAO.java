package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.product.vo.CommentVO;
import kr.util.DBUtil;

public class CommentDAO {
	// 싱글턴 패턴
	private static CommentDAO instance = new CommentDAO();
	public static CommentDAO getInstance() {
		return instance;
	}
	private CommentDAO() {}
	
	// 댓글/대댓글 작성
	public void insertComment(CommentVO comment) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			boolean exist = comment.getAcomment_parent()==null ? false : true;
			
			sql = "INSERT INTO acomment (acomment_num, amember_num, aproduct_num, content";
			sql += exist ? ", acomment_parent) " : ") ";
			sql += "VALUES (acomment_seq.NEXTVAL, ?, ?, ?";
			sql += exist ? ", ?)" : ")";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, comment.getAmember_num());
			pstmt.setInt(2, comment.getAproduct_num());
			pstmt.setString(3, comment.getContent());
			if(exist) pstmt.setInt(4, comment.getAcomment_parent());
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 물품별 댓글 수 구하기
	public int getCountComment(int aproduct_num) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(acomment_num) FROM acomment "
				+ "WHERE aproduct_num=? AND acomment_parent IS NULL";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			
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
	
	// 물품별 댓글 목록
	public List<CommentVO> getListComment(int aproduct_num, int startCount, int endCount) throws Exception {
		List<CommentVO> comments = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT c.*, ROWNUM AS rnum FROM (SELECT * "
					+ "FROM acomment JOIN amember_detail USING(amember_num) "
					+ "WHERE aproduct_num=? AND acomment_parent IS NULL ORDER BY acomment_num DESC) c) "
				+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, startCount);
			pstmt.setInt(3, endCount);
			
			rs = pstmt.executeQuery();
			comments = new ArrayList<CommentVO>();
			while(rs.next()) {
				CommentVO comment = new CommentVO();
				// 댓글 정보 저장
				comment.setAcomment_num(rs.getInt("acomment_num"));
				comment.setAmember_num(rs.getInt("amember_num"));
				comment.setAproduct_num(rs.getInt("aproduct_num"));
				comment.setContent(rs.getString("content"));
				comment.setReg_date(rs.getString("reg_date"));
				comment.setModify_date(rs.getString("modify_date"));
				comment.setAcomment_parent(rs.getInt("acomment_parent"));
				comment.setDeleted(rs.getInt("deleted")); // 대댓글이 있는 부모 댓글의 경우, 행은 보존하고 deleted 값으로 삭제 여부를 나타냄
				// 댓글 작성 회원 정보 저장
				MemberVO member = new MemberVO();
				member.setNickname(rs.getString("nickname"));
				member.setPhoto(rs.getString("photo"));
				member.setAddress(rs.getString("address"));
				comment.setMemberVO(member);
				comments.add(comment);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return comments;
	}
	
	// 대댓글 목록
	public List<CommentVO> getListReply(int aproduct_num, int acomment_parent) throws Exception {
		List<CommentVO> replies = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM acomment JOIN amember_detail USING(amember_num) "
				+ "WHERE aproduct_num=? AND acomment_parent=? ORDER BY acomment_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, acomment_parent);

			rs = pstmt.executeQuery();
			replies = new ArrayList<CommentVO>();
			while(rs.next()) {
				CommentVO reply = new CommentVO();
				// 대댓글 정보 저장
				reply.setAcomment_num(rs.getInt("acomment_num"));
				reply.setAmember_num(rs.getInt("amember_num"));
				reply.setAproduct_num(rs.getInt("aproduct_num"));
				reply.setContent(rs.getString("content"));
				reply.setReg_date(rs.getString("reg_date"));
				reply.setModify_date(rs.getString("modify_date"));
				reply.setAcomment_parent(rs.getInt("acomment_parent"));
				// 대댓글 작성 회원 정보 저장
				MemberVO member = new MemberVO();
				member.setNickname(rs.getString("nickname"));
				member.setPhoto(rs.getString("photo"));
				member.setAddress(rs.getString("address"));
				reply.setMemberVO(member);
				replies.add(reply);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return replies;
	}	
	
	// 댓글 작성자 회원 번호 구하기
	public int getMember(int acomment_num) throws Exception {
		int amember_num = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT amember_num FROM acomment WHERE acomment_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, acomment_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				amember_num = rs.getInt("amember_num");	
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return amember_num;
	}
	
	// 댓글 수정 시간 구하기
	public String getModify_date(int acomment_num) throws Exception {
		String modify_date = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT modify_date FROM acomment WHERE acomment_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, acomment_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				modify_date = rs.getString("modify_date");	
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return modify_date;
	}
	
	// 댓글 수정
	public void updateComment(CommentVO comment) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE acomment SET content=?, modify_date=SYSDATE WHERE acomment_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getAcomment_num());
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	// 특정 댓글의 대댓글 존재 여부 확인
	public boolean existsReply(int acomment_num) throws Exception {
		boolean exist = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(acomment_num) FROM acomment WHERE acomment_parent=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, acomment_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)>0) exist = true;
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return exist;
	}
	
	// 댓글 삭제
	public void deleteComment(int acomment_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			boolean exist = existsReply(acomment_num);
			if(!exist) sql = "DELETE FROM acomment WHERE acomment_num=?"; // 대댓글이 없으면 댓글을 삭제
			else sql = "UPDATE acomment SET deleted=1 WHERE acomment_num=?"; // 대댓글이 있으면 댓글의 deleted 값만 변경
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, acomment_num);
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}