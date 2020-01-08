package com.eitan.couponsproject.enums;

public enum UserType {

	CUSTOMER("CUSTOMER"),
	COMPANY("COMPANY"),
	ADMIN("ADMIN");

	private final String userType;

	private UserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}


}
