package com.cafe.repos;

import java.util.List;

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

	@Query("SELECT o FROM Orders o WHERE o.dispatched = false and o.cancled = false")
	List<Orders> findUnDispatchedOrders();

	@Query("SELECT o FROM Orders o WHERE o.o_id =:id")
	Orders findById(@Param("id") long id);

	@Query("SELECT o FROM Orders o WHERE o.timestamp >=:timestamp")
	List<Orders> findByDate(@Param("timestamp") long timestamp); 
}
