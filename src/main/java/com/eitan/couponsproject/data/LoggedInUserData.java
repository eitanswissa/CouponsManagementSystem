package com.eitan.couponsproject.data;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.enums.UserType;

public class LoggedInUserData {

	private int token;
	private UserType userType;
	private Company company;
	private long userId;
	
	public LoggedInUserData(UserType userType, Company company, long userId) {
		this(userType, userId);
		this.company = company;
	}

	public LoggedInUserData(UserType userType, Long userId) {
		this.userType = userType;
		this.company = null;
		this.userId = userId;
	}
	
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompanyId(Company company) {
		this.company = company;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
