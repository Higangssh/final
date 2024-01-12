package com.example.boot09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //설정 클래스라고 알려준다
@EnableWebSecurity // security 를 설정하기 위한 어노테이션
public class SecurityConfig {
	@Bean // 메소드에서 리턴되는 SecurityFilterChain 을 bean 으로 만들어준다.
	public SecurityFilterChain seFilterChain(HttpSecurity httpSecurity) throws Exception{
		//화이트 리스트를 미리 배열에 넣어두기
		String[] whiteList= {"/","/gallery/list","/user/signup_form",
				"/user/signup","/user/loginform","/user/login_fail",
				"/user/expired","/upload/images/**" ,"/gallery/detail",
				"/cafe/list", "/cafe/detail"};
		//메소드의 매개변수 Http security 의 참조값이 전달되는데 해당 객체를 이용해서 설정을 한다음에
		httpSecurity
		// csrf 보안 설정 끄기
		.csrf(config -> config.disable())
		.authorizeHttpRequests(config-> 
			config
			.requestMatchers(whiteList).permitAll() //whiteList 요청은 로그인과 상관없이 모두 허용
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.requestMatchers("/staff/**").hasAnyRole("ADMIN","STAFF")
			.anyRequest().authenticated() //위에 명시한 이외의 모든요청은 로그인해야지 요청 가능하게 한다.
		)
		.formLogin((config)-> 
			config
			//인증을 거치지 않은 사용자를 리다일렉트 시킬 경로
			.loginPage("/user/required_loginform")
			.loginProcessingUrl("/user/login")
			.usernameParameter("userName")
			.passwordParameter("password")
			.successHandler(new AuthSuccessHandler()) //로그인 성공 핸들러 등록
			//.successForwardUrl("/user/login_success")
			.failureForwardUrl("/user/login_fail")
			.permitAll()
		)
		.logout(config->
				config
					.invalidateHttpSession(true)
					.logoutUrl("/user/logout")
					.logoutSuccessUrl("/")
					.permitAll()
					
				)
		.exceptionHandling(config ->
			//403 forbidden 인 경우 forward 이동 시킬 경로 설정
			config.accessDeniedPage("/user/denied")
		)
		.sessionManagement(config ->
			config
				.maximumSessions(1) //최대 허용 세션 갯수
				.expiredUrl("/user/expired") //허용 세션 갯수가 넘어서 로그인 해체된 경우 리다일렉트 이동시킬 경로
		);
		//설정된 정보대로 SecurityFilterChain 객체를 만들어서 반환한다
		 
		return httpSecurity.build();
	}
	
	//비밀번호를 암호화 해주는 객체를 bean 으로 만든다.
	@Bean
	PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	//인증 메니저 객체를 bean 으로 만든다. (Spring Security 가 자동 로그인 처리할때도 사용되는 객체)
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, 
			BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) throws Exception {
	    
		return http.getSharedObject(AuthenticationManagerBuilder.class) 
	      .userDetailsService(userDetailService) // 유저 정보를 어떻게 구해올것인지
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}
}

