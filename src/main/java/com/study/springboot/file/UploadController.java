package com.study.springboot.file;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class UploadController {

	   @Value("${upload.path}")
	    private String uploadPath;

	    // 파일 업로드 폼
		@RequestMapping("/uploadForm")
		public String uploadForm(){
			
			return "FileUpload/uploadForm";
		}

		// 파일 업로드 처리
		@RequestMapping(value="/uploadAction", method=RequestMethod.POST)
		public String uploadAction(HttpServletRequest request, Model model){
			
			// 뷰로 전달할 정보를 저장하기 위한 Map타입의 변수
			Map returnObj = new HashMap();
			
			try {
				// 서버의 물리적경로 가져오기
				String path = ResourceUtils.getFile(uploadPath).toPath().toString();

	            /*
				 * 파일업로드 위한 MultipartHttpServletRequest객체 생성
				 * 객체 생성과 동시에 파일업로드 완료됨. 
				 * 나머지 폼값은 Multipart가 통째로 받아서 처리한다.
				 */
				MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
				
				// 업로드폼의 file속성 필드의 이름을 모두 읽음
				Iterator<String> itr = mhsr.getFileNames();
				
				MultipartFile mfile = null;			
				String fileName = "";		
				
				// 파일 하나의 정보를 저장하기 위한 List타입의 변수(원본파일명, 저장된파일명 등)
				List resultList = new ArrayList();
							
				// 폼값받기 : 제목
				String title = mhsr.getParameter("title");
					
				// 업로드폼의 file속성의 필드의 갯수만큼 반복
				while (itr.hasNext()) {
					
					// userfile1, userfile2....출력됨
					fileName = (String)itr.next();
					System.out.println(fileName);	
					
					// 서버로 업로드된 임시파일명 가져옴
					mfile = mhsr.getFile(fileName);
					System.out.println(mfile);			//CommonsMultipartFile@1366c0b 형태임
					
					// 한글깨짐방지 처리 후 업로드된 파일명을 가져온다.
					String originalName =  new String(mfile.getOriginalFilename().getBytes(),"UTF-8"); // Linux
											//mfile.getOriginalFilename();
				   
					System.out.println("upload:"+originalName);
					
					// 파일명이 공백이라면 while문의 처음으로 돌아간다.
					if("".equals(originalName)){
						continue;
					}
					System.out.println("originalName:"+originalName);

					// 파일명에서 확장자 가져오기
//					String ext = originalName.substring(originalName.lastIndexOf('.'));
					
					// 파일명을 UUID로 생성된값으로 변경함.
//					String saveFileName = getUuid() + ext;
					String saveFileName = originalName;
					
					// 설정한 경로에 파일저장
					File serverFullName = new File(path + File.separator + saveFileName);
					
					// 업로드한 파일을 지정한 파일에 저장한다.
					mfile.transferTo(serverFullName);
					
					Map file = new HashMap();
					file.put("originalName", originalName);    //원본파일명
					file.put("saveFileName", saveFileName);    //저장된파일명
					file.put("serverFullName", serverFullName);//서버에 저장된 전체경로 및 파일명
					file.put("title", title);                  //타이틀
					
					// 위 파일의 정보를 담은 Map을 List에 저장
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
			
			return "FileUpload/uploadAction";
		}
		
		// uuid 생성할 메서드 선언
		/*
		 * UUID(Universally unique identifier), 범용 고유 식별자.
		 * UUID.randomUUID().toString() 으로 생성하면 
		 * 3d6f4151-5663-4f43-ab16-cb8e38a4ddc6
		 * 와 같이 4개의 하이픈과 32개의 문자로 이루어진 문자열을 반환한다.
		 */
		public static String getUuid(){
			String uuid = UUID.randomUUID().toString();
			//System.out.println(uuid);		
			uuid = uuid.replaceAll("-", "");
			//System.out.println("생성된UUID:"+ uuid);
			return uuid;
		}	

}
