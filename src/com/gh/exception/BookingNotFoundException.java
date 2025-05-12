package com.gh.exception;

/**
 * {@code BookingNotFoundException}은 예약 ID 또는 조건에 해당하는 예약 정보를 찾을 수 없을 때 발생하는 사용자
 * 정의 예외입니다.
 *
 * <p>
 * 예를 들어, 존재하지 않는 예약을 조회하거나 삭제/수정하려 할 때 이 예외가 발생합니다.
 * </p>
 * 
 * <p>
 * 메시지를 지정하여 예외 상황에 대한 추가적인 설명을 제공할 수 있습니다.
 * </p>
 * 
 * @author 소유나, 우승환, 양준용
 */

@SuppressWarnings("serial")
public class BookingNotFoundException extends Exception {
	/**
	 * 기본 메시지를 사용하는 생성자입니다.
	 * <p>
	 *	 예: "예약을 찾을 수 없습니다."
	 * </p>
	 */
	public BookingNotFoundException(String message) {
		super(message);
	}

	/**
	 * 사용자 정의 메시지를 사용하는 생성자입니다.
	 *
	 * @param message 예외 상황에 대한 상세 설명
	 */
	public BookingNotFoundException() {
		super("예약을 찾을 수 없습니다.");
	}
}