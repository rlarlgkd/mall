package com.study.springboot.dto;

import lombok.Data;

@Data
public class MemberDto {
	private int mnum;
	private String mid;
	private String mpw;
	private String mname;
	private String postcode;
	private String address;
	private String detailAddress;
	private String extraAddress;
	private String usertype;
}
