package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entities.UserBag;

public interface BagRepo extends JpaRepository<UserBag, Long> {

}


