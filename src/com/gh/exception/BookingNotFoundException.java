package com.gh.exception;

@SuppressWarnings("serial")
public class BookingNotFoundException extends Exception {
	public BookingNotFoundException(String message) {
		super(message);
	}

	public BookingNotFoundException() {
		super("예약을 찾을 수 없습니다.");
	}
}