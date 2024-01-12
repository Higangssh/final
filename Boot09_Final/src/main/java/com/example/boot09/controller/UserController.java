package com.example.boot09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.boot09.dto.UserDto;
import com.example.boot09.repository.UserDao;
import com.example.boot09.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	//util 역할을 하는 서비스 객체를 인터페이스 type 으로 DI 받아서 사용한다.
	@PostMapping("/user/update")
	public String update(UserDto dto) {
		service.updateUser(dto);
		return "redirect:/user/info";
	}
	@GetMapping("/user/pwd_updateform")
	public String pwdUpdateform(UserDto dto) {
		return"user/pwd_updateform";
	}
	
	@PostMapping("/user/pwd_update")
	public String pwdUpdate(UserDto dto, HttpSession session) {
		service.updatePassowr(dto);
		//강제 로그아웃 처리
		session.invalidate();
		return"user/pwd_update";
	}
	
	@Autowired
	private UserService service;
	@GetMapping("/user/updateform")
	public String updateform(Model model) {
		service.getInfo(model);
		return "user/updateform";
		
	}
	
	@GetMapping("/user/info")
	public String info(Model model) {
		service.getInfo(model);
		return "user/info";
	}
	
	@PostMapping("/user/signup")
	public String signup(UserDto dto, HttpServletRequest req) {
		System.out.println(req.getParameterNames());
		//서비스(util) 객체를 이용해서 가입정보를 등록한다
		service.addUser(dto);
		
		return "user/signup";
	}
	@GetMapping("/user/signup_form")
	public String signupForm() {
		return "user/signup_form";
	}
	@GetMapping("/user/expired")
	public String userExpired() {
		return "user/expired";
	}
	
	@GetMapping("/user/denied")
	public String userDenied() {
		return "user/denied";
	}
	
	@GetMapping("/staff/user/list")
	public String userList() {
		return "user/list";
	}
	@GetMapping("/admin/user/manage")
	public String userManage() {
		return "user/manage";
	}
	//로그인이 필요한 요청경로를 로그인 하지 않은 상태로 요청하면 리다일렉트 되는 요청경로
	@GetMapping("/user/required_loginform")
	public String required_loginform() {
		return "user/required_loginform";
	}
	
	@GetMapping("/user/loginform")
	public String loginform() {
		
		return "user/loginform";
	}
	@PostMapping("/user/login_success")
	public String loginSuceess() {
		return "user/login_success";
	}
	@PostMapping("/user/login_fail")
	public String loginFail() {
		return "user/login_fail";
	}
	
}
