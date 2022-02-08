package com.partnerstoreservice.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.partnerstoreservice.dto.CityDto;
import com.partnerstoreservice.dto.ServiceAreaDto;
import com.partnerstoreservice.dto.StateDto;
import com.partnerstoreservice.repositories.CityRepository;
import com.partnerstoreservice.repositories.ServiceAreaRepository;
import com.partnerstoreservice.repositories.StateRepository;

/**
 * @author ahmed
 */

@Service
public class StoreManagementServiceImpl implements StoreManagementService {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ServiceAreaRepository serviceAreaRepository;

	@Override
	public List<StateDto> getStates(String countryName) {
		return this.stateRepository.findStatesInCountry(countryName).stream().map((state) -> {
			StateDto stateDto = null;
			stateDto = new StateDto();
			stateDto.setStateId(state.getStateId());
			stateDto.setStateName(state.getStateName());
			return stateDto;

		}).collect(Collectors.toList());
	}

	@Override
	public List<CityDto> getCities(String stateName) {
		return this.cityRepository.findCitiesInState(stateName).stream().map((city) -> {
			CityDto cityDto = null;
			cityDto = new CityDto();
			cityDto.setCityId(city.getCityId());
			cityDto.setCityName(city.getCityName());
			return cityDto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<ServiceAreaDto> getServiceAreas(String cityName) {
		return this.serviceAreaRepository.findServiceAreasInCity(cityName).stream().map((serviceArea) -> {
			ServiceAreaDto serviceAreaDto = null;
			serviceAreaDto = new ServiceAreaDto();
			serviceAreaDto.setServiceAreaId(serviceArea.getServiceAreaId());
			serviceAreaDto.setServiceAreaName(serviceArea.getServiceAreaName());
			return serviceAreaDto;
		}).collect(Collectors.toList());
	}
}
