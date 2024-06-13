package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.entities.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Orders o WHERE o.user_details.u_id =:key")
	void deleteByForeingKey(@Param("key") long key);
}
