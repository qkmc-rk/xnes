package com.rk.xnes.entity;

/**
 * �û���ʵ����֤��Ϣ,�����Ƿ��Ѿ�ʵ����֤
 * ���ʵ����֤,��Ӧ�ð���ʵ����Ϣ��ѧ��֤��Ƭ.
 * @author Mrruan
 *
 */
public class UserCertif{
	private Integer id;
	private Integer userid;
	//�Ƿ��Ѿ�ʵ����֤
	private Integer state;
	//����Ѿ�ʵ����֤,��ѧ��֤��Ƭ����λ��
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
