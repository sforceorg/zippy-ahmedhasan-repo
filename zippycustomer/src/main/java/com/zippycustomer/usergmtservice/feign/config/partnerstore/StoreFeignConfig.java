package com.zippycustomer.usergmtservice.feign.config.partnerstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zippycustomer.usergmtservice.feign.config.customer.UserAccountFeignConfig;

import feign.auth.BasicAuthRequestInterceptor;

/**
 * @author ahmed
 */

//@Configuration
public class StoreFeignConfig {

	private final static Logger logger = LoggerFactory.getLogger(UserAccountFeignConfig.class);

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
			@Value("${partnerstoreservice.apiKey}") String apiKey,
			@Value("${partnerstoreservice.apiPassword}") String apiPassword) {
		return new BasicAuthRequestInterceptor(apiKey, apiPassword);
	}

}
