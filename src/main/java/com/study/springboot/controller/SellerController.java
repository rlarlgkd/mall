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
public class SellerController {
	
	@Autowired
	IMallDao dao;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@RequestMapping("/seller/file")
	   public String file(){
		return "redirect:fileList";
	}
	
	@RequestMapping("/seller/csChatting")
	public String csChatting(Model model) {
		
		return "/seller/csChatting";
	}

	/*
	 * @RequestMapping("/") public String root(Model model) throws Exception{ return
	 * "redirect:main"; }
	 */
	@RequestMapping("/seller/mallInfo")
	public String mallInfo(Model model) throws Exception{
		
		return "/seller/mallInfo";
	}
	@RequestMapping("/seller/main")
	public String main(Model model) throws Exception{
		model.addAttribute("main", dao.listDao());
		return "/seller/main";
	}


	@RequestMapping("/seller/signup")
	public String signup(HttpServletRequest request, Model model){
		return "/seller/signup";
	}
		
	@RequestMapping("/seller/signin")
	public String signin(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		String mname = request.getParameter("mname");
		String usertype = request.getParameter("usertype");
		String postcode = request.getParameter("postcode");
		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		
		

		dao.signupDao(mid, mpw, mname, usertype, postcode, address,
				detailAddress, extraAddress);
		return "redirect:main";
	}
	
	@RequestMapping("/seller/myPage")
	   public String mypage(HttpServletRequest request, Model model){
	      String mid = request.getParameter("mid");
	      
	      model.addAttribute("member",dao.memberInfoDao(mid));
	      return "/seller/myPage";
	   }
	@RequestMapping("/seller/myPageChange")
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

