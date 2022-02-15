package com.zippycustomer.service.partnerstore;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zippycustomer.dto.partnerstore.CityDto;
import com.zippycustomer.dto.partnerstore.ServiceAreaDto;
import com.zippycustomer.dto.partnerstore.StateDto;
import com.zippycustomer.usergmtservice.feign.config.partnerstore.StoreFeignConfig;

/**
 * @author ahmed
 */

@FeignClient(name = "storeApiService", url = "${partnerstoreservice.apiUrl}/store", configuration = StoreFeignConfig.class)
public interface StoreApiService {

	@RequestMapping(value = "/states/{countryName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<StateDto> getStates(@PathVariable("countryName") String countryName);

	@RequestMapping(value = "/cities/{stateName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CityDto> getCities(@PathVariable("stateName") String stateName);

	@RequestMapping(value = "/serviceAreas/{cityName}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ServiceAreaDto> getServiceAreas(@PathVariable("cityName") String cityName);

}
