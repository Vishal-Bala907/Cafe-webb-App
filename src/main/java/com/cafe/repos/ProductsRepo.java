package com.cafe.repos;

import java.util.List;

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
	
	@Query("SELECT p FROM Products p WHERE p.pro_Id =:id")
	Products findByproId(long id);
	
	@Query("SELECT p FROM Products p WHERE p.productName =:name")
	List<Products> findByName(String name);
}
