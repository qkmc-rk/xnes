package com.rk.xnes.entity;

import java.sql.Blob;

/**
 * �û�ͷ�������
 * @author Mrruan
 *
 */
public class UserPortrait {

	private Integer id;
	private Integer userid;
	//ͷ��ͼƬ�Ķ������ļ�
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
