package com.rk.xnes.entity;

import java.sql.Date;

/**
 * �û�����
 * �û��������ڵĵ�¼��Ϣ����
 * @author Mrruan
 *
 */
public class UserToken {

	private Integer id;
	private Integer userid;
	
	//�û�������,�û������¼״̬
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
