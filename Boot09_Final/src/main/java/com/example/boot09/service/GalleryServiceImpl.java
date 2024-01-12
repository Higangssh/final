package com.example.boot09.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.boot09.dto.GalleryDto;
import com.example.boot09.exception.NotOwnerExeption;
import com.example.boot09.repository.GalleryDao;

@Service
public class GalleryServiceImpl implements GalleryService {
	
	//한 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=8;
	//하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	@Autowired private GalleryDao dao;
	
	@Value("${file.location}")
	private String fileLocation;
	
	@Override
	public void addToGallery(GalleryDto dto) {

		/*
		 *  컨트롤러에서 전달한 GalleryDto 에는 string caption 과 MulitpartFile image 정보만 들어 있다.
		 */
		System.out.println("하이");
		System.out.println(dto.getCaption());
		System.out.println(dto.getImage());
		String saveFileName=UUID.randomUUID().toString();
		//저장할 파일의 전체 경로 구성하기
		String filePath=fileLocation+File.separator+saveFileName;
		try {
			File f=new File(filePath);
			dto.getImage().transferTo(f);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// 로그인된 사용자(userName) 읽어오기
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		//GalleryDto 에 추가정보를 담고
		dto.setSaveFileName(saveFileName);
		dto.setWriter(userName);
		//DB에 저장하기
		dao.insert(dto);
		
	}

	@Override
	public void selectOne(Model model, int num) {
		
		GalleryDto dto= dao.getData(num);
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("dto",dto);
		model.addAttribute("userName", userName);
		
	}

	@Override
	public void selectPage(Model model, int pageNum) {
		//보여줄 페이지의 시작 ROWNUM
		int startRowNum = 1 + (pageNum-1) * PAGE_ROW_COUNT;
		//보여줄 페이지의 끝 ROWNUM
		int endRowNum = pageNum * PAGE_ROW_COUNT;
		
		//startRowNum 과 endRowNum  을 GalleryDto 객체에 담고
		GalleryDto dto = new GalleryDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
	   
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT) * PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum = startPageNum + PAGE_DISPLAY_COUNT - 1;
	   
		//전체 row 의 갯수
		int totalRow = dao.getCount();
		//전체 페이지의 갯수 구하기
		int totalPageCount = (int)Math.ceil(totalRow / (double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum = totalPageCount; //보정해 준다. 
		}
		//GalleryDao 객체를 이용해서 회원 목록을 얻어온다.
		List<GalleryDto> list = dao.getList(dto);
		
		//view page 에서 필요한 값을 Model 객체에 담기 
		model.addAttribute("list", list);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("pageNum", pageNum);
	}

	@Override
	public void deleteOne(int num) {
		System.out.println(num);
		//삭제할 글의 정보를 얻어온다
		GalleryDto dto=dao.getData(num);
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		if(!dto.getWriter().equals(userName)) {
			//예외 발생시킨다
			throw new NotOwnerExeption("이건 당신의 소유가 아닙니다");
		}
		//사진을 파일 시스템에서 삭제
		String filepath=fileLocation+File.separator+dto.getSaveFileName();
		new File(filepath).delete();
		//DB 에서 삭제
		dao.delete(num);
	}
	
	

}
