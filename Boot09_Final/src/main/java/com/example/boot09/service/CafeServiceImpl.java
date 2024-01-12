package com.example.boot09.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.boot09.dto.CafeDto;
import com.example.boot09.exception.NotOwnerExeption;
import com.example.boot09.repository.CafeDao;
import com.example.boot09.repository.GalleryDao;

@Service
public class CafeServiceImpl implements CafeService {

	
	//한 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	//하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	@Autowired private CafeDao dao;
	@Override
	public void addToCafe(CafeDto dto) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(userName);
		dto.setWriter(userName);
		dao.insert(dto);
		
	}

	@Override
	public void selectOne(Model model, int num) {
		CafeDto dto =dao.getData(num);
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("dto",dto);
		model.addAttribute("userName",userName);
		
	}

	@Override
	public void selectPage(Model model, CafeDto dto) {
		//보여줄 페이지의 시작 ROWNUM
		int pageNum= dto.getPageNum();
		int startRowNum = 1 + (pageNum-1) * PAGE_ROW_COUNT;
		//보여줄 페이지의 끝 ROWNUM
		int endRowNum = pageNum * PAGE_ROW_COUNT;
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT) * PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum = startPageNum + PAGE_DISPLAY_COUNT - 1;
	   
		//전체 row 의 갯수
		int totalRow = dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount = (int)Math.ceil(totalRow / (double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum = totalPageCount; //보정해 준다. 
		}
		
		//startRowNum 과 endRowNum  을 GalleryDto 객체에 담고
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		List<CafeDto> list = dao.getList(dto);
		
		//view page 에서 필요한 값을 Model 객체에 담기 
		model.addAttribute("list", list);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("dto", dto);
		model.addAttribute("totalRow",totalRow);
		
	}

	@Override
	public void deleteOne(int num) {
		String writer=dao.getData(num).getWriter();
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		if(!writer.equals(userName)) {
			throw new NotOwnerExeption("글 작성자와 일치 하지 않습니다");
		}
		dao.delete(num);
		
	}

	@Override
	public void updateOne(int num) {
		dao.viewCount(num);	
	}

	@Override
	public void updateContent(CafeDto dto) {
		dao.update(dto);
		
	}

}
