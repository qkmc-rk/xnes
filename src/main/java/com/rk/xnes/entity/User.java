package com.rk.xnes.entity;

/**
 * user �� �û��ĵ�¼��Ϣ
 * @author Mrruan
 *
 */
public class User {

	private Integer id;
	//ѧ��
	private Integer stuid;
	private String account;
	//ע�����ݿ��ֶε�һһ��Ӧ
	private String password;
	private String oldpassword;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStuid() {
		return stuid;
	}
	public void setStuid(Integer stuid) {
		this.stuid = stuid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
}
