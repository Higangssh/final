package com.example.boot09.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.boot09.dto.UserDto;
import com.example.boot09.repository.UserDao;

@Service
public class UserServiceImpl implements UserService{
	//서비스는 dao를 이용해서 로직 처리를 한다
	@Autowired
	private UserDao dao;
	//비밀번호 암호화는 객체도 bean 으로 등록이 되어 있다.
	@Autowired
	private PasswordEncoder encoder;
	@Value("${file.location}")
	private String fileLocation;
	
	@Override
	public void addUser(UserDto dto) {
		String encodedpwd= encoder.encode(dto.getPassword());
		dto.setPassword(encodedpwd);
		//일반 사용자라는 의미에서 role 에 "USER"를 넣어준다
		dto.setRole("USER");
		dao.insert(dto);
	}

	@Override
	public void getInfo(Model model) {
		//로그인된 userName
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		UserDto dto= dao.getData(userName);
		model.addAttribute("dto", dto);
	}

	@Override
	public void updateUser(UserDto dto) {
		MultipartFile image=dto.getImage();
		if(image.getSize() != 0) {
			String saveFileName=UUID.randomUUID().toString();
			//저장할 파일의 전체 경로 구성하기
			String filePath=fileLocation+File.separator+saveFileName;
			try {
				File f=new File(filePath);
				image.transferTo(f);
			}catch (Exception e) {
				e.printStackTrace();
			}
			dto.setProfile(saveFileName);
		}
		
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		dto.setUserName(userName);
		
		dao.update(dto);
	}

	@Override
	public void updatePassowr(UserDto dto) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		String password=dao.getData(userName).getPassword();
		boolean isValid=BCrypt.checkpw(dto.getPassword(), password);
		if(!isValid) {
			throw new RuntimeException("비밀번호가 안맞아요!");
		}
		
		String encodedPwd=encoder.encode(dto.getNewPassword());
		dto.setNewPassword(encodedPwd);
		dto.setUserName(userName);
		dao.updatePwd(dto);
//		//로그 아웃처리(인증정보 초기화)
//		SecurityContextHolder.clearContext();
	}

}
