package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.entities.Attendence;

public interface AttendenceRepo extends JpaRepository<Attendence, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Attendence a WHERE a.att_user.u_id =:key")
	void deleteByForeingKey(@Param("key") long key);
}
