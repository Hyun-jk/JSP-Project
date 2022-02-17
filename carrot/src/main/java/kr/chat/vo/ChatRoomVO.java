package kr.chat.vo;

import kr.member.vo.MemberVO;
import kr.product.vo.ProductVO;

public class ChatRoomVO {
	private int achatroom_num;
	private int aproduct_num;
	private int seller_num;
	private int buyer_num;
	
	// 테이블에 없지만 UI에 필요한 정보
	private ProductVO productVO;
	private MemberVO buyerVO;
	private MemberVO sellerVO;
	private String latest_chat;
	private String latest_date;
	
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
	public int getSeller_num() {
		return seller_num;
	}
	public void setSeller_num(int seller_num) {
		this.seller_num = seller_num;
	}
	public int getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(int buyer_num) {
		this.buyer_num = buyer_num;
	}
	
	// 테이블에 없지만 UI에 필요한 정보
	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public MemberVO getBuyerVO() {
		return buyerVO;
	}
	public void setBuyerVO(MemberVO buyerVO) {
		this.buyerVO = buyerVO;
	}
	public MemberVO getSellerVO() {
		return sellerVO;
	}
	public void setSellerVO(MemberVO sellerVO) {
		this.sellerVO = sellerVO;
	}
	public String getLatest_chat() {
		return latest_chat;
	}
	public void setLatest_chat(String latest_chat) {
		this.latest_chat = latest_chat;
	}
	public String getLatest_date() {
		return latest_date;
	}
	public void setLatest_date(String latest_date) {
		this.latest_date = latest_date;
	}
}