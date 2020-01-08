package com.eitan.couponsproject.cache;


public interface ICacheController {

	public Object get(String key);

	public void put(String key, Object value);

	public void remove(String key);


}
