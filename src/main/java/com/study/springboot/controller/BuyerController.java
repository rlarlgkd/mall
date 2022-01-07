package com.study.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.springboot.dao.IMallDao;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.NoticePageDto;


@Controller
public class BuyerController {
	
	@Autowired
	IMallDao dao;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@RequestMapping("/buyer/file")
	   public String file(){
		return "redirect:fileList";
	}
	
	@RequestMapping("/buyer/csChatting")
	public String csChatting(Model model) {
		
		return "/buyer/csChatting";
	}
	
	

	/*
	 * @RequestMapping("/") public String root(Model model) throws Exception{ return
	 * "redirect:main"; }
	 */
	@RequestMapping("/buyer/mallInfo")
	public String mallInfo(Model model) throws Exception{
		
		return "/buyer/mallInfo";
	}
	@RequestMapping("/buyer/main")
	public String main(Model model) throws Exception{
		model.addAttribute("main", dao.listDao());
		return "/buyer/main";
	}

	/*
	 * @RequestMapping("/buyer/logout") public String logout(HttpServletRequest
	 * request, Model model){ return "/buyer/logout"; }
	 * 
	 * @RequestMapping("/buyer/signup") public String signup(HttpServletRequest
	 * request, Model model){ return "/buyer/signup"; }
	 * 
	 * @RequestMapping("/buyer/signin") public String signin(HttpServletRequest
	 * request, Model model){ String mid = request.getParameter("mid"); String mpw =
	 * request.getParameter("mpw"); String mname = request.getParameter("mname");
	 * String usertype = request.getParameter("usertype"); String postcode =
	 * request.getParameter("postcode"); String address =
	 * request.getParameter("address"); String detailAddress =
	 * request.getParameter("detailAddress"); String extraAddress =
	 * request.getParameter("extraAddress");
	 * 
	 * 
	 * 
	 * dao.signupDao(mid, mpw, mname, usertype, postcode, address, detailAddress,
	 * extraAddress); return "redirect:main"; }
	 */
	
	@RequestMapping("/buyer/myPage")
	   public String mypage(HttpServletRequest request, Model model){
	      String mid = request.getParameter("mid");
	      
	      model.addAttribute("member",dao.memberInfoDao(mid));
	      return "/buyer/myPage";
	   }
	@RequestMapping("/buyer/myPageChange")
public void mypageChange(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
		
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		String newpw = request.getParameter("newpw");
		String postcode = request.getParameter("postcode");
		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");

		model.addAttribute("currentInfo", dao.memberInfoDao(mid));		
		MemberDto member = (MemberDto) model.getAttribute("currentInfo");		
		Boolean isMatches = bCryptPasswordEncoder.matches(mpw, member.getMpw());
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		if (isMatches && (newpw.equals(""))) {
			dao.infoChangeDao(member.getMpw(), postcode, address, detailAddress, extraAddress, mid);

			out.println("<script>alert('회원 정보 수정이 완료되었습니다'); history.go(-2);</script>");
			out.flush();
		}
		else if (isMatches && (mpw!=newpw)) {
			String pwChange = bCryptPasswordEncoder.encode(newpw);
			dao.infoChangeDao(pwChange, postcode, address, detailAddress, extraAddress, mid);
			out.println("<script>alert('회원 정보 수정이 완료되었습니다'); history.go(-2);</script>");
			out.flush();
		}
		else {
		out.println("<script>alert('비밀번호가 틀렸습니다'); history.go(-1);</script>");
		out.flush();
		}
	}
	
