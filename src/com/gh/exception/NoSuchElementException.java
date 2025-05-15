package com.gh.exception;

/**
 * {@code NoSuchElementException}은 고객 등록 요 시 등록된 고객의 이름이 존재하지 않을 경우의 예외입니다.
 *
 * <p>
 * 예를 들어, 등록된 고객이 아닌 고객이 등록을 할 경우 등록되지 않은 고객입니다 메시지를 전달할 수 있습니다.
 * </p>
 *
 * @author 소유나, 양준용, 우승환
 */

@SuppressWarnings("serial")
public class NoSuchElementException extends Exception {
	/**
	 * 기본 메시지를 사용하는 생성자입니다.
	 * <p>
	 * 예: "등록되지 않은 고객입니다. 다시 입력하세요.\n"
	 * </p>
	 */
	public NoSuchElementException() {
		super("등록되지 않은 고객입니다. 다시 입력하세요.\\n");
	}

	/**
	 * 사용자 정의 메시지를 사용하는 생성자입니다.
	 *
	 * @param message 예외 상황에 대한 상세 설명
	 */
	public NoSuchElementException(String message) {
		super(message);
	}
}
