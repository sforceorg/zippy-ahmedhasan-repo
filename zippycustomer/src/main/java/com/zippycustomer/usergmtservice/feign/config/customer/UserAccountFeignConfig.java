package com.zippycustomer.usergmtservice.feign.config.customer;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zippycustomer.dto.customer.ErrorInfoDto;
import com.zippycustomer.service.customer.exceptions.OtpAlreadyVerifiedException;
import com.zippycustomer.service.customer.exceptions.OtpMismatchException;
import com.zippycustomer.service.customer.exceptions.UnknownVerificationTypeException;
import com.zippycustomer.service.customer.exceptions.UserAccountAlreadyActivatedException;
import com.zippycustomer.service.customer.exceptions.UserAccountException;
import com.zippycustomer.service.customer.exceptions.UserAccountNotFoundException;

import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
/**
 * 
 * @author ahmed
 *
 */

@Configuration
public class UserAccountFeignConfig {
	private final static Logger logger = LoggerFactory.getLogger(UserAccountFeignConfig.class);
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${usermgmtservice.apiKey}") String apiKey,
			@Value("${usermgmtservice.apiPassword}") String apiPassword) {
		return new BasicAuthRequestInterceptor(apiKey, apiPassword);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new UserAccountExceptionDecoder();
	}

/**
 * 	
 * This is errorDecoder class, @TODO needs to be revisited/revised
 *
 */
	private final class UserAccountExceptionDecoder implements ErrorDecoder {
		
		@Override
		public Exception decode(String methodKey, Response response) {
			ObjectMapper objectMapper = null;
			ErrorInfoDto errorInfoDto = null;
			UserAccountException exception = null;
			Reader reader = null;
			
			try {
				exception = new UserAccountException(0, "");
				objectMapper = new ObjectMapper();
				reader = response.body().asReader(Charset.forName("utf-8"));
				logger.info("reader value = "+reader.toString());
				errorInfoDto = objectMapper.readValue(reader, ErrorInfoDto.class);
				logger.info("errorInfoDto : {}",errorInfoDto.toString());
			} catch (StreamReadException e) {
				logger.warn("Error occured while decodeing the error response ", e);
			} catch (DatabindException e) {
				logger.warn("Error occured while decodeing the error response ", e);
			} catch (IOException e) {
				logger.warn("Error occured while decodeing the error response ", e);
			}
			logger.info("decode the error accordingly");
			if (response.status() == HttpStatus.ALREADY_REPORTED.value()) {
				exception = new UserAccountAlreadyActivatedException(errorInfoDto.getErrorCode(), errorInfoDto.getErrorMessage());
			} else if  (response.status() == HttpStatus.NOT_FOUND.value()) {
				exception = new UserAccountNotFoundException(errorInfoDto.getErrorCode(), errorInfoDto.getErrorMessage()); 
			} else if (response.status() == HttpStatus.NOT_ACCEPTABLE.value()) {
				exception = new UnknownVerificationTypeException(errorInfoDto.getErrorCode(), errorInfoDto.getErrorMessage());
			} else if (response.status()== HttpStatus.BAD_REQUEST.value()) {
				logger.info("bad request please decode it");
				logger.info("printing the errorInfoDto {} "+ errorInfoDto.toString());
				exception = new OtpMismatchException(errorInfoDto.getErrorCode(), errorInfoDto.getErrorMessage());
			} else if (response.status()== HttpStatus.TOO_MANY_REQUESTS.value()) {
				  exception = new OtpAlreadyVerifiedException(errorInfoDto.getErrorCode(), errorInfoDto.getErrorMessage()); 
			}
				 
			logger.info("returning the decoded exception");
			return exception;
		}
	}
}
