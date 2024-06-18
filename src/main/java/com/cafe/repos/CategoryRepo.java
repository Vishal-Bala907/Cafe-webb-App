package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	
	Category findBycatagoryName(String categoryName);
	Category findById(long id);

}
