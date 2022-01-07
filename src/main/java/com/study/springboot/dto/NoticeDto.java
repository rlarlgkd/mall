package com.study.springboot.dto;

import java.sql.Timestamp;

public class NoticeDto {
	private int nonum;
	private String mid;
	private String notitle;
	private String nocontent;
	private Timestamp nodate;
	
	public NoticeDto(int nonum, String mid, String notitle, String nocontent, Timestamp nodate) {
		this.nonum = nonum;
		this.mid = mid;
		this.notitle = notitle;
		this.nocontent = nocontent;
		this.nodate = nodate;
		
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public NoticeDto() {
		// TODO Auto-generated constructor stub
	}
	public int getNonum() {
		return nonum;
	}
	public void setNonum(int nonum) {
		this.nonum = nonum;
	}
	public String getNotitle() {
		return notitle;
	}
	public void setNotitle(String notitle) {
		this.notitle = notitle;
	}
	public String getNocontent() {
		return nocontent;
	}
	public void setNocontent(String nocontent) {
		this.nocontent = nocontent;
	}
	public Timestamp getNodate() {
		return nodate;
	}
	public void setNodate(Timestamp nodate) {
		this.nodate = nodate;
	}
	
	
}
