package com.eitan.couponsproject.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eitan.couponsproject.consts.Constants;
import com.eitan.couponsproject.data.LoggedInUserData;
import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.entities.Coupon;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.logic.CompanyController;
import com.eitan.couponsproject.logic.CouponController;

@RestController
@RequestMapping("/coupons")
public class CouponApi {

	@Autowired
	private CouponController couponController;

	@Autowired
	private CompanyController companyController;

	@PostMapping
	public void createCoupon(HttpServletRequest request, @RequestBody Coupon coupon) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);

		Company company = userData.getCompany();
		coupon.setCompany(company);

		couponController.createCoupon(coupon);	
		System.out.println(coupon);
	}

	@PutMapping
	public void updateCoupon(HttpServletRequest request, @RequestBody Coupon coupon) throws ApplicationException{
		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);

		Company company = (userData.getCompany());
		coupon.setCompany(company);

		couponController.updateCoupon(coupon);
	}

	@GetMapping
	@RequestMapping("/getCoupon/{couponId}")
	public Coupon getCouponById(@PathVariable("couponId") long couponId) throws ApplicationException{
		return couponController.getCouponById(couponId);
	}

	@DeleteMapping
	@RequestMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException{
		couponController.deleteCoupon(couponId);
	}

	@GetMapping
	@RequestMapping
	public List<Coupon> getAllCoupons() throws ApplicationException{
		return couponController.getAllCoupons();

	}

	@GetMapping
	@RequestMapping("/byStartDate")
	public List<Coupon> getCouponByStartDate(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate) throws ApplicationException{
		return couponController.getCouponByStartDate(startDate);
	}

	@GetMapping
	@RequestMapping("/byEndDate")
	public List<Coupon> getCouponByEndDate(@RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) throws ApplicationException{
		return couponController.getCouponByEndDate(endDate);
	}

	@GetMapping
	@RequestMapping("/byType")
	public List<Coupon> getCouponsByType(@RequestParam("type") String couponType) throws ApplicationException{
		return couponController.getCouponsByType(couponType);
	}

	@GetMapping
	@RequestMapping("/byName")
	public Coupon getCouponByName(@RequestParam("couponName") String couponName) throws ApplicationException{
		return couponController.getCouponByName(couponName);
	}

	@GetMapping
	@RequestMapping("/upToPrice")
	public List<Coupon> getCouponsUpToPrice(@RequestParam("couponPrice") int couponPrice) throws ApplicationException{
		return couponController.getCouponsUpToPrice(couponPrice);
	}

	@GetMapping
	@RequestMapping("/byCompany")
	public List<Coupon> getCouponsByCompanyId(HttpServletRequest request) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		Company company = userData.getCompany();

		return couponController.getCouponsByCompanyId(company);
	}
}
