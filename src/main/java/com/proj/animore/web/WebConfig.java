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
							"/",
							"/login","/logout",
							"/member/**",
							"/css/**","/js/**","/img/**",
							"/main/{bcategory}","/{bcategory}","/inquire/**","/search?**",
							"/board/{bcategory}");
						//   \" 변수명 \"
	}
	
}