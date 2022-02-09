package kr.product.vo;

import java.sql.Date;

public class ProductVO {
	private int Aproduct_num; 
	private int Amember_num;
	private String photo1; 
	private String photo2; 
	private String photo3; 
	private String photo4; 
	private String photo5; 	
	private String title; 
	private String price; 
	private String content; 
	private String category; 
	private Date reg_date; 
	private Date modify_date; 
	private int complete; 
	private int buyer_num;

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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
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

}




