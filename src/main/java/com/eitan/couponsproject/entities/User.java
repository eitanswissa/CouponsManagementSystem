package com.eitan.couponsproject.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eitan.couponsproject.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Users")
public class User implements Serializable{

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "UserName", unique = true, nullable = false)
	private String userName;

	@Column(name = "Password", nullable = false)
	private String password;

	@Column(name = "Type", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType type; 

	@ManyToOne
	@JoinColumn(name = "Company_id" ,nullable = true)
	private Company company;

	@OneToOne(cascade = CascadeType.REMOVE, mappedBy = "user")
	@JsonIgnore
	private Customer customer;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", type=" + type + ", company="
				+ company + ", customer=" + customer + "]";
	}



}


