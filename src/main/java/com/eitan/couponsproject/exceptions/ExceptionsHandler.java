package com.eitan.couponsproject.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eitan.couponsproject.entities.ErrorBean;
import com.eitan.couponsproject.enums.ErrorType;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler
	@ResponseBody
	public ErrorBean toResponse(Throwable throwable, HttpServletResponse response) {

		if(throwable instanceof ApplicationException) {
			ApplicationException appException = (ApplicationException) throwable;

			ErrorType errorType = appException.getErrorType(); 
			int errorNumber = errorType.getErrorNumber();
			String errorMessage = errorType.getErrorMessage();
			String errorName = errorType.getErrorName();

			ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, errorName); 
			if(appException.getErrorType().isShowStackTrace()) {
				appException.printStackTrace();
			}
			
			response.setStatus(errorNumber);
			return errorBean;
		}

		String errorMessage = throwable.getMessage();
		ErrorBean errorBean = new ErrorBean(601, errorMessage, "General error");
		throwable.printStackTrace();
		
		
		response.setStatus(errorBean.getErrorNumber());
		return errorBean;
	}

}



