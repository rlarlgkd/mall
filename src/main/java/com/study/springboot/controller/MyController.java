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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.springboot.dao.IMallDao;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.NoticePageDto;

@Controller
public class MyController {
	
	@Autowired
	IMallDao dao;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/loginForm")
	public String loginForm(){
		return "security/loginForm";
	}
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@RequestMapping("/admin/file")
	   public String file(){
		return "redirect:fileList";
	}
	
	@RequestMapping("/admin/csChatting")
	public String csChatting(Model model) {
		
		return "/admin/csChatting";
	}

	@RequestMapping("/login_success_handler")
	public String loginsuccesshandler(HttpServletRequest request, Model model){
//		System.out.println("login1");
		
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		//???????????? ?????????
//		String encode = passwordEncoder.encode(mpw);
//		//????????? ??????????????? ???????????? ??????????????? ??????(??????????????????, ???????????? ????????????)
//		Boolean isMatches = passwordEncoder.matches(mpw, encode);
//		System.out.println("mid : "+mid+" mpw : "+encode);		
//		session.setAttribute("mid", mid);
//		System.out.println("loginsuccesshandler_1");
		
		session.setAttribute("memberInfo", dao.memberInfoDao(mid));
		MemberDto mInfo = (MemberDto)session.getAttribute("memberInfo");
		String type = mInfo.getUsertype();
		if(type.equals("ROLE_SELLER")) {
			session.setAttribute("mid", mid);
			return "redirect:seller/main";
		}
		else if (type.equals("ROLE_BUYER")) {
			session.setAttribute("mid", mid);
			return "redirect:buyer/main";
		}
		else if (type.equals("ROLE_ADMIN")) {
			session.setAttribute("mid", mid);
			return "redirect:admin/main";
		}
		return "guest/main";
	}
	

	 
	@RequestMapping("/admin/mallInfo")
	public String mallInfo(Model model) throws Exception{
		
		return "/admin/mallInfo";
	}
	@RequestMapping("/admin/main")
	public String main(Model model) throws Exception{
		model.addAttribute("main", dao.listDao());
		return "/admin/main";
	}
	
	//Navbar -----------------------------------------------------

	
	@RequestMapping("/main")
	public String smain(HttpServletRequest request, Model model){
		 model.addAttribute("main", dao.listDao());
		return "/guest/main";
	}
	
	@RequestMapping("/seller/logout")
	public String slogout(HttpServletRequest request, Model model){
		return "./guest/logout";
	}
	
	@RequestMapping("/buyer/logout")
	public String blogout(HttpServletRequest request, Model model){
		return "./guest/logout";
	}
	
	@RequestMapping("/admin/logout")
	public String logout(HttpServletRequest request, Model model){
		return "./guest/logout";
	}
	
	@RequestMapping("/guest/logout")
	public String glogout(HttpServletRequest request, Model model){
		return "/guest/logout";
	}
	
//	@RequestMapping("/loginProcess")
//	public void loginProcess(HttpServletRequest request, Model model){
//		String mid = request.getParameter("mid");
//		String mpw = request.getParameter("mpw");
//		
//		System.out.println("mid : "+mid+"mpw : "+mpw);
//		
//		if (dao.loginConfirmDao(mid)==1) {		
//			session.setAttribute("mid", mid);
//		}
//	}
	
	@RequestMapping("/admin/signup")
	public String signup(HttpServletRequest request, Model model){
		return "/admin/signup";
	}
		
	/*
	 * @RequestMapping("/admin/signin") public String signin(HttpServletRequest
	 * request, Model model){ String mid = request.getParameter("mid"); String mpw =
	 * request.getParameter("mpw"); String mname = request.getParameter("mname");
	 * String usertype = request.getParameter("usertype"); String postcode =
	 * request.getParameter("postcode"); String address =
	 * request.getParameter("address"); String detailAddress =
	 * request.getParameter("detailAddress"); String extraAddress =
	 * request.getParameter("extraAddress"); String encoded=new
	 * BCryptPasswordEncoder().encode(mpw);
	 * 
	 * dao.signupDao(mid, encoded, mname, usertype, postcode, address,
	 * detailAddress, extraAddress); return "redirect:main"; }
	 */
	
	@RequestMapping("/admin/myPage")
	   public String mypage(HttpServletRequest request, Model model){
	      String mid = request.getParameter("mid");
	      
	      model.addAttribute("member",dao.memberInfoDao(mid));
	      return "/admin/myPage";
	   }
	@RequestMapping("/admin/myPageChange")
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
	
