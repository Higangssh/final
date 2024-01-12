package com.example.boot09.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("cafeDto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CafeDto {
	private int num;
	private String title;
	private String writer;
	private String content;
	private int viewCount;
	private String regdate;
	private int	startRowNum;
	private int endRowNum;
	//검색 기능 관련된 필드
	private String condition ="";
	private String keyword="";
	private int pageNum=1;
}
