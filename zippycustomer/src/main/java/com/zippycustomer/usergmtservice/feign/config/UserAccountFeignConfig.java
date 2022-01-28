package com.zippycustomer.usergmtservice.feign.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class UserAccountFeignConfig {

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${usermgmtservice.apiKey}") String apiKey,@Value("${usermgmtservice.apiPassword}") String apiPassword) {
		return new BasicAuthRequestInterceptor(apiKey, apiPassword);
	}
}
