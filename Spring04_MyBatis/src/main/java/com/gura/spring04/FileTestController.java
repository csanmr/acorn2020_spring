package com.gura.spring04;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileTestController {
	
	//<input type="file" name="myFile"/> 하나만 전송되는 경우 였다가 @RequestParam String Title이 붙음
	@RequestMapping("/upload")                    //name과 같은 값=myFile
	public String upload(@RequestParam MultipartFile myFile, HttpServletRequest request,@RequestParam String title) {
		//원본 파일명
		String orgFileName=myFile.getOriginalFilename();
		//파일의 크기
		long fileSize=myFile.getSize();
		
		// webapp/upload 폴더 까지의 실제 경로(서버의 파일시스템 상에서의 경로)
		String realPath=request.getServletContext().getRealPath("/upload");
		//저장할 파일의 상세 경로 File.separator은 운영체제에 따라 /(슬래시)나 \(역슬래시)
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File upload=new File(filePath);
		if(!upload.exists()) { //만일 upload디렉토리가 존재하지 않으면
			upload.mkdir(); //만들어준다.
		}
		//저장할 파일 명을 구성한다.
		String saveFileName=System.currentTimeMillis()+orgFileName;
		try {
			//upload 폴더에 파일을 저장한다. 업로드한 파일을 filePath+saveFileName으로 저장한다는 뜻
			myFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("orgFileName", orgFileName);
		request.setAttribute("saveFileName", saveFileName);
		request.setAttribute("fileSize", fileSize);
		request.setAttribute("title", title);
		return "upload";
	}
	//input type이 여러개 일 때
	@RequestMapping("/upload2")                    
	public String upload2(HttpServletRequest request,FileDto dto) {
		//FileDto 객체에 있는 MultiPartFile 객체의 참조값 얻어오기
		MultipartFile myFile=dto.getMyFile();
		//원본 파일명
		String orgFileName=myFile.getOriginalFilename();
		//파일의 크기
		long fileSize=myFile.getSize();
		
		// webapp/upload 폴더 까지의 실제 경로(서버의 파일시스템 상에서의 경로)
		String realPath=request.getServletContext().getRealPath("/upload");
		//저장할 파일의 상세 경로 File.separator은 운영체제에 따라 /(슬래시)나 \(역슬래시)
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File upload=new File(filePath);
		if(!upload.exists()) { //만일 upload디렉토리가 존재하지 않으면
			upload.mkdir(); //만들어준다.
		}
		//저장할 파일 명을 구성한다.
		String saveFileName=System.currentTimeMillis()+orgFileName;
		try {
			//upload 폴더에 파일을 저장한다. 업로드한 파일을 filePath+saveFileName으로 저장한다는 뜻
			myFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//업로드 된 파일 정보를 FileDto에 담는다// title은 이미 담겨있음
		dto.setOrgFileName(orgFileName);
		dto.setSaveFileName(saveFileName);
		dto.setFileSize(fileSize);;
		
		//request 영역에 FileDto 담기
		request.setAttribute("dto", dto);
		// /WEB-INF/views/upload2.jsp 페이지로 forward이동해서 응답
		return "upload2";
	}
}
