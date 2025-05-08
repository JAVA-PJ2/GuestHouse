package com.gh.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * 게스트하우스 예약 정보를 담는 클래스입니다.
 * 예약 ID, 시작일, 종료일, 숙박일수, 인원 수, 숙소 정보(Has a)를 포함합니다.
 * 
 * <p>bookingId는 UUID 기반으로 고유한 값을 자동 생성합니다.</p>
 */

public class Booking {
	/** 고유 예약 식별자 (UUID 기반 */
	private String bookingId;
	/** 예약 시작일 */
	private LocalDate startDate;
	/** 예약 종료일 */
	private LocalDate endDate;
	/** 숙박일수 */
	private int bookingDays;
	/** 투숙 인원 수 */
	private int numberOfPeople;
	/** 예약된 게스트하우스 정보 */
	private Guesthouse guesthouse;
	
	/**
	 * 기본 생성자입니다.
	 * 기본값으로 오늘 날짜를 시작일로 하고, 1박 1인 기준으로 예약을 생성합니다.
	 */
	public Booking() {
		this(LocalDate.now(), 1, 1, null);
	}
	
	/**
	 * 예약 정보를 초기화하는 생성자입니다.
	 * 
	 * @param startDate
	 * @param bookingDays
	 * @param numberOfPeople
	 * @param guesthouse
	 */
	public Booking(LocalDate startDate, int bookingDays, int numberOfPeople, Guesthouse guesthouse) {
		super();
		this.bookingId = UUID.randomUUID().toString();
		this.startDate = startDate;
		this.endDate = startDate.plusDays(bookingDays);
		this.bookingDays = bookingDays;
		this.numberOfPeople = numberOfPeople;
		this.guesthouse = guesthouse;
	}
	
	/**
	 * 예약 ID를 반환합니다.
	 * 
	 * @return UUID 문자열 형식의 예약 ID
	 */
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * 예약 시작일을 반환합니다.
	 * 
	 * @return 예약 시작일
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * 예약 시작일을 설정합니다.
	 * 예약 시작일과 동시에 endDate도 설정한다.
	 * 
	 * @param startDate
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		if(this.bookingDays > 0) {
			this.endDate = this.startDate.plusDays(this.bookingDays)
		}
	}

	/**
	 * 예약 종료일을 반환합니다.
	 * 
	 * @return 예약 종료일
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * 예약 종료일을 설정합니다.
	 * 
	 * @param endDate
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * 숙박일수를 반환합니다.
	 * 
	 * @return 숙박일수
	 */
	public int getBookingDays() {
		return bookingDays;
	}

	  /**
     * 숙박일 수를 설정합니다.
     *
     * @param bookingDays 숙박일 수
     */
	public void setBookingDays(int bookingDays) {
		this.bookingDays = bookingDays;
		if(this.startDate != null) {
			this.endDate = this.startDate.plusDays(bookingDays)
		}
	}


    /**
     * 투숙 인원 수를 반환합니다.
     *
     * @return 투숙 인원 수
     */
	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	 /**
     * 투숙 인원 수를 설정합니다.
     *
     * @param numberOfPeople 투숙 인원 수
     */
	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	 /**
     * 예약된 게스트하우스를 반환합니다.
     *
     * @return 게스트하우스 객체
     */
	public Guesthouse getGuesthouse() {
		return guesthouse;
	}

	  /**
     * 예약된 게스트하우스를 설정합니다.
     *
     * @param guesthouse 게스트하우스 객체
     */
	public void setGuesthouse(Guesthouse guesthouse) {
		this.guesthouse = guesthouse;
	}

	/**
     * 예약 ID를 수동으로 설정합니다.
     * 주의: 일반적으로는 UUID로 자동 생성되므로 수동 설정은 지양해야 합니다.
     *
     * @param bookingId 예약 ID 문자열
     */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/**
     * 객체의 문자열 표현을 반환합니다.
     *
     * @return 예약 객체의 문자열 표현
     */
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", bookingDays=" + bookingDays + ", numberOfPeople=" + numberOfPeople + ", guesthouse=" + guesthouse
				+ "]";
	}
	
}
