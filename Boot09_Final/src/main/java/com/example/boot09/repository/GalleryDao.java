package com.example.boot09.repository;

import java.util.List;

import com.example.boot09.dto.GalleryDto;

public interface GalleryDao {
	public void insert(GalleryDto dto);
	public GalleryDto getData(int num);
	public int getCount();
	public List<GalleryDto> getList(GalleryDto dto);
	public void delete(int num);
}
