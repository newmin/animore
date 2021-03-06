package com.proj.animore.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.proj.animore.web.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
						.order(1)
						.addPathPatterns("/**")
						.excludePathPatterns(
							"/","/main/{bcategory}",
							"/login","/logout",
							"/member/**","/join/**",
							"/css/**","/js/**","/img/**",
							"/{bcategory}/**","/search?**",
							"/board/P/**",
							"/board/Q/**",
							"/board/M/**",
							"/board/F/**",
							"/api/hospital",
							"/bhospital/api/**",
							"/images/**");
						//   \" 변수명 \"
	}
	
}