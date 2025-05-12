package com.gh.exception;

/**
 * {@code BookingCancelledException}은 이미 취소된 예약에 대해 변경 또는 재취소 등의 작업을 시도할 때 발생하는
 * 사용자 정의 예외입니다.
 *
 * <p>
 * 예를 들어, 사용자가 이미 취소된 예약을 다시 취소하거나 수정하려고 할 때 이 예외가 발생합니다.
 * </p>
 * 
 * <p>
 * 예외 메시지를 통해 구체적인 상황 설명을 전달할 수 있습니다.
 * </p>
 * 
 * @author 소유나, 양준용, 우승환
 */
@SuppressWarnings("serial")
public class BookingCancelledException extends Exception {
	/**
	 * 예외 메시지를 지정하여 {@code BookingCancelledException} 객체를 생성합니다.
	 *
	 * @param message 예외 상황에 대한 상세 설명
	 */
	public BookingCancelledException(String message) {
		super(message);
	}
}
