package com.example.boot09.repository;

import com.example.boot09.dto.UserDto;

public interface UserDao {
	public void insert(UserDto dto);
	public UserDto getData(String userName);
	public void update(UserDto dto);
	public void updatePwd(UserDto dto);
}
