package com.weather.Temper;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface detailRepository extends JpaRepository<Weather, String>{

	@Transactional
	@Modifying
	@Query(value="insert into weather(city,details) values (:location,:details)", nativeQuery = true)
	void insertdetails(@Param(value="location") String location, @Param(value="details") String details);

}
