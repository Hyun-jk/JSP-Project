package kr.product.vo;

import kr.member.vo.MemberVO;

public class ChatVO {
	private int achat_num;
	private int aproduct_num;
	private int amember_num;
	private int opponent_num;
	private String content;
	private String send_date;
	private String read_date;
	private int read; // 읽었는지 여부, 1=읽지 않음, 2=읽음
	
	// 테이블에 없지만 UI에 필요한 정보
	private ProductVO productVO;
	private MemberVO memberVO;
	private MemberVO opponentVO;
	
	public int getAchat_num() {
		return achat_num;
	}
	public void setAchat_num(int achat_num) {
		this.achat_num = achat_num;
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
	
	// 테이블에 없지만 UI에 필요한 정보
	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public MemberVO getOpponentVO() {
		return opponentVO;
	}
	public void setOpponentVO(MemberVO opponentVO) {
		this.opponentVO = opponentVO;
	}
}