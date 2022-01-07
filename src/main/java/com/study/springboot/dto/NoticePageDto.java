package com.study.springboot.dto;

import org.apache.ibatis.annotations.Mapper;


public class NoticePageDto {
	private int totalCount;		// 총 게시물의 갯수
	private int listCount;		// 한 페이지당 보여줄 게시물의 갯수
	private int totalPage;		// 총 페이지	
	private int curPage;		// 현재 페이지
	private int pageCount;		// 하단에 보여줄 페이지 리스트의 갯수
	private int startPage;		// 시작 페이지
	private int endPage;		// 끝 페이지
	
	
	
	public NoticePageDto(int totalCount, int listCount, int totalPage, int curPage, int pageCount, int startPage,
			int endPage) {
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.totalPage = totalPage;
		this.curPage = curPage;
		this.pageCount = pageCount;
		this.startPage = startPage;
		this.endPage = endPage;
	}
	public NoticePageDto() {
		// TODO Auto-generated constructor stub
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}
