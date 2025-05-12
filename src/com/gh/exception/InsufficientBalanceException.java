package com.gh.exception;

public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException() {
		super("잔액이 부족하여 예약을 진행할 수 없습니다.");
	}

	public InsufficientBalanceException(String message) {
		super(message);
	}

}
