package com.eitan.couponsproject.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.eitan.couponsproject.entities.Customer;
import com.eitan.couponsproject.entities.User;
import com.eitan.couponsproject.dao.ICustomerDao;
import com.eitan.couponsproject.enums.ErrorType;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.utils.DateUtils;
import com.eitan.couponsproject.utils.StringValidation;

@Controller
public class CustomerController {

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private UserController userController;

	public void createCustomer(Customer customer) throws ApplicationException {

		validateCustomer(customer);

		customerDao.save(customer);
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		validateCustomer(customer);

		customerDao.save(customer);
	}

	public Customer getCustomerById(long customerId) throws ApplicationException {

		Customer customer = customerDao.findById(customerId).get();
		return customer;
	}

	public void deleteCustomer(long customerId) throws ApplicationException {

		//		Customer customer = customerDao.findById(customerId).get();
		User user = userController.getUserById(customerId);
		userController.deleteUser(user.getId());
		//		customerDao.delete(customer);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		List<Customer> customers =  (List<Customer>) customerDao.findAll();
		return customers;
	} 

	private void validateCustomer(Customer customer) throws ApplicationException {

		if (customer.getName() == null) {
			throw new ApplicationException(ErrorType.MUST_INSERT_A_VALUE, DateUtils.getCurrentDateAndTime() 
					+ "Error in customerController.validateCustomer(), You must insert details");	
		}

		if (customer.getName().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, DateUtils.getCurrentDateAndTime()
					+ "Error in customerController.validateCustomer() " + customer + "An empty name.");
		}

		if (customer.getAddress() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_ADDRESS, DateUtils.getCurrentDateAndTime()			
					+ "Error in customerController.validateCustomer() " + customer + "Null name.");
		}

		if (customer.getAddress().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_ADDRESS, DateUtils.getCurrentDateAndTime()
					+ "Error in customerController.validateCustomer() " + customer + "The company address is empty.");	
		}

		if(!StringValidation.isEmailAddressValid(customer.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL, DateUtils.getCurrentDateAndTime()
					+ "Error in StringValidation.isEmailAddressValid(), InValid Email.");	
		}
	}

}


