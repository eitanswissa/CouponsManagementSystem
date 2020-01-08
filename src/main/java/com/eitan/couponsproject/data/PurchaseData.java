package com.eitan.couponsproject.data;

public class PurchaseData {
	
	private long couponId;
	private int amount;
	
	public PurchaseData() {
	}
	
	public PurchaseData(long couponId, int amount) {
		this.couponId = couponId;
		this.amount = amount;
	}
	
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	

}
