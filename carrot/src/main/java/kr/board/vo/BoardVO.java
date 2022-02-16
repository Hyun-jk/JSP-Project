package kr.board.vo;

import java.sql.Date;

public class BoardVO {
 
	private int Aboard_num;//글 번호
	private int Amember_num;//회원 번호
	private int auth_num;//회원 등급
	private int category;//카테고리 0:공지, 1:FAQ, 2:일대일문의
	private String title;//글 제목
	private String content;//내용
	private Date reg_date;//가입일
	private int reply_num;//댓글 번호
	private int Aboard_category_num;
	
	public int getAboard_category_num() {
		return Aboard_category_num;
	}
	public void setAboard_category_num(int aboard_category_num) {
		Aboard_category_num = aboard_category_num;
	}
	public int getAboard_num() {
		return Aboard_num;
	}
	public void setAboard_num(int aboard_num) {
		Aboard_num = aboard_num;
	}
	public int getAmember_num() {
		return Amember_num;
	}
	public void setAmember_num(int amember_num) {
		Amember_num = amember_num;
	}
	public int getAuth_num() {
		return auth_num;
	}
	public void setAuth_num(int auth_num) {
		this.auth_num = auth_num;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
}
