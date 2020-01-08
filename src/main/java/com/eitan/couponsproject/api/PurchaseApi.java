package com.eitan.couponsproject.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eitan.couponsproject.consts.Constants;
import com.eitan.couponsproject.data.LoggedInUserData;
import com.eitan.couponsproject.data.PurchaseData;
import com.eitan.couponsproject.entities.Coupon;
import com.eitan.couponsproject.entities.Customer;
import com.eitan.couponsproject.entities.Purchase;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.logic.CustomerController;
import com.eitan.couponsproject.logic.PurchaseController;

@RestController
@RequestMapping("/purchases")
public class PurchaseApi {

	@Autowired
	private PurchaseController purchaseController;

	@Autowired
	private CustomerController customerController;

	@PostMapping
	public void createPurchase(HttpServletRequest request, @RequestBody PurchaseData purchase) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		purchaseController.createPurchase(purchase, userData);
	}

	@GetMapping
	@RequestMapping("/getCustomerPurchases")
	public List<Coupon> getPurchasesByCustomerId(HttpServletRequest request) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		Customer customer = customerController.getCustomerById(userData.getUserId());

		return purchaseController.getPurchasesByCustomerId(customer);
	}

	@GetMapping
	@RequestMapping("/getCouponPurchases")
	public List<Customer> getPurchasesByCouponId(@RequestParam("couponId") long couponId) throws ApplicationException{
		return purchaseController.getPurchasesByCouponId(couponId);
	}

	@DeleteMapping
	@RequestMapping("/{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException{
		purchaseController.deletePurchase(purchaseId);
	}

	@GetMapping
	public List<Purchase> getAllPurchases() throws ApplicationException{
		return purchaseController.getAllPurchases();
	}

}

