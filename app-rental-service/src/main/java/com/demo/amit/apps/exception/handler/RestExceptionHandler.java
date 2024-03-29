package com.demo.amit.apps.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.amit.apps.exception.DuplicateToolException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DuplicateToolException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   DuplicateToolException ex) {
	       ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
}