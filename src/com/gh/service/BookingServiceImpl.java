package com.gh.service;

import java.util.ArrayList;
import java.util.List;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Account;
import com.gh.user.Customer;

public class BookingServiceImpl implements BookingService {
	private static final BookingServiceImpl service = new BookingServiceImpl();

	private final List<Booking> bookings = new ArrayList<>();

	private BookingServiceImpl() {
	    
	}

	public static BookingServiceImpl getInstance() {
	    return service;
	}


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

	/**
	 * 예약 변경
	 */
	@Override
	public void updateBooking(Customer c, Booking b) {
		
		// 1. 고객이 이 예약을 실제로 가지고 있는지 확인
		// 수정하려는 booking의 정보가 고객이 가지고 있는 예약이 맞다면 진행
		boolean bookingExists = false;
		
		for(Booking booking: c.getBookings()) {
			if(booking.getBookingId().equals(b.getBookingId())) {
				bookingExists = true;
				break;
			}
		}
		
		if(!bookingExists) {
			System.out.println("고객의 예약 정보가 없습니다.");
			return;
		}
		
		// 2. 예약이 취소되었는지 확인
		// 취소된 예약인지 여부 검증
	    if (b.getIsCancled()) {
	        System.out.println("취소된 예약입니다.");
	        return;
	    }

	    // 3. 시스템 전체 예약 목록에서 해당 예약 찾기
	    // 수정하려는 예약의 기존 정보 찾기
	    Booking original = null;
	    for (Booking booking : bookings) {
	        if (booking.getBookingId().equals(b.getBookingId())) {
	            original = booking;
	            break;
	        }
	    }

	    if (original == null) {
	        System.out.println("해당 예약을 찾을 수 없습니다: " + b.getBookingId());
	        return;
	    }

	    // 4. 기존 예약에 대해 환불 처리
	    // 수정하려는 예약의 게스트하우스와 고객의 계좌 정보
	    Guesthouse gh = b.getGuesthouse();
	    Account account = c.getAccount();
	    
	    // 기존 예약 요금 계산
	    int originalDays = original.getBookingDays();
	    int originalPeople = original.getNumberOfPeople();
	    double rate = gh.getPricePerDays();
	    double originalPrice = originalDays * originalPeople * rate;

	    // 기존 예약 환불
	    account.setBalance(account.getBalance() + originalPrice);
	    gh.setTotalSales(gh.getTotalSales() - originalPrice);

	    // 5. 기존 예약 인원을 날짜별로 제거
	    gh.removePeople(original.getStartDate(), original.getEndDate(), original.getNumberOfPeople());

	    // 6. 새로운 예약이 가능한지 확인
	    boolean canBook = gh.canBook(b.getStartDate(), b.getEndDate(), b.getNumberOfPeople());

	    if (canBook) {
	        // 새 예약 요금 계산
	        int days = b.getBookingDays();
	        int people = b.getNumberOfPeople();
	        double totalPrice = days * people * rate;

	        // 7. 예약이 가능할 경우 새 요금 계산 및 잔액 검사
	        if (account.getBalance() < totalPrice) {
	            System.out.println("잔액 부족으로 예약 변경이 불가합니다. 필요 금액: " + totalPrice + " / 현재 잔액: " + account.getBalance());

	            // 기존 예약 복구
	            account.setBalance(account.getBalance() - originalPrice);
	            gh.setTotalSales(gh.getTotalSales() + originalPrice);
	            gh.addPeople(original.getStartDate(), original.getEndDate(), original.getNumberOfPeople());
	            return;
	        }

	        // 8. 예약이 가능하고 잔액도 충분한 경우: 결제 수행
	        account.setBalance(account.getBalance() - totalPrice);
	        gh.setTotalSales(gh.getTotalSales() + totalPrice);

	        // 9. 날짜별 인원 다시 추가
	        gh.addPeople(b.getStartDate(), b.getEndDate(), b.getNumberOfPeople());

	        // 10. 예약 정보 시스템에 반영
	        bookings.remove(original);
	        bookings.add(b);

	        System.out.println("예약이 성공적으로 변경되었습니다: " + b.getStartDate() + " ~ " + b.getEndDate());
	        System.out.println("차감 금액: " + totalPrice + ", 남은 잔액: " + account.getBalance());
	        System.out.println("게스트하우스 총 매출: " + gh.getTotalSales());

	    } else {
	        // 예약 실패 → 기존 예약 복구
	        account.setBalance(account.getBalance() - originalPrice);
	        gh.setTotalSales(gh.getTotalSales() + originalPrice);
	        gh.addPeople(original.getStartDate(), original.getEndDate(), original.getNumberOfPeople());

	        System.out.println("예약 변경 실패: 최대 수용 인원 초과");
	    }
	}

//	@Override
	/**
	 * 숙소의 모든 예약 조회
	 * 
	 * <p>
	 * 	예약 번호로 고객명, 숙소명, 체크인/체크아웃 날짜, 총 결제 금액 등 반환
	 * 	숙소 타입 별로 추가 정보를 반환
	 * </p>
	 */
//	public Booking findBooking(int bookingId) {
//		Booking find = null;
//		for (Booking b : bookings) {
//			if(b.getBookingId().hashCode() == bookingId) {
//				find = b;
//			} else {
//				System.out.println(bookingId + " 예약 정보를 찾을 수 없습니다.");
//			}
//		}
//		return find;
//	}
	
	@Override 
	/**
	 * 해당 고객의 모든 예약 조회
	 * 
	 * <p>고객명은 제외하고 출력</p>
	 */
	public List<Booking> findBooking(Customer cs) {
		return cs.getBookings();
	}
}
