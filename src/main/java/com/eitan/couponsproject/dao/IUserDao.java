package com.eitan.couponsproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.entities.Coupon;
import com.eitan.couponsproject.entities.User;

public interface IUserDao extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.userName= :userName AND u.password= :password")
	User findUserByUserNameAndPassword(@Param("userName") String userName, @Param("password")  String password);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userName = :userName")
	boolean existsByName(@Param("userName") String userName);

	@Query("FROM User u WHERE u.company = :company")
	List<User> findAllByCompanyId(@Param("company") Company company);

}
