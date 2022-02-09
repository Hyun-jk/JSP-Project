package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private int amember_num; // 회원 번호
	private String id; // 아이디
	private int auth; // 회원 등급; 0=탈퇴, 1=정지, 2=일반, 3=관리자
	private String name; // 이름
	private String nickname; // 별명
	private String password; // 비밀번호
	private Date age; // 생년월일
	private String phone; // 휴대전화번호
	private String address; // 회원 동네
	private String address_favor; // 선호 동네
	private String email; // 이메일
	private String photo; // 프로필 사진 파일명
	private double rate; // 매너 점수
	private Date reg_date; // 가입일
	
	// 비밀번호 일치 여부 체크
	public boolean checkPassword(String UserPassword) {
		if(auth>1 && password.equals(UserPassword)) { // 탈퇴 및 정지 회원은 서비스 이용 불가하도록 처리
			return true;
		}
		return false;
	}
	
	public int getAmember_num() {
		return amember_num;
	}
	public void setAmember_num(int amember_num) {
		this.amember_num = amember_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getAge() {
		return age;
	}
	public void setAge(Date age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress_favor() {
		return address_favor;
	}
	public void setAddress_favor(String address_favor) {
		this.address_favor = address_favor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
}