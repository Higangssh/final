package com.example.boot09.repository;

import java.util.List;

import org.apache.catalina.Session;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.boot09.dto.CafeDto;
import com.example.boot09.dto.GalleryDto;

@Repository
public class CafeDaolmpl implements CafeDao {

	@Autowired private SqlSession session;
	@Override
	public void insert(CafeDto dto) {
		session.insert("cafe.insert",dto);	
	}

	@Override
	public CafeDto getData(int num) {
		return session.selectOne("cafe.getData", num);
	}

	@Override
	public int getCount(CafeDto dto) {
		return session.selectOne("cafe.getCount",dto);
	}

	@Override
	public List<CafeDto> getList(CafeDto dto) {
		return session.selectList("cafe.getList",dto);
	}

	@Override
	public void delete(int num) {
		session.delete("cafe.delete",num);
		
	}

	@Override
	public void viewCount(int num) {
		 session.update("cafe.viewCount",num);
	}

	@Override
	public void update(CafeDto dto) {
		session.update("cafe.update", dto);
		
	}

}
