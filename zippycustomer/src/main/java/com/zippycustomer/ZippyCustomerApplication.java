package com.zippycustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients
public class ZippyCustomerApplication implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		System.out.println("inside the addview controller");
		registry.addViewController("/home").setViewName("/zippy-home");
		//registry.addViewController("/register").setViewName("register-customer");
		registry.addViewController("/login").setViewName("customer-login");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZippyCustomerApplication.class, args);
		//System.out.println("the apis has been modified>>>");
	}

}
