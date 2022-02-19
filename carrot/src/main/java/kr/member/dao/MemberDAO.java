package kr.member.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
				vo.setNickname(rs.getString("nickname"));
				vo.setPhoto(rs.getString("photo"));
				vo.setAddress(rs.getString("address"));
				vo.setAddress_favor(rs.getString("address_favor"));
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
	
	//회원 상세정보 
			public MemberVO getMember(int amember_num)throws Exception{
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MemberVO vo =null;
			String sql =null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM amember m JOIN amember_detail d "
					+ "ON m.amember_num=d.amember_num WHERE m.amember_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, amember_num);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new MemberVO();
					vo.setAmember_num(rs.getInt("amember_num"));
					vo.setAuth(rs.getInt("auth"));
					vo.setId(rs.getString("id"));
					vo.setPassword(rs.getString("password"));
					vo.setName(rs.getString("name"));
					vo.setNickname(rs.getString("nickname"));
					vo.setAge(rs.getDate("age"));
					vo.setPhone(rs.getString("phone"));
					vo.setAddress(rs.getString("address"));
					vo.setAddress_favor(rs.getString("address_favor"));
					vo.setEmail(rs.getString("email"));
					vo.setPhoto(rs.getString("photo"));
					vo.setRate(rs.getDouble("rate"));
					vo.setReg_date(rs.getDate("reg_date"));	
					
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return vo;
		}
		//회원정보수정
			public void updateMember(MemberVO member)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				String sql = null;
				
				try {
					
					conn = DBUtil.getConnection();
					sql = "UPDATE amember_detail SET nickname=?,phone=?,email=?, "
						+ "address=?,address_favor=? "
						+ "WHERE amember_num=?";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, member.getNickname());
					pstmt.setString(2, member.getPhone());
					pstmt.setString(3, member.getEmail());
					pstmt.setString(4, member.getAddress());
					pstmt.setString(5, member.getAddress_favor());
					pstmt.setInt(6, member.getAmember_num());
					
					pstmt.executeUpdate();
					
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					//자원정리
					DBUtil.executeClose(null, pstmt, conn);
				}
			}
			//비밀번호 수정
			public void updatePassword(String password, int amember_num)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				String sql = null;
				
				try {
					conn = DBUtil.getConnection();
					sql = "UPDATE amember_detail SET password=? WHERE amember_num=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, password);
					pstmt.setInt(2, amember_num);
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (e);
				}finally {
					DBUtil.executeClose(null, pstmt, conn);
					
				}
			}
			//프로필 사진 수정
			public void updateMyPhoto(String photo, int amember_num)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				String sql =null;

				try {
					conn = DBUtil.getConnection();
					sql="UPDATE Amember_detail SET photo=? WHERE amember_num=?";
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, photo);
					pstmt.setInt(2, amember_num);

					pstmt.executeUpdate();

				}catch(Exception e){
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(null, pstmt, conn);

				}
			}	
			//회원 탈퇴
			public void deleteMember(int amember_num) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				String sql = null;
				
				try {
					conn= DBUtil.getConnection();
					conn.setAutoCommit(false);
					
					sql="UPDATE amember SET auth=0 WHERE amember_num=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,amember_num);
					pstmt.executeUpdate();
					
					sql="DELETE FROM amember_detail WHERE amember_num=?";
					pstmt2=conn.prepareStatement(sql);
					pstmt2.setInt(1, amember_num);
					pstmt.executeUpdate();
					
					conn.commit();
				}catch(Exception e){
					conn.rollback();
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(null, pstmt, null);
					DBUtil.executeClose(null, pstmt2, conn);
				}
				
				
			}
	
			
		//관리자
			//총 회원 수
			public int getMemberCountByAdmin(String keyfield, String keyword) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				String sub_sql = "";
				int count = 0;
				
				try {
					conn = DBUtil.getConnection();
					
					if(keyword != null && !"".equals(keyword)) {
						if(keyfield.equals("1")) sub_sql = "WHERE id LIKE ?";
						else if(keyfield.equals("2")) sub_sql = "WHERE name LIKE ?";
						else if(keyfield.equals("3")) sub_sql = "WHERE email LIKE ?";
					}
					sql="SELECT COUNT(*) FROM amember m "
					   + "LEFT OUTER JOIN amember_detail d USING(amember_num)" + sub_sql;
					pstmt = conn.prepareStatement(sql);
					if(keyword != null && !"".equals(keyword)) {
						pstmt.setString(1, "%" + keyword + "%");
					}
					rs = pstmt.executeQuery();
					if(rs.next()) {
						count = rs.getInt(1);
					}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				
				return count;
			}
			//회원 목록 
			public List<MemberVO> getListMemberByAdmin(int startRow, int endRow,
					                             String keyfield, String keyword)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<MemberVO> list = null;
				String sql = null;
				String sub_sql = "";
				int cnt = 0;
				
				try {
					conn = DBUtil.getConnection();
					
					if(keyword != null && !"".equals(keyword)) {
						if(keyfield.equals("1")) sub_sql = "WHERE id LIKE ?";
						else if(keyfield.equals("2")) sub_sql = "WHERE name LIKE ?";
						else if(keyfield.equals("3")) sub_sql = "WHERE email LIKE ?";
					}
					sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					    + "(SELECT * FROM amember m LEFT OUTER JOIN amember_detail d "
					    + "USING(amember_num) "+ sub_sql + "ORDER BY reg_date DESC NULLS LAST)a) "
					    + "WHERE rnum >=? AND rnum <= ?";
					
					pstmt = conn.prepareStatement(sql);
					if(keyword != null && !"".equals(keyword)) {
						pstmt.setString(++cnt, "%" + keyword + "%");
					}
					pstmt.setInt(++cnt, startRow);
					pstmt.setInt(++cnt, endRow);
					
					rs = pstmt.executeQuery();
					list = new ArrayList<MemberVO>();
					while(rs.next()) {
						MemberVO vo = new MemberVO();
						vo.setAmember_num(rs.getInt("amember_num"));
						vo.setId(rs.getString("id"));
						vo.setAuth(rs.getInt("auth"));
						vo.setName(rs.getString("name"));
						vo.setNickname(rs.getString("nickname"));
						vo.setPassword(rs.getString("password"));
						vo.setPhone(rs.getString("phone"));
						vo.setEmail(rs.getString("email"));
						vo.setAddress(rs.getString("address"));
						vo.setAddress_favor(rs.getString("address_favor"));
						vo.setPhoto(rs.getString("photo"));
						vo.setReg_date(rs.getDate("reg_date"));
						
						list.add(vo);
					}
					
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				
				return list;
			}
			//회원 정보 수정
			public void updateMemberByAdmin(MemberVO vo)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				String sql = null;
				
				try {
					//커넥션풀로부터 커넥션을 할당
					conn = DBUtil.getConnection();

					conn.setAutoCommit(false);
					
					sql = "UPDATE amember SET auth=? WHERE amember_num=?";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, vo.getAuth());
					pstmt.setInt(2, vo.getAmember_num());
					
					pstmt.executeUpdate();
					
					sql = "UPDATE amember_detail SET name=?,phone=?,email=?,"
						+ "address=?,address_favor=? "
						+ "WHERE amember_num=?";
					
					pstmt2 = conn.prepareStatement(sql);
					
					pstmt2.setString(1, vo.getName());
					pstmt2.setString(2, vo.getPhone());
					pstmt2.setString(3, vo.getEmail());
					pstmt2.setString(4, vo.getAddress());
					pstmt2.setString(5, vo.getAddress_favor());
					pstmt2.setInt(6, vo.getAmember_num());
					
					pstmt2.executeUpdate();
					
					
					conn.commit();
				}catch(Exception e) {
					
					conn.rollback();
					throw new Exception(e);
				}finally {
					//자원정리
					DBUtil.executeClose(null, pstmt2, null);
					DBUtil.executeClose(null, pstmt, conn);
				}
			}
			
			
			
			
} 


