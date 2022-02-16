package kr.board.vo;

public class CategoryVO {
	private int Aboard_category_num; // 자주묻는 질문 카테고리  1:운영정책 , 2:구매/판매 3:거래매너 4, 이용제재
	private String Aboard_category_name; // 상품 분류명
	
	public int getAboard_category_num() {
		return Aboard_category_num;
	}
	public void setAboard_category_num(int board_category_num) {
		this.Aboard_category_num = board_category_num;
	}
	public String getAboard_category_name() {
		return Aboard_category_name;
	}
	public void setAboard_category_name(String board_category_name) {
		this.Aboard_category_name = board_category_name;
	}
	
}
