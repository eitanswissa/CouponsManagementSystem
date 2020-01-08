package com.eitan.couponsproject.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eitan.couponsproject.entities.Customer;

public interface ICustomerDao extends CrudRepository<Customer, Long> {


}
