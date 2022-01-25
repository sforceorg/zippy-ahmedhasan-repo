package com.zippycustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ZippyCustomerApplication implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("/zippy-home");
		registry.addViewController("/register").setViewName("register-customer");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZippyCustomerApplication.class, args);
	}

}
