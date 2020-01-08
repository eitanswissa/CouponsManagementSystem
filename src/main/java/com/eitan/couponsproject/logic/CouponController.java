package com.eitan.couponsproject.logic;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.entities.Coupon;
import com.eitan.couponsproject.dao.ICouponDao;
import com.eitan.couponsproject.enums.ErrorType;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.utils.DateUtils;


@Controller
public class CouponController {

	@Autowired
	private ICouponDao couponDao;

	public void createCoupon(Coupon coupon) throws ApplicationException {

		if (isCouponExistByName(coupon.getTitle())) {
			throw new ApplicationException(ErrorType.COUPON_TITLE_EXIST, DateUtils.getCurrentDateAndTime() 
					+ "Error in couponController.createCoupon(), coupon's name already exists.");
		}

		validateCoupon(coupon);

		couponDao.save(coupon);
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {

		Coupon originCoupon = getCouponById(coupon.getId());
		String couponOriginName = originCoupon.getTitle();

		if (!coupon.getTitle().equalsIgnoreCase(couponOriginName)) {
			if (isCouponExistByName(coupon.getTitle())) {
				throw new ApplicationException(ErrorType.COUPON_TITLE_EXIST, DateUtils.getCurrentDateAndTime() 
						+ "Error in couponController.updateCoupon(), coupon's name already exists.");
			}
		}

		validateCoupon(coupon);

		couponDao.save(coupon);
	}

	public Coupon getCouponById(long couponId) throws ApplicationException {
		Coupon coupon = couponDao.findById(couponId).get();	
		return coupon;
	}

	public List<Coupon> getCouponsByType(String couponType) throws ApplicationException {

		List<Coupon> coupons = (List<Coupon>) couponDao.findByType(couponType);
		return coupons;
	}

	public void deleteCoupon(long couponId) throws ApplicationException {

		Coupon coupon = getCouponById(couponId);
		couponDao.delete(coupon);
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		List<Coupon> coupons = (List<Coupon>) couponDao.findAll();
		return coupons;
	}

	public boolean isCouponExistByName(String couponName) throws ApplicationException {
		return couponDao.existsByName(couponName);
	}

	public void deleteAllExpiredCoupons(Date date) throws ApplicationException {
		couponDao.deleteAllExpiredCoupons(date);
	}

	public void reduceCouponAmountByPurchaseAmont(Coupon coupon, int amount) throws ApplicationException {
		coupon.setAmount(amount);
		couponDao.save(coupon);
	}

	public List<Coupon> getCouponsUpToPrice(int couponPrice) throws ApplicationException {
		return couponDao.findByPriceLessThan(couponPrice);
	}

	public List<Coupon> getCouponByStartDate(Date startDate) throws ApplicationException {
		return couponDao.findByStartDateGreaterThan(startDate);
	}

	public Coupon getCouponByName(String couponName) {
		return couponDao.findByName(couponName);
	}

	public List<Coupon> getCouponByEndDate(Date endDate) throws ApplicationException {
		return couponDao.findByEndDateLessThan(endDate);
	}

	public List<Coupon> getCouponsByCompanyId(Company company) throws ApplicationException {
		return couponDao.findByCompanyId(company);
	}

	private void validateCoupon(Coupon coupon) throws ApplicationException {

		if (coupon == null) {
			System.out.println("Null");
			throw new ApplicationException(ErrorType.MUST_INSERT_A_VALUE, DateUtils.getCurrentDateAndTime() 
					+ "Error in couponController.validateCoupon(), You must insert a Coupon");		}

		if (coupon.getAmount() <= 0) {
			throw new ApplicationException(ErrorType.INVALID_AMOUNT, DateUtils.getCurrentDateAndTime() 
					+ "Error in couponController.createCoupon(), Minimum quantity must be 1.");
		}

		if (coupon.getPrice() < 0) {
			throw new ApplicationException(ErrorType.INVALID_PRICE, DateUtils.getCurrentDateAndTime() 
					+ "Error in couponController.createCoupon(), Coupon's price can't be negetive.");
		}
	}



}
