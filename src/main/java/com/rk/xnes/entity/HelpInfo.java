package com.rk.xnes.entity;

import java.sql.Timestamp;

/**
 * ������Ϣ ÿ���û������Է���������Ϣ
 * @author Mrruan
 *
 */
public class HelpInfo {

	private Integer id; 
	private Integer userid;
	private Timestamp crettime;
	
	//����
	private Long timeout;
	//����
	private String title;
	//��Ҫ����
	private String content;
	//��ע
	private String tip;
	//��ʱûʲô��
	private String imgpath;
	//�ͽ�
	private Integer reward;
	
	
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
	public Timestamp getCrettime() {
		return crettime;
	}
	public void setCrettime(Timestamp crettime) {
		this.crettime = crettime;
	}
	public Long getTimeout() {
		return timeout;
	}
	public void setTimeout(long l) {
		this.timeout = l;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
}
