package com.rk.xnes.entity;

import java.sql.Timestamp;

/**
 * ���� entity
 * ����Ա��������
 * @author Mrruan
 *
 */
public class Notice {

	private Integer id;
	private Timestamp cretime;
	//����������˵�ID
	private Integer creatorid;
	//��������
	private String notice;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getCretime() {
		return cretime;
	}
	public void setCretime(Timestamp cretime) {
		this.cretime = cretime;
	}
	public Integer getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
	
}