	@RequestMapping("/buyer/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		
		model.addAttribute("currentInfo", dao.memberInfoDao(mid));
		
		MemberDto member = (MemberDto) model.getAttribute("currentInfo");
		
		Boolean isMatches = bCryptPasswordEncoder.matches(mpw, member.getMpw());
		
		if (isMatches) {
			dao.delNotice(mid);
			dao.delSale(mid);
			dao.delReview(mid);
			dao.delCart(mid);
			dao.delProduct(mid);
			dao.withdrawDao(mid);
			session.invalidate();
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원 탈퇴 완료되었습니다'); history.go(-1);</script>");
			out.flush();
		}
		else {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('비밀번호가 틀렸습니다'); history.go(-1);</script>");
		out.flush();
		}
	}
	
	
	@RequestMapping("/buyer/idcheck")
	public String idcheck(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		
		return "/buyer/signup";
	}
	

	
	// 파일 업로드 폼
	@RequestMapping("/buyer/buyerForm")
	public String buyerForm(HttpServletRequest request, Model model){
		
		return "/buyer/buyerForm";
	}



	
	//end Navbar------------------------------------------------------
	
	//totalView.jsp-----------------------------------------------------
	@RequestMapping("/buyer/totalView")
	public String totalView(HttpServletRequest request, Model model) {
		String pname = request.getParameter("pname");
		
		model.addAttribute("list", dao.bestViewDao(pname));
		model.addAttribute("result", dao.resultDao(pname));
		return "/buyer/totalView";
	}
	//-------------------------------------------------------------------
	
	//contentView.jsp-----------------------------------------------------
	@RequestMapping("/buyer/contentView")
	public String view(HttpServletRequest request, Model model){	
		String sPnum=request.getParameter("pnum");
		
		dao.upHitDao(sPnum);
		model.addAttribute("content", dao.viewDao(sPnum));
		model.addAttribute("review", dao.reviewDao(sPnum));
		model.addAttribute("users",dao.userSaleDao(sPnum));
		
		return "/buyer/contentView";
	}
	
	//cart.jsp-----------------------------------------------------
	@RequestMapping("/buyer/cart")
	public String cart(HttpServletRequest request, Model model) {
		String sId=request.getParameter("mid");
		List<Map<String, Object>> list=dao.cartDao(sId);
		for(Map<String, Object> map: list) {
			map.get("PNAME");
			map.get("CCNT");
			map.get("PRICE");
			map.get("PNUM");
			map.get("MID");
			
			model.addAttribute("cart", list);
		}
		return "/buyer/cart";
	}
	
	@RequestMapping("/buyer/cartDelete")
	public String cartDelete(HttpServletRequest request, Model model) {

		dao.cartDeleteDao(request.getParameter("pnum"),
							request.getParameter("mid"));
		String result = "redirect:cart?mid="+request.getParameter("mid");
		return result;
	}
	
	@RequestMapping("/buyer/addSale")
	public String addSale(HttpServletRequest request, Model model) {
		
		String[] salePnum=request.getParameterValues("salePnum");
		String[] saleMid=request.getParameterValues("saleMid");

		for(int i=0; i<salePnum.length; i++) {
			
			String mid=saleMid[i];
			String pnum=salePnum[i];
			System.out.println("mid : "+mid+" pnum : "+pnum);
			dao.addSaleDao(mid, pnum);
			dao.delCart(mid);
		}
		return "redirect:main";
	}
	
	@RequestMapping("/buyer/cartAdd")
	public String cartAdd(HttpServletRequest request, Model model) {
		String mid=request.getParameter("mid");
		String pnum=request.getParameter("pnum");
		String ccnt=request.getParameter("ccnt");

//		System.out.println(mid+" : "+pnum+" : "+ccnt);
		
		if(dao.cartSelectDao(mid, pnum) >= 1) {
			dao.cartUpdateDao(ccnt, pnum);
		}else {
			dao.cartAddDao(mid, pnum, ccnt);	
		}	
		
		String result="redirect:contentView?pnum="+request.getParameter("pnum");
		return result;
	}
	
