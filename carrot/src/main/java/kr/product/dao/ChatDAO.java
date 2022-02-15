package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.product.vo.ChatVO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class ChatDAO {
	// 싱글턴 패턴
	private static ChatDAO instance = new ChatDAO();
	public static ChatDAO getInstance() {
		return instance;
	}
	private ChatDAO() {}

	// 특정 상품에서 메시지 보내기
	public void sendChat(ChatVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO achat (aproduct_num, amember_num, opponent_num, content) "
				+ "VALUES (?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getAproduct_num());
			pstmt.setInt(2, vo.getAmember_num());
			pstmt.setInt(3, vo.getOpponent_num());
			pstmt.setString(4, vo.getContent());
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 특정 상품에서 주고 받은 메시지 수 구하기
	public int getCountChat(ChatVO vo) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(*) FROM achat WHERE (amember_num=? OR opponent_num=?) "
				+ "AND aproduct_num=? AND read<? ORDER BY send_date DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getAmember_num()); // 로그인한 회원이 보낸 메시지
			pstmt.setInt(2, vo.getAmember_num()); // 로그인한 회원이 받은 메시지
			pstmt.setInt(3, vo.getAproduct_num());
			pstmt.setInt(4, vo.getRead());
			
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
	
	// 특정 상품에서 주고 받은 메시지 불러오기
	public List<ChatVO> getListChat(int aproduct_num, int amember_num, int read, int startCount, int endCount) throws Exception {
		List<ChatVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT r.*, ROWNUM AS rnum "
					// 로그인한 사용자 정보 결합
					+ "FROM (SELECT c.*, m.nickname, m.photo, m.rate, "
					// 상대방 정보 결합
					+ "o.nickname AS o_nickname, o.photo AS o_photo, o.rate AS o_rate, "
					// 상품 정보 결합
					+ "p.title, p.photo1, p.price "
					+ "FROM achat c JOIN amember_detail m ON c.amember_num=m.amember_num "
					+ "JOIN amember_detail o ON c.opponent_num=o.amember_num "
					+ "JOIN aproduct p ON c.aproduct_num=p.aproduct_num "
					// 회원 번호, 상품 번호 및 읽음 여부 조건절
					+ "WHERE (c.amember_num=? OR c.opponent_num=?) "
					+ "AND c.aproduct_num=? AND c.read<? "
					// 최신순 정렬
					+ "ORDER BY c.send_date DESC) r) "
				+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, amember_num); // 로그인한 회원이 보낸 메시지
			pstmt.setInt(2, amember_num); // 로그인한 회원이 받은 메시지
			pstmt.setInt(3, aproduct_num);
			pstmt.setInt(4, read);
			pstmt.setInt(5, startCount);
			pstmt.setInt(6, endCount);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				// 채팅 정보 저장
				chat.setAproduct_num(rs.getInt("aproduct_num"));
				chat.setAmember_num(rs.getInt("amember_num"));
				chat.setOpponent_num(rs.getInt("opponent_num"));
				chat.setContent(rs.getString("content"));
				chat.setSend_date(rs.getString("send_date"));
				chat.setRead_date(rs.getString("read_date"));
				chat.setRead(rs.getInt("read"));
				
				// 로그인한 사용자 정보 저장
				MemberVO member = new MemberVO();
				member.setNickname(rs.getString("nickname"));
				member.setPhoto(rs.getString("photo"));
				member.setRate(rs.getDouble("rate"));
				chat.setMemberVO(member);
				// 상대방 정보 저장
				MemberVO opponent = new MemberVO();
				opponent.setNickname(rs.getString("o_nickname"));
				opponent.setPhoto(rs.getString("o_photo"));
				opponent.setRate(rs.getDouble("o_rate"));
				chat.setOpponentVO(opponent);
				// 채팅 중인 상품 정보 저장
				ProductVO product = new ProductVO();
				product.setTitle(rs.getString("title"));
				product.setPhoto1(rs.getString("photo1"));
				product.setPrice(rs.getInt("price"));
				chat.setProductVO(product);
				
				list.add(chat);
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
	
	// 메시지 읽음 처리
	
	// 안 읽은 메시지 수
	
	// 회원별 메시지함
	
}