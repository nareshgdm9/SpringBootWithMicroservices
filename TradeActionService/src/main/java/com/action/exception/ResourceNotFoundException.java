package com.action.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String string) {
		super(string);
	}

}