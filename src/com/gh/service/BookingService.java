package com.gh.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.gh.exception.BookingCancelledException;
import com.gh.exception.BookingNotFoundException;
import com.gh.exception.InsufficientBalanceException;
import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Customer;

/**
 * 예약(Booking)에 대한 등록, 수정, 삭제 기능을 정의하는 서비스 인터페이스입니다.
 * 
 * <p>
 * 예약 데이터를 저장소(예: DB)에 연동하거나 관리하는 비즈니스 로직의 진입점 역할을 합니다.
 * </p>
 */
public interface BookingService {
	/**
	 * 새로운 예약 정보를 추가합니다.
	 *
	 * @param b 추가할 예약 객체 (Booking)
	 * @throws InsufficientBalanceException
	 */
	void addBooking(Customer c, Booking b) throws InsufficientBalanceException;

	/**
	 * 예약 ID를 기반으로 기존 예약을 삭제합니다.
	 *
	 * @param bookingId 삭제할 예약의 고유 식별자
	 * @throws BookingCancelledException
	 * @throws InsufficientBalanceException
	 * @throws BookingNotFoundException
	 */
	void deleteBooking(Customer c, String bookingId)
			throws BookingCancelledException, InsufficientBalanceException, BookingNotFoundException;

	/**
	 * 기존 예약 정보를 수정합니다.
	 *
	 * @param b 수정할 예약 객체. bookingId를 포함해야 하며, 해당 ID의 예약이 존재해야 합니다.
	 * @throws InsufficientBalanceException
	 * @throws BookingCancelledException
	 */
	void updateBooking(Customer c, Booking b)
			throws InsufficientBalanceException, BookingCancelledException, BookingCancelledException;

	/**
	 * 예약 조회
	 * 
	 * @param booginId
	 * @throws BookingCancelledException
	 */
	Booking findBooking(int bookingId) throws BookingCancelledException, BookingCancelledException;

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
	 * <p>
	 * 총 매출(40%)과 예약 수(60%)의 가중치를 기반으로 점수를 계산하여 전체 게스트하우스 중 상위 5개를 선정하고, 숙소 이름만
	 * 반환합니다.
	 * </p>
	 *
	 * @param customer 추천 기준이 되는 고객 정보
	 * @return 추천 숙소 이름(String)의 리스트 (최대 5개)
	 */
	List<Guesthouse> getRecommendedByGH(List<Guesthouse> gh, Customer customer);

	/**
	 * 예약이 불가능한 경우, 고객의 예약 요청을 대기열에 우선순위와 함께 추가합니다.
	 *
	 * @param c           예약을 요청한 고객
	 * @param b           예약 정보
	 * @param requestDate 예약 우선순위 (값이 낮을수록 우선)
	 */
	void enqueueWaitingRequest(Customer c, Booking b, LocalDateTime requestDate);

	/**
	 * 예약이 취소되어 자리가 날 경우, 대기열에서 우선순위가 높은 예약 요청을 확인하여 자동으로 예약을 처리합니다. 조건에 맞는 예약이 있을
	 * 경우 단 한 건만 처리됩니다.
	 * 
	 * @throws InsufficientBalanceException
	 */
	void processWaitingList() throws InsufficientBalanceException;
}
