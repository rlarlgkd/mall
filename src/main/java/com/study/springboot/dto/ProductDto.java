package com.study.springboot.dto;

import java.sql.Blob;

import lombok.Data;

@Data
public class ProductDto {
	private int pnum;
	private String mid;
	private String pname;
	private String price;
	private String hit;
	private String pcnt;
	private Blob pic;
}
