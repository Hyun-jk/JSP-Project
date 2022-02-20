package kr.my.vo;

import kr.member.vo.MemberVO;

public class AmannerVO {
	private int Amanner_num; //매너 평가를 식별하는 번호
	private int Amember_num; //판매자 회원번호
	private int Aproudct_num; //상품을 식별하는 번호
	private int rate; //거래후 구매자가 평가한 판매자 매너점수
	private String review; //판매자 매너에 대한 평가/후기
	private int buyer_num; // 구매자 회원번호(판매자가 받은 거래 후기 목록에서 아이디 일부 노출)
	private float totalRate; //매너점수 계산
	
	private MemberVO member; //리뷰 남긴 사람 정보(닉네임,프로필사진 표시를 위해서)
	
	public int getAmanner_num() {
		return Amanner_num;
	}
	public void setAmanner_num(int amanner_num) {
		Amanner_num = amanner_num;
	}
	public int getAmember_num() {
		return Amember_num;
	}
	public void setAmember_num(int amember_num) {
		Amember_num = amember_num;
	}
	public int getAproudct_num() {
		return Aproudct_num;
	}
	public void setAproudct_num(int aproudct_num) {
		Aproudct_num = aproudct_num;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(int buyer_num) {
		this.buyer_num = buyer_num;
	}
	public double getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(float totalRate) {
		this.totalRate = totalRate;
	}
	public MemberVO getMember() {
		return member;
	}
	public void setMember(MemberVO member) {
		this.member = member;
	}
}
