package com.demo.amit.apps.rental.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.amit.apps.rental.exception.DiscountNotInRangeException;
import com.demo.amit.apps.rental.exception.DuplicateToolRentRequestException;
import com.demo.amit.apps.rental.exception.InvalidCheckOutDateException;
import com.demo.amit.apps.rental.exception.InvalidRentalDayException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DuplicateToolRentRequestException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   DuplicateToolRentRequestException ex) {
	       ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }
	@ExceptionHandler(DiscountNotInRangeException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   DiscountNotInRangeException ex) {
	       ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }
	@ExceptionHandler(InvalidCheckOutDateException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   InvalidCheckOutDateException ex) {
	       ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }
	@ExceptionHandler(InvalidRentalDayException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   InvalidRentalDayException ex) {
	       ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
}
