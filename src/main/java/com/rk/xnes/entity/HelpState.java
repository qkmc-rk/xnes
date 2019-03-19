package com.rk.xnes.entity;

public class HelpState {
	
	private Integer id;
	private Integer infoid;
	//是否超时,与HelpInfo中的timeout有差别
	private Integer timeout;
	
	private Integer received;
	private Integer receiverid;
	private Integer achieved;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInfoid() {
		return infoid;
	}
	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Integer getReceived() {
		return received;
	}
	public void setReceived(Integer received) {
		this.received = received;
	}
	public Integer getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}
	public Integer getAchieved() {
		return achieved;
	}
	public void setAchieved(Integer achieved) {
		this.achieved = achieved;
	}
	@Override
	public String toString() {
		return "HelpState [id=" + id + ", infoid=" + infoid + ", timeout=" + timeout + ", received=" + received
				+ ", receiverid=" + receiverid + ", achieved=" + achieved + "]";
	}
}
