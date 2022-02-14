package kr.product.vo;

import java.sql.Date;

public class ProductVO {
	private int Aproduct_num; // 상품번호
	private int Amember_num;  //상품을 판매하는 회원번호
	private String photo1; 	  //상품 사진 1번파일
	private String photo2; 	  //상품 사진 2번파일
	private String photo3; 	  //상품 사진 3번파일
	private String photo4; 	  //상품 사진 4번파일
	private String photo5; 	  //상품 사진 5번파일
	private String title; 	  //상품 판매글의 제목
	private int price; 	      //상품의 가격
	private String content;   //상품 판매글의 내용
	private int category;  //상품 분류 번호
	private Date reg_date; 	  //상품 판매글의 등록일
	private Date modify_date; //상품 판매글의 수정일
	private int complete;  	  //거래 완료 여부 0=판매중, 1=거래완료
	private int buyer_num;	  //상품을 구매한 회원번호
	private int status; // 상품 표시 여부 1=미표시, 2=표시
	
	// 테이블에 없지만 필요한 정보
	private String nickname;  //상품 판매자 닉네임
	private String address; // 판매자 동네
	private double rate; // 판매자 매너 점수
	private String cname; // 상품 분류명
	private int replies; // 댓글 수
	private int chats; // 채팅 수
	private int likes; // 좋아요 수

	public int getAproduct_num() {
		return Aproduct_num;
	}
	public void setAproduct_num(int aproduct_num) {
		Aproduct_num = aproduct_num;
	}
	public int getAmember_num() {
		return Amember_num;
	}
	public void setAmember_num(int amember_num) {
		Amember_num = amember_num;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	public String getPhoto4() {
		return photo4;
	}
	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}
	public String getPhoto5() {
		return photo5;
	}
	public void setPhoto5(String photo5) {
		this.photo5 = photo5;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	public int getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(int buyer_num) {
		this.buyer_num = buyer_num;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	// 테이블에 없지만 필요한 정보
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getReplies() {
		return replies;
	}
	public void setReplies(int replies) {
		this.replies = replies;
	}
	public int getChats() {
		return chats;
	}
	public void setChats(int chats) {
		this.chats = chats;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
}




