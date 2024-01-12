package com.example.boot09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boot09.dto.CafeDto;
import com.example.boot09.service.CafeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class CafeController {
	@Autowired private CafeService service;
	
	
	@PostMapping("/cafe/update")
	public String update(CafeDto dto) {
		service.updateContent(dto);
		
		return "redirect:/cafe/detail?num="+dto.getNum();
	}
	
	@GetMapping("/cafe/updateform")
	public String updateform(Model model, int num) {
		service.selectOne(model, num);
		return "cafe/updateform";
	}
	
	@GetMapping("/cafe/list")
	public String list(Model model, CafeDto dto) {
		
		log.trace("trace");
		log.debug("debug"); 
		log.info("info");
		log.warn("warn"); 
		log.error("error");
		log.fatal("fatal");
		
		service.selectPage(model, dto);
		return "cafe/list";
	}
	@GetMapping("/cafe/detail")
	public String detail(Model model,  int num) {
		service.selectOne(model, num);//detail 정보가저오기
		service.updateOne(num);//Count 올리기
		return "cafe/detail";
	}
	@GetMapping("/cafe/insertform")
	public String insertForm() {
		
		return "cafe/insertform";
	}
	@PostMapping("/cafe/insert")
	public String insert(CafeDto dto) {
		System.out.println(dto.getTitle());
		service.addToCafe(dto);
		return "cafe/insert";
	}
	@GetMapping("/cafe/delete")
	public String delete(int num, Model model) {
		service.deleteOne(num);
		//service.selectPage(model, 1);
		return "redirect:/cafe/list";
	}
}
