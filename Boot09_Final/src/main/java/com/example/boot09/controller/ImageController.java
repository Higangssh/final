package com.example.boot09.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
	
	@Value("${file.location}")
	private String fileLocation;
	
	@ResponseBody
	@GetMapping(		
			//jpg , png, gif 이미지 데이터를 응답할수 있도록 produces 에 배열로 전달한다
			value = "/upload/images/{imageName}", 		
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, 
			MediaType.IMAGE_GIF_VALUE}
	)
	public byte[] image(@PathVariable("imageName") String name) throws IOException {
		String absolutePath = fileLocation + File.separator + name;
		InputStream is=new FileInputStream(absolutePath);
		byte[] bytes= null;
		bytes=IOUtils.toByteArray(is);
		//FileUtils.readFileToByteArray(new File(absolutePath));
		System.out.println(name);
		return bytes;
	}
}
