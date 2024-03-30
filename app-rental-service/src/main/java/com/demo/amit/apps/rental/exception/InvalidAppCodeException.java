package com.demo.amit.apps.rental.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidAppCodeException  extends Exception{public InvalidAppCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidAppCodeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidAppCodeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidAppCodeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
