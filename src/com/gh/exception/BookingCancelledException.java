package com.gh.exception;

@SuppressWarnings("serial")
public class BookingCancelledException extends Exception {
	public BookingCancelledException(String message) {
		super(message);
	}
}
