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

	// 특정 물품에서 메시지 보내기
	public void sendChat(ChatVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO achat (achat_num, aproduct_num, amember_num, opponent_num, content) "
				+ "VALUES (achat_seq.NEXTVAL, ?, ?, ?, ?)";
			
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
	
	// 특정 물품에서 주고 받은 메시지 수 구하기
	public int getCountChat(int aproduct_num, int amember_num) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(*) FROM achat WHERE aproduct_num=? "
				+ "AND (amember_num=? OR opponent_num=?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, amember_num); // 로그인한 회원이 보낸 메시지
			pstmt.setInt(3, amember_num); // 로그인한 회원이 받은 메시지
			
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
	
	// 특정 물품에서 주고 받은 메시지 불러오기
	public List<ChatVO> getListChat(int aproduct_num, int amember_num, int startCount, int endCount) throws Exception {
		List<ChatVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			// 주고 받은 메시지 불러오기
			sql = "SELECT * FROM (SELECT r.*, ROWNUM AS rnum FROM (SELECT * FROM achat "
					+ "WHERE aproduct_num=? AND (amember_num=? OR opponent_num=?) "
					+ "ORDER BY achat_num DESC) r) "
				+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, amember_num); // 로그인한 회원이 보낸 메시지
			pstmt.setInt(3, amember_num); // 로그인한 회원이 받은 메시지
			pstmt.setInt(4, startCount);
			pstmt.setInt(5, endCount);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				// 채팅 정보 저장
				chat.setAchat_num(rs.getInt("achat_num"));
				chat.setAproduct_num(rs.getInt("aproduct_num"));
				chat.setAmember_num(rs.getInt("amember_num"));
				chat.setOpponent_num(rs.getInt("opponent_num"));
				chat.setContent(rs.getString("content"));
				chat.setSend_date(rs.getString("send_date"));
				chat.setRead_date(rs.getString("read_date"));
				chat.setRead(rs.getInt("read"));				
				list.add(chat);
			}
			
			// 불러온 메시지가 있으면 읽음 처리하기
			if(list!=null) {
				int achat_last = list.get(0).getAchat_num(); // 최신 메시지의 PK 구하기
				
				sql = "UPDATE achat SET read=2, read_date=SYSDATE "
					+ "WHERE aproduct_num=? AND opponent_num=? AND achat_num<? AND read=1";
				
				pstmt2 = conn.prepareStatement(sql);
				
				pstmt2.setInt(1, aproduct_num);
				pstmt2.setInt(2, amember_num); // 로그인한 회원이 받은 메시지
				pstmt2.setInt(3, achat_last+1);
				
				pstmt2.executeUpdate();
			}
			
			conn.commit();
		}
		catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

	// 채팅 중인 물품과 상대방 정보 불러오기
	public ChatVO getChatVO(int aproduct_num, int opponent_num) throws Exception {
		ChatVO chat = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			// 채팅 중인 물품 정보 불러오기
			sql = "SELECT * FROM aproduct WHERE aproduct_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			
			rs = pstmt.executeQuery();
			chat = new ChatVO();
			if(rs.next()) {
				chat.setAproduct_num(aproduct_num);

				ProductVO product = new ProductVO();
				product.setTitle(rs.getString("title"));
				product.setPhoto1(rs.getString("photo1"));
				product.setPrice(rs.getInt("price"));
				product.setAmember_num(rs.getInt("amember_num"));
				product.setBuyer_num(rs.getInt("buyer_num"));
				product.setComplete(rs.getInt("complete"));
				product.setStatus(rs.getInt("status"));
				
				chat.setProductVO(product);
			}
			
			// 채팅 중인 상대방 정보 불러오기
			sql = "SELECT nickname, photo, rate FROM amember_detail WHERE amember_num=?";
			
			pstmt2 = conn.prepareStatement(sql);
			
			pstmt2.setInt(1, opponent_num);
			
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
				chat.setOpponent_num(opponent_num);

				MemberVO opponent = new MemberVO();
				opponent.setNickname(rs2.getString("nickname"));
				opponent.setPhoto(rs2.getString("photo"));
				opponent.setRate(rs2.getDouble("rate"));
				
				chat.setOpponentVO(opponent);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs2, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return chat;
	}
	
	// 회원별로 채팅 중인 상대방 목록 불러오기
	public List<ChatVO> getListChatByUser(int amember_num) throws Exception {
		List<ChatVO> list = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 안 읽은 메시지 수 구하기
	public int getCountUnread(int aproduct_num, int amember_num) throws Exception {
		int unread = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(*) FROM achat WHERE aproduct_num=? AND opponent_num=? AND read=1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, amember_num); // 로그인한 회원이 받은 메시지
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				unread = rs.getInt(1);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return unread;
	}
	
}