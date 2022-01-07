package com.study.springboot.dto;


public class ReviewDto {
	private int reid;
	private String mid;
	private int pnum;
	private String review;
	private int reindent;
	private int regroup;
	private int restep;
	
	
	public ReviewDto() {
		// TODO Auto-generated constructor stub
	}
	


	
	public ReviewDto(int reid, String mid, int pnum, String review, int reindent, int regroup, int restep) {
		super();
		this.reid = reid;
		this.mid = mid;
		this.pnum = pnum;
		this.review = review;
		this.reindent = reindent;
		this.regroup = regroup;
		this.restep = restep;
	}




	public int getReid() {
		return reid;
	}




	public void setReid(int reid) {
		this.reid = reid;
	}




	public int getReindent() {
		return reindent;
	}

	public void setReindent(int reindent) {
		this.reindent = reindent;
	}

	public int getRegroup() {
		return regroup;
	}

	public void setRegroup(int regroup) {
		this.regroup = regroup;
	}

	public int getRestep() {
		return restep;
	}

	public void setRestep(int restep) {
		this.restep = restep;
	}

	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	
}
