package com.study.springboot.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadController {

	 @Value("${upload.path}")
	    private String uploadPath;

	    // 파일목록보기
		@RequestMapping("/fileList")
		public String fileList(HttpServletRequest request,
				HttpServletResponse response, Model model) throws IOException
		{
			// 서버의 물리적 경로 얻어오기
			String saveDirectory = ResourceUtils.getFile(uploadPath).toPath().toString();
			System.out.println("saveDirectory:"+saveDirectory);

			// File객체 생성
			File file = new File(saveDirectory);

			// 파일 목록 얻어오기
			File[] files = file.listFiles();
			
			Map<String, Integer> fileMap = new HashMap<>();
					
			for (File f : files) {
				// Key:파일명 , Value:파일크기
				fileMap.put(f.getName(), (int)Math.ceil(f.length()/1024.0));
				//print.println(fname +"---"+ fsize +"<br/>");		
			}		
					
			model.addAttribute("fileMap", fileMap);				
			return "FileUpload/fileList"; 
		}
		
		// 파일 다운로드	
		@RequestMapping("/download")	
		public void download(HttpServletRequest request, 
	            HttpServletResponse response) throws Exception
		{
			String fileName = request.getParameter("fileName");
			String oriFileName = request.getParameter("oriFileName");
			
			// 데이터베이스를 사용했다면 Original File Name이 저장되어 있겠지만
			// 이 예제에서는 다음과 같이 구함.
			// ----------------------------------------------------------------
			int i = fileName.indexOf(".");
			oriFileName = fileName.substring(i+1);
			// ----------------------------------------------------------------

//		    String saveDirectory = request
//		    		.getSession().getServletContext()
//		    		.getRealPath("/resources/upload");
			String saveDirectory = ResourceUtils.getFile(uploadPath).toPath().toString();
		    
		    File downloadFile = new File(saveDirectory + File.separator + fileName);
		    
		    if (!downloadFile.canRead()) {
		        throw new Exception("File can't read(파일을 찾을 수 없습니다)");
		    }

		    String userAgent = request.getHeader("User-Agent");
	        boolean ie = userAgent.indexOf("MSIE") > -1;
	        String fileName2 = null;
	        if(ie) {
	            fileName2 = URLEncoder.encode(oriFileName, "utf-8");
	        } else {
	            fileName2 = new String(oriFileName.getBytes("utf-8"), "iso-8859-1");
	        }

		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("apllication/download; charset=utf-8");
		    response.setHeader("Content-Disposition", "attachment;filename=" + fileName2);
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(downloadFile));
		    BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
		      
		    byte[] buffer = new byte[1024];
		    int bytesRead = 0;
		    while ((bytesRead = inStrem.read(buffer)) != -1) {
		        outStream.write(buffer, 0, bytesRead);
		    }
		    outStream.flush();
		    inStrem.close();
		}	

}
