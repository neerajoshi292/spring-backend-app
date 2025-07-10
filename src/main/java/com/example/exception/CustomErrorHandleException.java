package com.example.exception;

public class CustomErrorHandleException extends RuntimeException {

	public CustomErrorHandleException(String msg) {
		super(msg);
	}
}