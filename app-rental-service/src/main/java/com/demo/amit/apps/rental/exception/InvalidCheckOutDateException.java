package com.demo.amit.apps.rental.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCheckOutDateException  extends Exception{public InvalidCheckOutDateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidCheckOutDateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCheckOutDateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidCheckOutDateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
