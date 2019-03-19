package com.rk.xnes.entity;

import java.sql.Date;

/**
 * 用户令牌
 * 用户保存用于的登录信息作用
 * @author Mrruan
 *
 */
public class UserToken {

	private Integer id;
	private Integer userid;
	
	//用户的令牌,用户保存登录状态
	private String token;
	private Date crettime;
	
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCrettime() {
		return crettime;
	}
	public void setCrettime(Date crettime) {
		this.crettime = crettime;
	}
	
}
