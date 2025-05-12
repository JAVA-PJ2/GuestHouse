package com.gh.service;

import java.time.LocalDate;
import java.util.List;

import com.gh.model.Booking;

/**
 * GHService 인터페이스는 예약 관련 주요 기능을 정의합니다. 기능에는 예약 가능 여부 확인, 월 매출 계산, 개별 예약 조회가
 * 포함됩니다.
 * 
 * @author 소유나, 우승환, 양준용
 */
public interface GHService {

	/**
	 * 주어진 날짜에 특정 인원 수를 수용할 수 있는지 여부를 확인합니다.
	 * 
	 * @param date           예약을 요청하는 날짜
	 * @param numberOfPeople 요청된 인원 수
	 * @return 수용 가능하면 {@code true}, 수용 불가능하면 {@code false}
	 */
	boolean canAccomodate(LocalDate date, int numberOfPeople);

	/**
	 * 예약 목록을 기반으로 해당 월의 매출 정보를 반환합니다.
	 * 
	 * @param bookingList 전체 예약 목록
	 * @return 월 매출에 해당하는 예약 목록
	 */
	List<Booking> monthlyRevenue(List<Booking> bookingList);

	/**
	 * 예약 ID를 기반으로 해당 예약 정보를 조회합니다.
	 * 
	 * @param bookingId 조회할 예약의 고유 ID
	 * @return 예약 ID에 해당하는 {@link Booking} 객체, 존재하지 않으면 {@code null}
	 */
	Booking findBooking(int bookingId);
}
