package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatRoomVO;
import kr.chat.vo.ChatVO;
import kr.member.vo.MemberVO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class ChatDAO {
	// 싱글턴 패턴
	private static ChatDAO instance = new ChatDAO();
	public static ChatDAO getInstance() {
		return instance;
	}
	private ChatDAO() {}
	
	// 채팅방 생성하기
	public void insertChatRoom(int aproduct_num, int seller_num, int buyer_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO achatroom (achatroom_num, aproduct_num, seller_num, buyer_num) "
				+ "VALUES (achatroom_seq.NEXTVAL, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, seller_num);
			pstmt.setInt(3, buyer_num);
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 채팅방 존재 확인하기
	public boolean existsChatRoom(int aproduct_num, int seller_num, int buyer_num) throws Exception {
		boolean exist = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT achatroom_num FROM achatroom WHERE aproduct_num=? AND seller_num=? AND buyer_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, seller_num);
			pstmt.setInt(3, buyer_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				exist = true;
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
	
	// 특정 채팅방 번호 가져오기
	public int getChatRoom(int aproduct_num, int seller_num, int buyer_num) throws Exception {
		int achatroom_num = -1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT achatroom_num FROM achatroom WHERE aproduct_num=? AND seller_num=? AND buyer_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, aproduct_num);
			pstmt.setInt(2, seller_num);
			pstmt.setInt(3, buyer_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				achatroom_num = rs.getInt(1);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return achatroom_num;
	}
	
	// 특정 채팅방 정보 불러오기
	public ChatRoomVO getChatRoom(int achatroom_num) throws Exception {
		ChatRoomVO chatroom = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT c.*, p.title, p.price, p.photo1, p.status, p.complete, p.buyer_num AS p_buyer_num, "
				+ "s.nickname AS s_nickname, s.address AS s_address, s.rate AS s_rate, s.photo AS s_photo, "
				+ "b.nickname AS b_nickname, b.address AS b_address, b.rate AS b_rate, b.photo AS b_photo "
				+ "FROM achatroom c JOIN aproduct p ON c.aproduct_num=p.aproduct_num "
				+ "JOIN amember_detail s ON c.seller_num=s.amember_num "
				+ "JOIN amember_detail b ON c.buyer_num=b.amember_num "
				+ "WHERE achatroom_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, achatroom_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chatroom = new ChatRoomVO();
				// 채팅방 정보 저장
				chatroom.setAchatroom_num(achatroom_num);
				chatroom.setAproduct_num(rs.getInt("aproduct_num"));
				chatroom.setSeller_num(rs.getInt("seller_num"));
				chatroom.setBuyer_num(rs.getInt("buyer_num")); // 구매(희망)자 회원 번호
				// 물품 정보 저장
				ProductVO product = new ProductVO();
				product.setTitle(rs.getString("title"));
				product.setPrice(rs.getInt("price"));
				product.setPhoto1(rs.getString("photo1"));
				product.setStatus(rs.getInt("status"));
				product.setComplete(rs.getInt("complete"));
				product.setBuyer_num(rs.getInt("p_buyer_num")); // 거래 완료된 경우의 실제 구매자 회원 번호
				chatroom.setProductVO(product);
				// 물품 판매자 정보 저장
				MemberVO seller = new MemberVO();
				seller.setNickname(rs.getString("s_nickname"));
				seller.setAddress(rs.getString("s_address"));
				if(rs.getString("s_rate")!=null) seller.setRate(rs.getDouble("s_rate"));
				seller.setPhoto(rs.getString("s_photo"));
				chatroom.setSellerVO(seller);
				// 물품 구매(희망)자 정보 저장
				MemberVO buyer = new MemberVO();
				buyer.setNickname(rs.getString("b_nickname"));
				buyer.setAddress(rs.getString("b_address"));
				if(rs.getString("b_rate")!=null) buyer.setRate(rs.getDouble("b_rate"));
				buyer.setPhoto(rs.getString("b_photo"));
				chatroom.setBuyerVO(buyer);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return chatroom;
	}
	
	// 특정 채팅방에서 메시지 보내기
	public void sendChat(ChatVO chat) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO achat (achat_num, achatroom_num, aproduct_num, amember_num, opponent_num, content) "
				+ "VALUES (achat_seq.NEXTVAL, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, chat.getAchatroom_num());
			pstmt.setInt(2, chat.getAproduct_num());
			pstmt.setInt(3, chat.getAmember_num());
			pstmt.setInt(4, chat.getOpponent_num());
			pstmt.setString(5, chat.getContent());
			
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 특정 채팅방에서 주고 받은 메시지 수 구하기
	public int getCountChat(int achatroom_num) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(achat_num) FROM achat WHERE achatroom_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, achatroom_num);
			
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
	
	// 특정 채팅방에서 주고 받은 메시지 목록을 불러오고, 로그인한 회원이 받은 메시지는 읽음 처리하기
	public List<ChatVO> getListChat(int achatroom_num, int amember_num, int startCount, int endCount) throws Exception {
		List<ChatVO> chats = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
		
			// 특정 채팅방에서 주고 받은 메시지 목록 불러오기
			sql = "SELECT * FROM (SELECT c.*, ROWNUM AS rnum "
					+ "FROM (SELECT * FROM achat WHERE achatroom_num=? ORDER BY achat_num DESC) c) "
				+ "WHERE rnum>=? AND rnum<=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, achatroom_num);
			pstmt.setInt(2, startCount);
			pstmt.setInt(3, endCount);
			rs = pstmt.executeQuery();
			chats = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				chat.setAchat_num(rs.getInt("achat_num"));
				chat.setAchatroom_num(rs.getInt("achatroom_num"));
				chat.setAproduct_num(rs.getInt("aproduct_num"));
				chat.setAmember_num(rs.getInt("amember_num"));
				chat.setOpponent_num(rs.getInt("opponent_num"));
				chat.setContent(rs.getString("content"));
				chat.setSend_date(rs.getString("send_date"));
				chat.setRead_date(rs.getString("read_date"));
				chat.setRead(rs.getInt("read"));
				chats.add(chat);
			}
			
			// 로그인한 회원이 받은 메시지 읽음 처리하기
			sql = "UPDATE achat SET read=2, read_date=SYSDATE "
				+ "WHERE achatroom_num=? AND opponent_num=? AND achat_num<? AND read=1";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, achatroom_num);
			pstmt2.setInt(2, amember_num); // 로그인한 회원이 받은 메시지를 검색
			pstmt2.setInt(3, chats.get(0).getAchat_num()+1); // 불러온 메시지 중 가장 최신 메시지의 번호 구하기
			pstmt2.executeUpdate();
			
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
		
		return chats;
	}
	
	// 특정 채팅방에서 안 읽은 메시지 수 구하기
	public int getCountUnread(int achatroom_num, int amember_num) throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(achat_num) FROM achat WHERE achatroom_num=? AND opponent_num=? AND read=1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, achatroom_num);
			pstmt.setInt(2, amember_num); // 로그인한 회원이 받은 메시지를 검색
			
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
	
	// 회원별로 채팅방 목록과 각 채팅방의 가장 최근 메시지 1건 불러오기
	public List<ChatRoomVO> getListChatRoom(int amember_num, String filter) throws Exception {
		List<ChatRoomVO> chatrooms = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		
		try {
			conn = DBUtil.getConnection();
			
			if(filter!=null && !filter.isEmpty()) {
				if(filter.equals("2")) sub_sql = "AND p.status=2 AND p.complete=0 "; // 거래 중
				else if(filter.equals("3")) sub_sql = "AND p.status=2 AND ((p.amember_num=21 OR p.buyer_num=21) AND p.complete=1) "; // 거래 완료
			}
			
			sql = "SELECT * FROM achat JOIN (SELECT MAX(achat_num) AS achat_num FROM achat "
					+ "JOIN (SELECT c.achatroom_num, p.status, p.complete FROM achatroom c "
						+ "JOIN aproduct p ON c.aproduct_num=p.aproduct_num "
						+ "WHERE (c.seller_num=? OR c.buyer_num=?) " + sub_sql
					+ ") USING(achatroom_num) GROUP BY achatroom_num) "
				+ "USING(achat_num) ORDER BY achat_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, amember_num); // 로그인한 회원이 판매자로 참여하고 있는 채팅방 검색
			pstmt.setInt(2, amember_num); // 로그인한 회원이 구매자로 참여하고 있는 채팅방 검색
			
			rs = pstmt.executeQuery();
			chatrooms = new ArrayList<ChatRoomVO>();
			while(rs.next()) {
				int achatroom_num = rs.getInt("achatroom_num");
				ChatRoomVO chatroom = getChatRoom(achatroom_num);
				chatroom.setLatest_chat(rs.getString("content"));
				chatroom.setLatest_date(rs.getString("send_date"));
				chatroom.setUnread(getCountUnread(achatroom_num, amember_num));
				chatrooms.add(chatroom);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return chatrooms;
	}
	
	// 회원별로 가장 최근에 받은 메시지 1건 불러오기
	public int getLatestChat(int amember_num) throws Exception {
		int latest_chat = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT MAX(achat_num) FROM achat WHERE opponent_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, amember_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				latest_chat = rs.getInt(1);
			}
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return latest_chat;
	}
	
}