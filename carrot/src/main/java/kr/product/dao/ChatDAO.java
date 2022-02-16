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
			
			sql = "SELECT * FROM (SELECT r.*, ROWNUM AS rnum FROM (SELECT * FROM achat "
					// 회원 번호, 상품 번호 및 읽음 여부 조건절
					+ "WHERE (amember_num=? OR opponent_num=?) AND aproduct_num=? AND read<? "
					// 최신순 정렬
					+ "ORDER BY send_date DESC) r) "
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

	// 채팅 중인 상대방과 물품 정보 불러오기
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
			
			// 판매자 정보 및 물품 정보 불러오기
			sql = "SELECT p.*, m.nickname, m.photo, m.rate "
				+ "FROM aproduct p JOIN amember_detail m ON p.amember_num=m.amember_num "
				+ "WHERE aproduct_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, aproduct_num);
			rs = pstmt.executeQuery();
			chat = new ChatVO();
			if(rs.next()) {
				chat.setAproduct_num(aproduct_num);
				// 판매자 정보 저장
				MemberVO member = new MemberVO();
				member.setNickname(rs.getString("nickname"));
				member.setPhoto(rs.getString("photo"));
				member.setRate(rs.getDouble("rate"));
				chat.setOpponentVO(member);
				// 채팅 중인 물품 정보 저장
				ProductVO product = new ProductVO();
				product.setTitle(rs.getString("title"));
				product.setPhoto1(rs.getString("photo1"));
				product.setPrice(rs.getInt("price"));
				product.setAmember_num(rs.getInt("amember_num"));
				Integer buyer_num = rs.getInt("buyer_num");
				product.setBuyer_num(buyer_num);
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
				// 채팅 중인 상대방 정보 저장
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
	
	// 메시지 읽음 처리
	
	// 안 읽은 메시지 수
	
	// 회원별 메시지함
	
}