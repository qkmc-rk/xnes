package com.rk.xnes.entity;

/**
 * 用户的实名认证信息,包括是否已经实名认证
 * 如果实名认证,则应该包含实名信息的学生证照片.
 * @author Mrruan
 *
 */
public class UserCertif{
	private Integer id;
	private Integer userid;
	//是否已经实名认证
	private Integer state;
	//如果已经实名认证,则学生证照片保存位置
	private String cardpath;
	
	
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCardpath() {
		return cardpath;
	}
	public void setCardpath(String cardpath) {
		this.cardpath = cardpath;
	}
	
	
}
