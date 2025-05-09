package com.gh.service;

import java.util.ArrayList;
import java.util.List;

import com.gh.model.Booking;

public class BookingServiceImpl implements BookingService {
	private static final int MAX_SIZE = 30;
	private Booking[] list = new Booking[MAX_SIZE];
	private int size = 0;

	private static final BookingServiceImpl service = new BookingServiceImpl();

	private BookingServiceImpl() {
	};

	public static BookingServiceImpl getInstance() {
		return service;
	}

	private List<Booking> bookings = new ArrayList<>();

	@Override
	/**
	 * 예약
	 */
	public void addBooking(Booking b) {
		bookings.add(b);
		System.out.println("예약이 성공적으로 추가되었습니다" + b.getBookingId());
	}

	@Override
	/**
	 * 예약 취소
	 */
	public void deleteBooking(String bookingId) {
		List<Booking> toRemove = new ArrayList<>();
		for (Booking b : bookings) {
			if (b.getBookingId().equals(bookingId)) {
				toRemove.add(b);
			}
		}
		if (!toRemove.isEmpty()) {
			bookings.removeAll(toRemove);
			System.out.println("예약이 삭제 되었습니다" + bookingId);
		} else {
			System.out.println("해당 예약 ID를 찾을 수 없습니다" + bookingId);
		}
	}

	@Override
	/**
	 * 예약 변경
	 */
	public void updateBooking(Booking b) {
		
	}

	@Override
	/**
	 * 숙소의 모든 예약 조회
	 * 
	 * <p>
	 * 	예약 번호로 고객명, 숙소명, 체크인/체크아웃 날짜, 총 결제 금액 등 반환
	 * 	숙소 타입 별로 추가 정보를 반환
	 * </p>
	 */
	public Booking findBooking(int bookingId) {
		Booking find = null;
		for (Booking b : bookings) {
			if(b.getBookingId().hashCode() == bookingId) {
				find = b;
			} else {
				System.out.println(bookingId + " 예약 정보를 찾을 수 없습니다.");
			}
		}
		return find;
	}
}
