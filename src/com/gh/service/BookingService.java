package com.gh.service;

import java.util.List;

import com.gh.model.Booking;
import com.gh.user.Customer;

/**
 * 예약(Booking)에 대한 등록, 수정, 삭제 기능을 정의하는 서비스 인터페이스입니다.
 * 
 * <p>예약 데이터를 저장소(예: DB)에 연동하거나 관리하는 비즈니스 로직의 진입점 역할을 합니다.</p>
 */
public interface BookingService {
	/**
     * 새로운 예약 정보를 추가합니다.
     *
     * @param b 추가할 예약 객체 (Booking)
     */
	void addBooking(Booking b);
	
	 /**
     * 예약 ID를 기반으로 기존 예약을 삭제합니다.
     *
     * @param bookingId 삭제할 예약의 고유 식별자
     */
	void deleteBooking(String bookingId);
	
	/**
     * 기존 예약 정보를 수정합니다.
     *
     * @param b 수정할 예약 객체. bookingId를 포함해야 하며, 해당 ID의 예약이 존재해야 합니다.
     */
	void updateBooking(Customer c, Booking b);

	/**
	 * 해당 고객의 예약 정보 조회합니다.
	 * 
	 * @param 고객의 이름을 제외한 예약 정보
	 */
	List<Booking> findBooking(Customer cs);
}
