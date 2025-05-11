package com.gh.service;

import java.time.LocalDate;
import java.util.List;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
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
	void addBooking(Customer c, Booking b);
	
	 /**
     * 예약 ID를 기반으로 기존 예약을 삭제합니다.
     *
     * @param bookingId 삭제할 예약의 고유 식별자
     */
	void deleteBooking(Customer c, String bookingId);
	
	/**
     * 기존 예약 정보를 수정합니다.
     *
     * @param b 수정할 예약 객체. bookingId를 포함해야 하며, 해당 ID의 예약이 존재해야 합니다.
     */
	void updateBooking(Customer c, Booking b);
	
	/**
	 * 예약 조회
	 * 
	 * @param booginId
	 */
	Booking findBooking(int bookingId);

	/**
	 * 해당 고객의 예약 정보 조회합니다.
	 * 
	 * @param cs
	 */
	List<Booking> findBooking(Customer cs);

	/**
	 * 숙소의 모든 예약 조회합니다.
	 * 
	 * @param gh
	 */
	List<Booking> findBookingByGHName(Guesthouse gh);

	/**
	 * 수용 가능 인원이 full인지 파악하는 메소드 구현합니다.
	 * 
	 * @param date
	 * @param numberOfPoeple
	 */
	boolean canAccomodate(LocalDate date, int numberOfPoeple);

	/**
     * 고객에게 추천할 숙소 이름 목록을 반환합니다.
     * 
     * <p>총 매출(40%)과 예약 수(60%)의 가중치를 기반으로 점수를 계산하여
     * 전체 게스트하우스 중 상위 5개를 선정하고, 숙소 이름만 반환합니다.</p>
     *
     * @param customer 추천 기준이 되는 고객 정보
     * @return 추천 숙소 이름(String)의 리스트 (최대 5개)
     */
	List<Guesthouse> getRecommendedByGH(Customer customer);
}
