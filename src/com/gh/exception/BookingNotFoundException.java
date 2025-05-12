package com.gh.exception;

@SuppressWarnings("serial")
public class BookingNotFoundException extends Exception {
	public BookingNotFoundException(String message) {
		super(message);
	}
}
