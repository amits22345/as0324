package com.demo.amit.apps.exception;

import 
lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateToolException extends Exception {	
	public DuplicateToolException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}
}
