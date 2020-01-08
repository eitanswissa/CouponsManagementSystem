package com.eitan.couponsproject.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eitan.couponsproject.entities.Company;

public interface ICompanyDao extends CrudRepository<Company, Long> {

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.name= :companyName")
	boolean isExistsByName(@Param("companyName") String companyName);


}
