package com.example.boot09.service;

import org.springframework.ui.Model;

import com.example.boot09.dto.CafeDto;



public interface CafeService {
	public void addToCafe(CafeDto dto);
	public void selectOne(Model model, int num);
	public void selectPage(Model model, CafeDto dto);
	public void deleteOne(int num);
	public void updateOne(int num);
	public void updateContent(CafeDto dto);
}
