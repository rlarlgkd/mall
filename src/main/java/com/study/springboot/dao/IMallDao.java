package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.NoticeDto;
import com.study.springboot.dto.ProductDto;
import com.study.springboot.dto.ReviewDto;

@Mapper
public interface IMallDao {
	
	//main ------------------------------------------
	public List<ProductDto> listDao();

	//navbar -----------------------------------------------------
	public int signupDao(String mid, String mpw, String mname,
						 String usertype, String postcode, String address,
						 String detailAddress, String extraAddress);
	public int loginConfirmDao(String mid);
	public MemberDto loginDao(String mid, String mpw);
	
	public List<MemberDto> checkId();
	
	public int infoChangeDao(String mpw, String postcode, String address,
							String detailAddress, String extraAddress, String mid);
	public int delNotice(String mid);
	public int delSale(String mid);
	public int delReview(String mid);
	public int delCart(String mid);
	public int delProduct(String mid);
	public int withdrawDao(String mid);
	public MemberDto memberInfoDao(String mid);
	public int sellerWriteDao(String pname, String price, String pcnt, String mid);
	//totalView -----------------------------------------------------
	public List<ProductDto> bestViewDao(String pname);	
	public int resultDao(String pname);
	

	//contentView ----------------------------------------------------
	public ProductDto viewDao(String pname);
	public void upHitDao(String pname);
	public List<String> userSaleDao(String pname);
	
	//cart-----------------------------------------------------------------
	public List<Map<String, Object>> cartDao(String mid);
	public int cartDeleteDao(String pnum, String mid);
	
	public int cartSelectDao(String mid,String pnum);
	public int cartAddDao(String mid, String pnum, String ccnt);
	public void cartUpdateDao(String ccnt, String pnum);
	
	//sale----------------------------------------------------------------
	public int addSaleDao(String mid, String pnum);
		
	//review--------------------------------------------------------
	public List<ReviewDto> reviewDao(String pname);
	public int reviewWriteDao(String mid, String pnum, String review, 
						int restep, int reindent);
	public List<Map<String, Object>> reviewListDao(String mid);
	
	public int replyDao(String mid, String pnum, String review, 
			int regroup, int restep, int reindent);
	public ReviewDto replyViewDao(String pnum);
	
	//notice----------------------------------------------------
	public List<NoticeDto> notilistDao(@Param("_start")int nStart, @Param("_end")int nEnd);
	public int articlePage();
	public NoticeDto noticeViewDao(String nonum);
	public int noticeWriteDao(String notitle, String nocontent);
	
	
}
