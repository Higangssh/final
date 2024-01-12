package com.example.boot09.repository;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.boot09.dto.UserDto;


@Repository
public class UserDaolmpl implements UserDao{
	@Autowired
	SqlSession session; 
	public void insert(UserDto dto) {
		session.insert("user.insert" , dto);
	}
	@Override
	public UserDto getData(String userName) {
		
		return session.selectOne("user.getData", userName);
	}
	@Override
	public void update(UserDto dto) {
		session.update("user.update", dto);
		
	}
	@Override
	public void updatePwd(UserDto dto) {
		session.update("user.updatePwd", dto);
		
	}
}
