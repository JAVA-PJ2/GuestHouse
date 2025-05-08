package com.gh.service;

import java.time.LocalDate;
import java.util.List;

import com.gh.model.Booking;

/**
 * GHService 인터페이스는 예약 또는 수용 가능 여부를 판단하는 서비스를 정의합니다.
 * 주요 기능으로는 예약 가능 여부 확인, 월 매출 계산, 예약 조회가 포함되어 있습니다.
 * 
 * @author YangJunYong
 */
public interface GHService {
	/**
	 * 1. 인원 수를 수용할 수 있는지 여부를 확인합니다.
	 * 	  수용 가능 인원이 full인지 여부 파악(예약이 가능한지)
	 * 2. 월 매출 파악 - 예약 목록을 기반으로 해당 월의 매출 정보를 반환합니다. 
	 * 3. 예약 조회 - 예약ID 기반으로 해당 예약 정보를 조회합니다.
	 */
    boolean canAccomodate(LocalDate date, int numberOfPeople);
    List<Booking> monthlyRevenue(List<Booking> bookingList);
    Booking findBooking(int bookingId);
}