			out.println("<script>alert('?????? ?????? ????????? ?????????????????????'); history.go(-2);</script>");
			out.flush();
		}
		else if (isMatches && (mpw!=newpw)) {
			String pwChange = bCryptPasswordEncoder.encode(newpw);
			dao.infoChangeDao(pwChange, postcode, address, detailAddress, extraAddress, mid);
			out.println("<script>alert('?????? ?????? ????????? ?????????????????????'); history.go(-2);</script>");
			out.flush();
		}
		else {
		out.println("<script>alert('??????????????? ???????????????'); history.go(-1);</script>");
		out.flush();
		}
	}
	
	@RequestMapping("/seller/withdraw")
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
			out.println("<script>alert('?????? ?????? ?????????????????????'); history.go(-1);</script>");
			out.flush();
		}
		else {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('??????????????? ???????????????'); history.go(-1);</script>");
		out.flush();
		}
	}
	
	
	@RequestMapping("/seller/idcheck")
	public String idcheck(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		
		return "/seller/signup";
	}
	

	
	// ?????? ????????? ???
	@RequestMapping("/seller/sellerForm")
	public String sellerForm(HttpServletRequest request, Model model){
		
		return "/seller/sellerForm";
	}


	// ?????? ????????? ??????
	@RequestMapping(value="/seller/sellerWrite", method=RequestMethod.POST)
	public String sellerWrite(HttpServletRequest request, Model model){
		
		// ?????? ????????? ????????? ???????????? ?????? Map????????? ??????
		Map returnObj = new HashMap();
		String result = null;

			
		try {
			// ????????? ??????????????? ????????????
			String path = ResourceUtils.getFile(uploadPath).toPath().toString();
            /*
			 * ??????????????? ?????? MultipartHttpServletRequest?????? ??????
			 * ?????? ????????? ????????? ??????????????? ?????????. 
			 * ????????? ????????? Multipart??? ????????? ????????? ????????????.
			 */
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
				
			// ??????????????? file?????? ????????? ????????? ?????? ??????
			Iterator<String> itr = mhsr.getFileNames();
			
			MultipartFile mfile = null;			
			String fileName = "";		
			
			// ?????? ????????? ????????? ???????????? ?????? List????????? ??????(???????????????, ?????????????????? ???)
			List resultList = new ArrayList();
						
			// ???????????? : ??????
			String pnum = request.getParameter("pnum");
			System.out.println(pnum);
			String pname = request.getParameter("pname");
			System.out.println(pname);
			String price = request.getParameter("price");
			System.out.println(price);
			String pcnt = request.getParameter("pcnt");
			String mid = request.getParameter("mid");
			System.out.println(pcnt);
			
			
			// ??????????????? file????????? ????????? ???????????? ??????
			while (itr.hasNext()) {
				
			// userfile1, userfile2....?????????
				fileName = (String)itr.next();
				System.out.println(fileName);	
					
				// ????????? ???????????? ??????????????? ?????????
				mfile = mhsr.getFile(fileName);
				System.out.println(mfile);			//CommonsMultipartFile@1366c0b ?????????
					
				// ?????????????????? ?????? ??? ???????????? ???????????? ????????????.
				String originalName =  new String(mfile.getOriginalFilename().getBytes(),"UTF-8"); // Linux
										//mfile.getOriginalFilename();
				   
				System.out.println("upload:"+originalName);
				result="redirect:sellerForm?mid="+mid;
					
				// ???????????? ??????????????? while?????? ???????????? ????????????.
				if("".equals(originalName)){
					continue;
					
			}
			dao.sellerWriteDao( pname, price, pcnt, mid);
			result="redirect:main";
			System.out.println("originalName:"+originalName);

			String saveFileName = originalName;
					
			// ????????? ????????? ????????????
			File serverFullName = new File(path + File.separator + saveFileName);
					
			// ???????????? ????????? ????????? ????????? ????????????.
			mfile.transferTo(serverFullName);
					
			Map file = new HashMap();
			file.put("originalName", originalName);    //???????????????
			file.put("saveFileName", saveFileName);    //??????????????????
			file.put("serverFullName", serverFullName);//????????? ????????? ???????????? ??? ?????????
			file.put("pnum", pnum);                  //?????????
			file.put("pname", pname);    
			file.put("price", price);    
			file.put("pcnt", pcnt);    
			file.put("mid", mid);
					
			// ??? ????????? ????????? ?????? Map??? List??? ??????
			resultList.add(file);
			
		}
			returnObj.put("files", resultList);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("returnObj", returnObj);	
		
		
		return result;
	}
		
	
	//end Navbar------------------------------------------------------
	
	//totalView.jsp-----------------------------------------------------
	@RequestMapping("/seller/totalView")
	public String totalView(HttpServletRequest request, Model model) {
		String pname = request.getParameter("pname");
		
		model.addAttribute("list", dao.bestViewDao(pname));
		model.addAttribute("result", dao.resultDao(pname));
		return "/seller/totalView";
	}
	//-------------------------------------------------------------------
	
	//contentView.jsp-----------------------------------------------------
	@RequestMapping("/seller/contentView")
	public String view(HttpServletRequest request, Model model){	
		String sPnum=request.getParameter("pnum");
		
		dao.upHitDao(sPnum);
		model.addAttribute("content", dao.viewDao(sPnum));
		model.addAttribute("review", dao.reviewDao(sPnum));
		model.addAttribute("users",dao.userSaleDao(sPnum));
		
		return "/seller/contentView";
	}
	
	//cart.jsp-----------------------------------------------------
	@RequestMapping("/seller/cart")
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
		return "/seller/cart";
	}
	
	@RequestMapping("/seller/cartDelete")
	public String cartDelete(HttpServletRequest request, Model model) {

		dao.cartDeleteDao(request.getParameter("pnum"),
							request.getParameter("mid"));
		String result = "redirect:cart?mid="+request.getParameter("mid");
		return result;
	}
	
	@RequestMapping("/seller/cartAdd")
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
	
	@RequestMapping("/seller/cartAddMove")
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
	@RequestMapping("/seller/reviewForm")
	public String reviewForm(HttpServletRequest request, Model model) {
		String pName=request.getParameter("pname");
		String pnum = request.getParameter("pnum");
		
		model.addAttribute("pname", pName);
		model.addAttribute("pnum",pnum);	
		return "/seller/reviewForm";
	}
	//------------------------------------------------------------------
	
	//reviewForm.jsp -> main.jsp----------------------------------------
	@RequestMapping("/seller/review")
	public String review(HttpServletRequest request, Model model) {
		String pName=request.getParameter("pname");
		String pnum = request.getParameter("pnum");
		String mid = request.getParameter("mid");
		String review = request.getParameter("review");
		
		dao.reviewWriteDao(mid,pnum,review,0 , 0);
		model.addAttribute("pname", pName);
		model.addAttribute("pnum",pnum);
			
		String result = "redirect:contentView?pnum=" + pnum;
		
		return result;
	}
	
	@RequestMapping("/seller/reply")
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
	
	@RequestMapping("/seller/replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		String reid = request.getParameter("reid");
		model.addAttribute("replyForm",dao.replyViewDao(reid));
		return "/seller/replyForm";
	}
	//end review--------------------------------------------------------


	//notice.jsp--------------------------------------------------------
	@RequestMapping("/seller/notice")
	public String notilistDao(HttpServletRequest request, Model model) {
		int nPage=1;
		int listCount=5;	//??? ????????? ??? ????????? ???????????? ??????
		int pageCount=10;	//????????? ????????? ????????? ???????????? ??????	
		int totalCount = dao.articlePage();
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0) { totalPage++; }
		try {
			nPage = Integer.parseInt(request.getParameter("page"));	
		} catch (Exception e) {
			nPage = 1;
		};
		//?????? ?????????
		int myCurPage = nPage;
		if (myCurPage > totalPage) { myCurPage = totalPage; }
		if (myCurPage < 1) { myCurPage = 1; }
		//?????? ?????????
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;	
		//??? ?????????
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
		return "/seller/notice";
	}

	  @RequestMapping("/seller/noticeView")
	   public String noticeView(HttpServletRequest request, Model model){
	      String sNum=request.getParameter("nonum");
	      
	      model.addAttribute("notice_View", dao.noticeViewDao(sNum));  
	      return "/seller/noticeView";
	   }

	  //end notice----------------------------------------------------
}
