package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe.entities.Products;

import jakarta.transaction.Transactional;

public interface ProductsRepo extends JpaRepository<Products, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Products a WHERE a.category.c_Id =:key")
	void deleteByForeingKey(@Param("key") long key);
}
