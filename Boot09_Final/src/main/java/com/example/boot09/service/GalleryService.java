package com.example.boot09.service;

import org.springframework.ui.Model;import com.example.boot09.dto.CafeDto;
import com.example.boot09.dto.GalleryDto;

public interface GalleryService {
	public void addToGallery(GalleryDto dto);
	public void selectOne(Model model, int num);
	public void selectPage(Model model, int pageNum);
	public void deleteOne(int num);
	
}
