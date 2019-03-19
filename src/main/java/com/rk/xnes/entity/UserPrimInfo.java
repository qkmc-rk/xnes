package com.rk.xnes.entity;

public class UserPrimInfo {

	private Integer id;
	private Integer userid;
	
	private String neckname;
	private String usermail;
	private String userphone;
	//mysql数据库中对应为枚举类型,M  F
	private String sex;
	private Integer age;
	private String qq;
	//那栋宿舍(稍微有点冗余,但是便于程序功能实现)
	private Integer dormnum;
	//宿舍地址
	private String dormaddr;
	//家庭住址
	private String homeaddr;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getNeckname() {
		return neckname;
	}
	public void setNeckname(String neckname) {
		this.neckname = neckname;
	}
	public String getUsermail() {
		return usermail;
	}
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getDormnum() {
		return dormnum;
	}
	public void setDormnum(Integer dormnum) {
		this.dormnum = dormnum;
	}
	public String getDormaddr() {
		return dormaddr;
	}
	public void setDormaddr(String dormaddr) {
		this.dormaddr = dormaddr;
	}
	public String getHomeaddr() {
		return homeaddr;
	}
	public void setHomeaddr(String homeaddr) {
		this.homeaddr = homeaddr;
	}
	
	
}
