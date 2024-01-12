package com.example.boot09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//업로드된 파일을 저장하는 경로
@PropertySource(value="classpath:custom.properties")
@SpringBootApplication
public class Boot09FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(Boot09FinalApplication.class, args);
//		Runtime rt = Runtime.getRuntime();
//        try {
//            rt.exec("cmd /c start chrome.exe http://localhost:9000/boot09");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}

}
