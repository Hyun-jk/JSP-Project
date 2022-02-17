package kr.chat.vo;

public class ChatVO {
	private int achat_num; // 메시지 번호
	private int achatroom_num; // 채팅방 번호
	private int aproduct_num; // 채팅 중인 물품 번호
	private int amember_num; // 메시지 보낸 회원 번호
	private int opponent_num; // 메시지 받은 회원 번호
	private String content; // 메시지 내용
	private String send_date; // 메시지 발신 시각
	private String read_date; // 메시지 수신 시각
	private int read; // 읽었는지 여부, 1=읽지 않음, 2=읽음
	
	public int getAchat_num() {
		return achat_num;
	}
	public void setAchat_num(int achat_num) {
		this.achat_num = achat_num;
	}
	public int getAchatroom_num() {
		return achatroom_num;
	}
	public void setAchatroom_num(int achatroom_num) {
		this.achatroom_num = achatroom_num;
	}
	public int getAproduct_num() {
		return aproduct_num;
	}
	public void setAproduct_num(int aproduct_num) {
		this.aproduct_num = aproduct_num;
	}
	public int getAmember_num() {
		return amember_num;
	}
	public void setAmember_num(int amember_num) {
		this.amember_num = amember_num;
	}
	public int getOpponent_num() {
		return opponent_num;
	}
	public void setOpponent_num(int opponent_num) {
		this.opponent_num = opponent_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getRead_date() {
		return read_date;
	}
	public void setRead_date(String read_date) {
		this.read_date = read_date;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
}
