package com.usermgmtservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermgmtservice.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
