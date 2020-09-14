package com.gura.spring05.users.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.users.dao.UsersDao;
import com.gura.spring05.users.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersDao dao; //uesrsdao에 의존

	@Override
	public Map<String, Object> isExistid(String inputId) { //String inputId가 dao에 있는지 확인
		// TODO Auto-generated method stub
		//dao를 이용해서 아이디 존재 여부를 알아내서
		boolean isExist=dao.isExist(inputId);
		//아이디가 존재하는지 여부를 Map에 담아서 리턴해준다
		Map<String, Object> map=new HashMap<>();
		map.put("isExist", isExist);
		return map;
	}

	@Override
	public void addUser(UsersDto dto) {
		//dto 객체에 비밀번호를 암호화 해서 넣어준다.
		String inputPwd=dto.getPwd(); //회원가입 form 에 입력한 비밀번호
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPwd=encoder.encode(inputPwd); //암호화된 비밀번호
		//암호화된 비밀번호를 dto 객체에 다시 넣어준다. 
		dto.setPwd(encodedPwd);
		
		//dao  객체를 이용해서 새로운 사용자 정보를 DB 에 저장하기 
		dao.insert(dto);
	}

	@Override
	public void loginProcess(UsersDto dto, ModelAndView mView, 
			HttpSession session) {
		//dao 객체를 이용해서 id, pwd가 유효한 정보인지 여부를 얻어낸다. 
		//boolean isValid=dao.isValid(dto); 암호화 하기 전에 사용했던 것
		
		//입력한 정보가 유효한 정보인지 여부를 저장할 지역변수 
		boolean isValid=false; //초기값 false
		//로그인폼에 입력한 아이디를 이용해서 DB 에서 select 해본다.
		//존재하지 않는 아이디면 null 이 리턴된다. 
		UsersDto resultDto=dao.getData(dto.getId());
		if(resultDto != null) {//아이디는 존재하는경우(아이디는 일치)
			//DB 에 저장된 암호화된 비밀번호 
			String encodedPwd=resultDto.getPwd();
			//로그인폼에 입력한 비밀번호 
			String inputPwd=dto.getPwd();
			//BCrypt 클래스의 static 메소드를 이용해서 일치 여부를 얻어낸다. 
			isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		}
		
		if(isValid) {//만일 유효한 정보이면 
			//로그인 처리를 한다. 
			session.setAttribute("id", dto.getId());
			//view 페이지에서 사용할 정보 
			mView.addObject("isSuccess", true);
		}else {//아니면 
			mView.addObject("isSuccess", false);
		}
	}

	@Override
	public void getInfo(HttpSession session, ModelAndView mView) {
		//로그인된 아이디를 session객체를 이용해서 얻어온다.
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		mView.addObject("dto", dto);
	}

	@Override
	public void deleteUser(HttpSession session) {
		// TODO Auto-generated method stub
		String id=(String)session.getAttribute("id");
		//삭제
		dao.delete(id);
		//로그아웃처리
		session.invalidate();
	}

	@Override
	public Map<String, Object> saveProfileImage(HttpServletRequest request, MultipartFile mFile) {
		// TODO Auto-generated method stub
		//원본 파일명
		String orgFileName=mFile.getOriginalFilename();
				
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
			mFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e){
			e.printStackTrace();
		}		
		//Map에 업로드된 이미지 파일의 경로를 담아서 리턴한다.
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("imageSrc", "/upload/"+saveFileName);
		return map;
	}

	@Override
	public void updateUser(HttpSession session, UsersDto dto) {
		// TODO Auto-generated method stub
		//로그인된 아이디를 읽어와서
		String id=(String)session.getAttribute("id");
		//UsersDto에 담고
		dto.setId(id);
		//dao를 이용해서 수정반영하기
		dao.update(dto);
	}

	@Override
	public void updateUserPwd(HttpSession session, UsersDto dto, ModelAndView mView) {
		// TODO Auto-generated method stub
		String id=(String)session.getAttribute("id");
		dto.setId(id);
		//작업 성공 여부
		 boolean isSuccess=false;
		 //1. 기존 비밀번호와 암호화된 비밀번호가 일치하는지 확인
		 UsersDto resultDto=dao.getData(id); //null일 가능성은 없다
		//DB에 저장된 암호화된 비밀번호
		String encodedPwd=resultDto.getPwd();
		//로그인 폼에 입력한 비밀번호
		String inputPwd=dto.getPwd();
		//BCrypt클래스의 static메소드를 이용해서 일치여부를 얻어낸다
		boolean isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		
		//2. 만약 일치한다면 새로운 비밀번호를 암호화해서 저장한다.
		if(isValid) {
			//새로운 비밀번호를 암호화 한다.
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			String encodedNewPwd=encoder.encode(dto.getNewPwd());
			//암호화된 새비밀번호를 dto에 다시 넣어준다
			dto.setNewPwd(encodedNewPwd);
			//dao를 이용해서 DB에 반영한다.
			dao.updatePwd(dto);
			//성공
			isSuccess=true;
		}
		//mView 객체에 성공 여부를 담는다.
		mView.addObject("isSuccess",isSuccess);
	}

	@Override
	public Map<String, Object> ajaxLoginProcess(UsersDto dto, HttpSession session) {
		//dao 객체를 이용해서 id, pwd가 유효한 정보인지 여부를 얻어낸다. 
		//boolean isValid=dao.isValid(dto); 암호화 하기 전에 사용했던 것
		
		//입력한 정보가 유효한 정보인지 여부를 저장할 지역변수 
		boolean isValid=false; //초기값 false
		//로그인폼에 입력한 아이디를 이용해서 DB 에서 select 해본다.
		//존재하지 않는 아이디면 null 이 리턴된다. 
		UsersDto resultDto=dao.getData(dto.getId());
		if(resultDto != null) {//아이디는 존재하는경우(아이디는 일치)
			//DB 에 저장된 암호화된 비밀번호 
			String encodedPwd=resultDto.getPwd();
			//로그인폼에 입력한 비밀번호 
			String inputPwd=dto.getPwd();
			//BCrypt 클래스의 static 메소드를 이용해서 일치 여부를 얻어낸다. 
			isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		}
		Map<String, Object> map=new HashMap<>();
		if(isValid) {//만일 유효한 정보이면 
			//로그인 처리를 한다. 
			session.setAttribute("id", dto.getId());
			map.put("isSuccess", true);
			map.put("id", dto.getId());
		}else {//아니면 
			map.put("isSuccess", false);
		}
		return map;
	}

	@Override
	public boolean jsonpLogin(UsersDto dto) {
		//입력한 정보가 유효한 정보인지 여부를 저장할 지역변수 
				boolean isValid=false; //초기값 false
				//로그인폼에 입력한 아이디를 이용해서 DB 에서 select 해본다.
				//존재하지 않는 아이디면 null 이 리턴된다. 
				UsersDto resultDto=dao.getData(dto.getId());
				if(resultDto != null) {//아이디는 존재하는경우(아이디는 일치)
					//DB 에 저장된 암호화된 비밀번호 
					String encodedPwd=resultDto.getPwd();
					//로그인폼에 입력한 비밀번호 
					String inputPwd=dto.getPwd();
					//BCrypt 클래스의 static 메소드를 이용해서 일치 여부를 얻어낸다. 
					isValid=BCrypt.checkpw(inputPwd, encodedPwd);
				}
				
				if(isValid) {//만일 유효한 정보이면 
					return true;
				}else {//아니면 
					return false;
				}
	}
	
}
