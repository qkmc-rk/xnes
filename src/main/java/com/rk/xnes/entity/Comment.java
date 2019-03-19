package com.rk.xnes.entity;

import java.sql.Timestamp;

/**
 * 评论 有人发布求助信息(任务)
 * 注册的用户可以进行评论
 * @author Mrruan
 *
 */
public class Comment {
	
	private Integer id;
	private Integer userid;
	private Integer infoid;
	
	private String title;
	private String comment;
	private Timestamp commenttime;
	
	
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
	public Integer getInfoid() {
		return infoid;
	}
	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getCommenttime() {
		return commenttime;
	}
	public void setCommenttime(Timestamp commenttime) {
		this.commenttime = commenttime;
	}
}
