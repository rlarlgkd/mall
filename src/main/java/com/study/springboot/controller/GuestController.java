package com.study.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
public class GuestController {

	@Autowired
	IMallDao dao;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@RequestMapping("guest/csChatting")
	public String csChatting(Model model) {

		return "/guest/csChatting";
	}
	
	 @RequestMapping("/")
	 public String root(Model model) throws Exception{ 
		 model.addAttribute("main", dao.listDao());
		 System.out.println("login1");
		 if (session.getAttribute("mid") != null)
		 {
			 Object obj=session.getAttribute("mid");
				String sId=(String)obj;
			 session.setAttribute("memberInfo", dao.memberInfoDao(sId));
				MemberDto mInfo = (MemberDto)session.getAttribute("memberInfo");
			 System.out.println("login2");
			String type = mInfo.getUsertype();
			System.out.println("login3");
			if(type.equals("ROLE_SELLER")) {
				return "redirect:seller/main";
			}
			else if (type.equals("ROLE_BUYER")) {
				return "redirect:buyer/main";
			}
			else if (type.equals("ROLE_ADMIN")) {
				return "redirect:admin/main";
			}
			System.out.println("login4");
		 }
		 return "redirect:guest/main"; 
		 
		 
	}
	 
	@RequestMapping("guest/mallInfo")
	public String mallInfo(Model model) throws Exception {

		return "/guest/mallInfo";
	}
	
	@RequestMapping("/loginProcess")
	public String loginProcess(Model model) throws Exception {

		return "/guest/loginProcess";
	}

	@RequestMapping("/guest/main")
	public String main(Model model) throws Exception {
		model.addAttribute("main", dao.listDao());
		return "/guest/main";
	}
	
	@RequestMapping("/guest/login")
	public String gusetlogin(HttpServletRequest request, Model model){
		return "/guest/login";
	}

	@RequestMapping("/guest/signup")
	public String signup(HttpServletRequest request, Model model){
		model.addAttribute("userId", dao.checkId());
		return "guest/signup";
	}
		
	@RequestMapping("/guest/signin")
	public String signin(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		String mname = request.getParameter("mname");
		String usertype = request.getParameter("usertype");
		String postcode = request.getParameter("postcode");
		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		String encoded= bCryptPasswordEncoder.encode(mpw);

		dao.signupDao(mid, encoded, mname, usertype, postcode, address,
				detailAddress, extraAddress);
		return "redirect:main";
	}
	
	
	
	@RequestMapping("/guest/idcheck")
	public String idcheck(HttpServletRequest request, Model model){
		String mid = request.getParameter("mid");
		
		return "/guest/signup";
	}

	// end Navbar------------------------------------------------------

	// totalView.jsp-----------------------------------------------------
	@RequestMapping("/guest/totalView")
	public String totalView(HttpServletRequest request, Model model) {
		String pname = request.getParameter("pname");

		model.addAttribute("list", dao.bestViewDao(pname));
		model.addAttribute("result", dao.resultDao(pname));
		return "/guest/totalView";
	}
	// -------------------------------------------------------------------

	// contentView.jsp-----------------------------------------------------
	@RequestMapping("/guest/contentView")
	public String view(HttpServletRequest request, Model model) {
		String sPnum = request.getParameter("pnum");

		dao.upHitDao(sPnum);
		model.addAttribute("content", dao.viewDao(sPnum));
		model.addAttribute("review", dao.reviewDao(sPnum));
		model.addAttribute("users", dao.userSaleDao(sPnum));

		return "/guest/contentView";
	}

	// notice.jsp--------------------------------------------------------
	@RequestMapping("/guest/notice")
	public String notilistDao(HttpServletRequest request, Model model) {
		int nPage = 1;
		int listCount = 5; // 한 페이지 당 보여줄 게시물의 갯수
		int pageCount = 10; // 하단에 보여줄 페이지 리스트의 갯수
		int totalCount = dao.articlePage();
		int totalPage = totalCount / listCount;
		if (totalCount % listCount > 0) {
			totalPage++;
		}
		try {
			nPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			nPage = 1;
		}
		;
		// 현재 페이지
		int myCurPage = nPage;
		if (myCurPage > totalPage) {
			myCurPage = totalPage;
		}
		if (myCurPage < 1) {
			myCurPage = 1;
		}
		// 시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		// 끝 페이지
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}
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
		return "/guest/notice";
	}

	@RequestMapping("/guest/noticeView")
	public String noticeView(HttpServletRequest request, Model model) {
		String sNum = request.getParameter("nonum");

		model.addAttribute("notice_View", dao.noticeViewDao(sNum));
		return "/guest/noticeView";
	}

}
