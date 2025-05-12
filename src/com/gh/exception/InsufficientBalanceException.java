package com.gh.exception;

/**
 * {@code InsufficientBalanceException}은 예약 요청 시 고객의 계좌 잔액이 부족할 경우 발생하는 사용자 정의
 * 예외입니다.
 *
 * <p>
 * 예를 들어, 예약 금액이 고객의 현재 잔액보다 많을 때 이 예외가 발생하며, 예외 메시지를 통해 부족한 금액이나 현재 잔액 등의 정보를
 * 전달할 수 있습니다.
 * </p>
 *
 * @author 소유나, 양준용, 우승환
 */
public class InsufficientBalanceException extends Exception {
	/**
	 * 기본 메시지를 사용하는 생성자입니다.
	 * <p>
	 * 예: "잔액이 부족하여 예약을 진행할 수 없습니다."
	 * </p>
	 */
	public InsufficientBalanceException() {
		super("잔액이 부족하여 예약을 진행할 수 없습니다.");
	}

	/**
	 * 사용자 정의 메시지를 사용하는 생성자입니다.
	 *
	 * @param message 예외 상황에 대한 상세 설명
	 */
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
