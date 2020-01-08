package com.eitan.couponsproject.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Customers")
public class Customer implements Serializable{

	@Id
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID")
	@MapsId
	private User user;

	@Column(name="Name", nullable = false)
	private String name;

	@Column(name="Address", nullable = false)
	private String address;

	@Column(name="Email", unique = true, nullable = false)
	private String email;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Purchase> purchases;

	public Customer() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [user=" + user + ", name=" + name + ", address=" + address + ", email=" + email + "]";
	}


}
