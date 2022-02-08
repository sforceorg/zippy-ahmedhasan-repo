package com.partnerstoreservice.service;

import java.util.List;

import com.partnerstoreservice.dto.CityDto;
import com.partnerstoreservice.dto.ServiceAreaDto;
import com.partnerstoreservice.dto.StateDto;

/**
 * @author ahmed
 */
public interface StoreManagementService {
	public List<StateDto> getStates(String countryName);
	public List<CityDto> getCities(String stateName);
	public List<ServiceAreaDto> getServiceAreas(String cityName);
}
