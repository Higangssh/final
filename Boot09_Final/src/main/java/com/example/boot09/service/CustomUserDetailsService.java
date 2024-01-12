package com.example.boot09.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.boot09.dto.UserDto;
import com.example.boot09.repository.UserDao;

@Service //bean 으로 만들기위해
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDao dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//form 에 입력한 userName 이 전달된다.
		UserDto dto=dao.getData(username);
		//만일 저장된 userName이 없다면
		if(dto==null) {
			//예외를 발생시킨다
			throw new UsernameNotFoundException("존재하지 않는 사용자 입니다");
		}
	
		//2. UserDetails 객체에 해당 정보를 담아서 리턴해 주어야 한다.
		//권한은 1개이지만 list 에 담아서
		List<GrantedAuthority> authList=new ArrayList<GrantedAuthority>();
		//Authority 는 role 앞에 "Role_" 를 붙어주어야한다
		authList.add(new SimpleGrantedAuthority("ROLE_"+dto.getRole()));
		UserDetails ud = new User(dto.getUserName(), dto.getPassword(), authList);
		return ud; 
	}
}