	@RequestMapping("/admin/withdraw")
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
	
	
	@RequestMapping("/admin/idcheck")
	public String idcheck(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		
		return "/admin/signup";
	}
	

	
	// ?????? ????????? ???
	@RequestMapping("/admin/adminForm")
	public String adminForm(HttpServletRequest request, Model model){
		
		return "/admin/adminForm";
	}


	// ?????? ????????? ??????
	@RequestMapping(value="/admin/adminWrite", method=RequestMethod.POST)
	public String adminWrite(HttpServletRequest request, Model model){
		
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
				result="redirect:adminForm?mid="+mid;
					
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
	@RequestMapping("/admin/totalView")
	public String totalView(HttpServletRequest request, Model model) {
		String pname = request.getParameter("pname");
		
		model.addAttribute("list", dao.bestViewDao(pname));
		model.addAttribute("result", dao.resultDao(pname));
		return "/admin/totalView";
	}
	//-------------------------------------------------------------------
	
	//contentView.jsp-----------------------------------------------------
	@RequestMapping("/admin/contentView")
	public String view(HttpServletRequest request, Model model){	
		String sPnum=request.getParameter("pnum");
		
		dao.upHitDao(sPnum);
		model.addAttribute("content", dao.viewDao(sPnum));
		model.addAttribute("review", dao.reviewDao(sPnum));
		model.addAttribute("users",dao.userSaleDao(sPnum));
		
		return "/admin/contentView";
	}
	
	//cart.jsp-----------------------------------------------------
	@RequestMapping("/admin/cart")
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
		return "/admin/cart";
	}
	
	@RequestMapping("/admin/cartDelete")
	public String cartDelete(HttpServletRequest request, Model model) {

		dao.cartDeleteDao(request.getParameter("pnum"),
							request.getParameter("mid"));
		String result = "redirect:cart?mid="+request.getParameter("mid");
		return result;
	}
	
	@RequestMapping("/admin/cartAdd")
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
	
	@RequestMapping("/admin/cartAddMove")
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
	@RequestMapping("/admin/reviewForm")
	public String reviewForm(HttpServletRequest request, Model model) {
		String pName=request.getParameter("pname");
		String pnum = request.getParameter("pnum");
		
		model.addAttribute("pname", pName);
		model.addAttribute("pnum",pnum);	
		return "/admin/reviewForm";
	}
	//------------------------------------------------------------------
	
	//reviewForm.jsp -> main.jsp----------------------------------------
	@RequestMapping("/admin/review")
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
	
	@RequestMapping("/admin/reply")
	public String reply(HttpServletRequest request, Model model) {
		String mid = request.getParameter("mid");
		String pnum = request.getParameter("pnum");
		String review = request.getParameter("review");
		System.out.println(pnum);
		int regroup = Integer.parseInt(request.getParameter("regroup"));
		int restep = Integer.parseInt(request.getParameter("restep"));
		int reindent = Integer.parseInt(request.getParameter("reindent"));
		
		
		dao.replyDao(mid, pnum, review, regroup, restep, reindent);
		
		String result="redirect:contentView?pnum="+pnum;
		return result;
	}
	
	@RequestMapping("/admin/replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		String reid = request.getParameter("reid");
		model.addAttribute("replyForm",dao.replyViewDao(reid));
		return "/admin/replyForm";
	}
	//end review--------------------------------------------------------


	//notice.jsp--------------------------------------------------------
	@RequestMapping("/admin/notice")
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
		return "/admin/notice";
	}

	  @RequestMapping("/admin/noticeView")
	   public String noticeView(HttpServletRequest request, Model model){
	      String sNum=request.getParameter("nonum");
	      
	      model.addAttribute("notice_View", dao.noticeViewDao(sNum));  
	      return "/admin/noticeView";
	   }
	  
	  @RequestMapping("/admin/noticeForm")
	   public String noticeForm(HttpServletRequest request, Model model){	      
	      return "/admin/noticeForm";
	   }
	  
	  @RequestMapping("/admin/noticeWrite")
	   public String noticeWrite(HttpServletRequest request, Model model){
		  System.out.println(request.getParameter("notitle"));
		  System.out.println(request.getParameter("nocontent"));
		  
		  dao.noticeWriteDao(request.getParameter("notitle"),
				  			 request.getParameter("nocontent"));	      
	      return "redirect:notice";
	   }

	  //end notice----------------------------------------------------
}
