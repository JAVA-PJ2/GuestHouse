package com.gh.service;

import java.util.ArrayList;
import java.util.List;

import com.gh.model.Booking;

public class BookingServiceImpl implements BookingService {
	
	private List<Booking> bookings = new ArrayList<>();

	@Override
	public void addBooking(Booking b) {
		bookings.add(b);
		System.out.println("예약이 성공적으로 추가되었습니다" + b.getBookingId());
		

	}

	@Override
	public void deleteBooking(String bookingId) {
		List<Booking> toRemove = new ArrayList<>();
		for(Booking b : bookings) {
			if(b.getBookingId().equals(bookingId)) {
				toRemove.add(b);
			}
		}
		
		if(!toRemove.isEmpty()) {
			bookings.removeAll(toRemove);
			System.out.println("예약이 삭제 되었습니다"+ bookingId);
		} else {
			System.out.println("해당 예약 ID를 찾을 수 없습니다"+ bookingId);
		}
		

	}

	@Override
	public void updateBooking(Booking b) {
		boolean updated = false;
		
		for(int i = 0; i < bookings.size(); i++) {
			Booking current = bookings.get(i);
			if(current.getBookingId().equals(b.getBookingId()){
				bookings.set(i,  b);
				System.out.println("예약이 수정되었습니다"+b.getBookingId());
				updated = true;
				break;
			}
		}
		if(!updated) {
			System.out.println("해당 예약 ID를 찾을 수 없습니다"+ b.getBookingId());
		}
		
	}

}
