package kr.product.vo;

import kr.member.vo.MemberVO;

public class CommentVO {
	private int acomment_num;
	private int amember_num;
	private int aproduct_num;
	private String content;
	private Integer acomment_parent;
	private String reg_date;
	
	// 테이블에 없지만 UI 처리를 위해 필요한 정보
	private MemberVO memberVO;
	
	public int getAcomment_num() {
		return acomment_num;
	}
	public void setAcomment_num(int acomment_num) {
		this.acomment_num = acomment_num;
	}
	public int getAmember_num() {
		return amember_num;
	}
	public void setAmember_num(int amember_num) {
		this.amember_num = amember_num;
	}
	public int getAproduct_num() {
		return aproduct_num;
	}
	public void setAproduct_num(int aproduct_num) {
		this.aproduct_num = aproduct_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getAcomment_parent() {
		return acomment_parent;
	}
	public void setAcomment_parent(Integer acomment_parent) {
		this.acomment_parent = acomment_parent;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	// 테이블에 없지만 UI 처리를 위해 필요한 정보
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
}