	@RequestMapping("/buyer/cartAddMove")
	public String cartAddMove(HttpServletRequest request, Model model) {
		String mid=request.getParameter("mid");
		String pnum=request.getParameter("pnum");
		String ccnt=request.getParameter("ccnt");

		System.out.println(mid+" : "+pnum+" : "+ccnt);
		
		if(dao.cartSelectDao(mid, pnum) >= 1) {
			dao.cartUpdateDao(ccnt, pnum);
		}else {
			dao.cartAddDao(mid, pnum, ccnt);	
		}	
		
		String result="redirect:cart?mid="+mid;
		return result;
	}
	//end cart-----------------------------------------------------------------
	
	//contentView.jsp -> reviewForm.jsp--------------------------------
	@RequestMapping("/buyer/reviewForm")
	public String reviewForm(HttpServletRequest request, Model model) {
		String pName=request.getParameter("pname");
		String pnum = request.getParameter("pnum");
		
		model.addAttribute("pname", pName);
		model.addAttribute("pnum",pnum);	
		return "/buyer/reviewForm";
	}
	//------------------------------------------------------------------
	
	//reviewForm.jsp -> main.jsp----------------------------------------
	@RequestMapping("/buyer/review")
	public String review(HttpServletRequest request, Model model) {
		String pName=request.getParameter("pname");
		String pnum = request.getParameter("pnum");
		String mid = request.getParameter("mid");
		String newReview = request.getParameter("review");
		String sReview = newReview.substring(3);
		String review = sReview.substring(0,sReview.length()-4);
		
		
		dao.reviewWriteDao(mid,pnum,review,0 , 0);
		model.addAttribute("pname", pName);
		model.addAttribute("pnum",pnum);
			
		String result = "redirect:contentView?pnum=" + pnum;
		
		return result;
	}
	
	@RequestMapping("/buyer/reply")
	public String reply(HttpServletRequest request, Model model) {
		String mid = request.getParameter("mid");
		String pnum = request.getParameter("pnum");
		String newReview = request.getParameter("review");
		String sReview = newReview.substring(3);
		String review = sReview.substring(0,sReview.length()-4);
		int regroup = Integer.parseInt(request.getParameter("regroup"));
		int restep = Integer.parseInt(request.getParameter("restep"));
		int reindent = Integer.parseInt(request.getParameter("reindent"));
		
		
		dao.replyDao(mid, pnum, review, regroup, restep, reindent);
		
		String result="redirect:contentView?pnum="+pnum;
		return result;
	}
	
	@RequestMapping("/buyer/replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		String reid = request.getParameter("reid");
		model.addAttribute("replyForm",dao.replyViewDao(reid));
		return "/buyer/replyForm";
	}
	//end review--------------------------------------------------------


	//notice.jsp--------------------------------------------------------
	@RequestMapping("/buyer/notice")
	public String notilistDao(HttpServletRequest request, Model model) {
		int nPage=1;
		int listCount=5;	//한 페이지 당 보여줄 게시물의 갯수
		int pageCount=10;	//하단에 보여줄 페이지 리스트의 갯수	
		int totalCount = dao.articlePage();
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0) { totalPage++; }
		try {
			nPage = Integer.parseInt(request.getParameter("page"));	
		} catch (Exception e) {
			nPage = 1;
		};
		//현재 페이지
		int myCurPage = nPage;
		if (myCurPage > totalPage) { myCurPage = totalPage; }
		if (myCurPage < 1) { myCurPage = 1; }
		//시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;	
		//끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) {	endPage = totalPage;}
		NoticePageDto pinfo = new NoticePageDto();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		model.addAttribute("page", pinfo);
		
		int nStart = (nPage - 1) * listCount + 1;
		int nEnd = (nPage - 1) * listCount + listCount;
		
		model.addAttribute("notice", dao.notilistDao(nStart, nEnd));	
		return "/buyer/notice";
	}

	  @RequestMapping("/buyer/noticeView")
	   public String noticeView(HttpServletRequest request, Model model){
	      String sNum=request.getParameter("nonum");
	      
	      model.addAttribute("notice_View", dao.noticeViewDao(sNum));  
	      return "/buyer/noticeView";
	   }

	  //end notice----------------------------------------------------
}
