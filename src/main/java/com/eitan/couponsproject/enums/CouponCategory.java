package com.eitan.couponsproject.enums;

public enum CouponCategory {

	CLOTHING_AND_SHOES("Clothing and shoes"),
	FOOD_AND_DRINKS("Food and drinks"),
	ELECTRONICS("Electronics"),
	OFFICS_SUPPLIES("Office supplies");

	private final String couponCatagory;

	private CouponCategory(String couponCatagory) {
		this.couponCatagory = couponCatagory;
	}

	public String getCouponCatagory() {
		return couponCatagory;
	}



}
