package com.eitan.couponsproject.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eitan.couponsproject.consts.Constants;
import com.eitan.couponsproject.data.LoggedInUserData;
import com.eitan.couponsproject.entities.Customer;
import com.eitan.couponsproject.entities.User;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.logic.CustomerController;
import com.eitan.couponsproject.logic.UserController;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

	@Autowired
	private CustomerController customerController;

	@Autowired
	private UserController userController;

	@PostMapping
	@RequestMapping("/register")
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException{

		long userId = userController.createUser(customer.getUser());
		User user = userController.getUserById(userId);

		customer.setUser(user);
		customerController.createCustomer(customer);
	}

	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException{
		customerController.updateCustomer(customer);
	}

	@GetMapping
	@RequestMapping("/getCustomer/{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") long customerId) throws ApplicationException{
		return customerController.getCustomerById(customerId);
	}

	@GetMapping
	@RequestMapping("/getDetails")
	public Customer getCustomerDetails(HttpServletRequest request) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);

		return customerController.getCustomerById(userData.getUserId());
	}

	@DeleteMapping
	@RequestMapping("/{customerId}")
	public void deleteCustomer (@PathVariable("customerId") long customerId) throws ApplicationException{
		customerController.deleteCustomer(customerId);
	}

	@GetMapping
	public List<Customer> getAllCustomers() throws ApplicationException{
		return customerController.getAllCustomers();
	}
}
