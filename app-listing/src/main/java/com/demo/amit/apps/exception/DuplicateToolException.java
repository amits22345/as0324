package com.demo.amit.apps.exception;

import 
lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateToolException extends Exception {	
	public DuplicateToolException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	public DuplicateToolException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DuplicateToolException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicateToolException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
