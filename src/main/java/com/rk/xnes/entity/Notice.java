package com.rk.xnes.entity;

import java.sql.Timestamp;

/**
 * 公告 entity
 * 管理员发布公告
 * @author Mrruan
 *
 */
public class Notice {

	private Integer id;
	private Timestamp cretime;
	//创建公告的人的ID
	private Integer creatorid;
	//公告内容
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
