package com.rk.xnes.entity;

import java.sql.Blob;

/**
 * 用户头像相关类
 * @author Mrruan
 *
 */
public class UserPortrait {

	private Integer id;
	private Integer userid;
	//头像图片的二进制文件
	private Blob portrait;
	
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
	public Blob getPortrait() {
		return portrait;
	}
	public void setPortrait(Blob portrait) {
		this.portrait = portrait;
	}
	
	
}
