package kr.product.vo;

public class CategoryVO {
	private int category; // 상품 분류 번호; 1=디지털기기, 2=생활/가전, 3=남성의류/잡화, 4=여성의류/잡화/미용, 5=유아동/유아도서, 6=스포츠, 7=생활/가구
	private String name; // 상품 분류명
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}