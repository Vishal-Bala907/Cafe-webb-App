package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entities.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
