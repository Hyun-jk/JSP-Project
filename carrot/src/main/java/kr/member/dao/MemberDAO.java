package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	// 싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	// 회원 가입
	public void insertMember(MemberVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		PreparedStatement pstmt2 =  null;
		PreparedStatement pstmt3 =  null;
		ResultSet rs = null;
		String sql = null;
		int amember_num = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			// 회원 번호 생성
			sql = "SELECT amember_seq.NEXTVAL FROM DUAL";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				amember_num = rs.getInt(1);
			}
			
			// AMEMBER
			sql = "INSERT INTO amember (amember_num, id) "
				+ "VALUES(?, ?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, amember_num);
			pstmt2.setString(2, vo.getId());
			pstmt2.executeUpdate();
			
			// AMEMBER_DETAIL
			sql = "INSERT INTO amember_detail (amember_num, name, nickname, password, age, phone, address, email) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, amember_num);
			pstmt3.setString(2, vo.getName());
			pstmt3.setString(3, vo.getNickname());
			pstmt3.setString(4, vo.getPassword());
			pstmt3.setDate(5, vo.getAge());
			pstmt3.setString(6, vo.getPhone());
			pstmt3.setString(7, vo.getAddress());
			pstmt3.setString(8, vo.getEmail());
			pstmt3.executeUpdate();
			
			conn.commit();
		}
		catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	// 아이디 중복 체크 및 로그인
	public MemberVO checkMember(String id) throws Exception {
		MemberVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM amember LEFT JOIN amember_detail "
				+ "USING(amember_num) WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new MemberVO();
				vo.setAmember_num(rs.getInt("amember_num"));
				vo.setId(rs.getString("id"));
				vo.setAuth(rs.getInt("auth"));
				vo.setPassword(rs.getString("password"));
				vo.setPhoto(rs.getString("photo"));
			}	
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
	
}