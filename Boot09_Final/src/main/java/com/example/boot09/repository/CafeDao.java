package com.example.boot09.repository;

import java.util.List;

import com.example.boot09.dto.CafeDto;

public interface CafeDao {
	public void insert(CafeDto dto);
	public CafeDto getData(int num);
	public int getCount(CafeDto dto);
	public List<CafeDto> getList(CafeDto dto);
	public void delete(int num);
	public void viewCount(int num);
	public void update(CafeDto dto);
}
