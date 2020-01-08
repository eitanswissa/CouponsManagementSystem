package com.eitan.couponsproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eitan.couponsproject.entities.Coupon;
import com.eitan.couponsproject.entities.Customer;
import com.eitan.couponsproject.entities.Purchase;

public interface IPurchaseDao extends CrudRepository<Purchase, Long> {

	@Query("FROM Coupon c WHERE c.id IN (SELECT p.coupon FROM Purchase p WHERE p.customer = :customer)")
	List<Coupon> findByCustomerId(@Param("customer") Customer customer);

	@Query("FROM Customer c WHERE c.id = (SELECT p.coupon FROM Purchase p WHERE p.coupon = :coupon)")
	List<Customer> findByCouponId(@Param("coupon") Coupon coupon);

}
