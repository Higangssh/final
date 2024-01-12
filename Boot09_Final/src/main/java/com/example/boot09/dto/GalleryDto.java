package com.example.boot09.dto;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("galleryDto")
@Builder // .num().writer().caption()...형태로 값을 넣어주는 것을 가능하게 한다.
@AllArgsConstructor //모든 값을 한번에 넣은 생성자(@Builder 가 동작하려면 필요함)
@NoArgsConstructor // 디폴트 생성자
@Data //setter,getter 만들기
public class GalleryDto {
	private int num;
	private String writer;
	private String caption;
	private String SaveFileName;
	private String regdate;
	private int	startRowNum;
	private int endRowNum;
	private MultipartFile image; //이미지 파일 업로드 처를를 위한 필드
	private int prevNum;
	private int nextNum;

}
