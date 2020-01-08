package com.eitan.couponsproject.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eitan.couponsproject.cache.ICacheController;
import com.eitan.couponsproject.consts.Constants;
import com.eitan.couponsproject.data.LoggedInUserData;

@Component
@Order(2)
public class LoginFilter implements Filter{

	@Autowired
	private ICacheController cacheController;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String method = httpRequest.getMethod();
		if(method.equals("OPTIONS")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		String url = httpRequest.getRequestURL().toString();
		if(url.endsWith("/register")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		if(url.endsWith("/logout")) {

			String token = httpRequest.getHeader("Authorization");
			cacheController.remove(token);

			chain.doFilter(httpRequest, response);
			return;
		}


		if(url.endsWith("/login")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		String token = httpRequest.getHeader("Authorization");

		LoggedInUserData loggedInUserData = (LoggedInUserData) cacheController.get(token);
		if(loggedInUserData != null) {
			request.setAttribute(Constants.USER_DATA_KEY, loggedInUserData);
			chain.doFilter(request, response);
			return;
		}


		//		token is not in cache or token is null
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		int unAuthorizedError = 401;
		httpResponse.setStatus(unAuthorizedError);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

}
