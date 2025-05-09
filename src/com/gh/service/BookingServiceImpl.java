package com.gh.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		if (b == null) {
			System.out.println("예약 정보가 유효하지 않습니다.");
			return;
		}

		Customer customer = b.getCustomer(); // 예약을 한 고객 객체 가져오기
		Guesthouse gh = b.getGuesthouse(); // 예약 대상인 게스트하우스 객체 가져오기
		Account account = customer.getAccount(); // 고객의 계좌 객체 가져오기

		int people = b.getNumberOfPeople(); // 예약 인원 수
		int days = b.getBookingDays(); // 예약 일수
		LocalDate startDate = b.getStartDate(); // 체크인 날짜
		LocalDate endDate = startDate.plusDays(days); // 체크아웃 날짜 계산
		b.setEndDate(endDate); // 예약 객체에 체크아웃 날짜설정

		double pricePerDay = gh.getPricePerDays(); // 1박당 가격
		double totalPrice = pricePerDay * days * people; // 총 결제 금액 계산
		b.setTotalAmount(totalPrice); // 예약 객체에 총 결제 금액 설정

		// 예약 가능 여부 확인(해당 날짜에 인원 수용 가능한지)
		boolean canBook = gh.canBook(startDate, endDate, people);

		if (!canBook) {
			System.out.println("예약 불가: 해당 날짜의 최대 수용 인원을 초과합니다.");
			return;
		}

		// 잔액 확인
		if (account.getBalance() < totalPrice) {
			System.out.println("잔액 부족으로 예약할 수 없습니다. 필요 금액: " + totalPrice + ", 현재 잔액: " + account.getBalance());
			return;
		}

		// 결제 및 예약 처리
		account.setBalance(account.getBalance() - totalPrice);
		gh.setTotalSales(gh.getTotalSales() + totalPrice);
		gh.addPeople(startDate, endDate, people);

		// 예약 ID 설정 및 저장
		String bookingId = UUID.randomUUID().toString();
		b.setBookingId(bookingId);

		bookings.add(b);
		customer.getBookings().add(b);

		System.out.println("예약이 완료되었습니다. 예약 번호: " + bookingId);
		System.out.println("차감 금액: " + totalPrice + ", 남은 잔액: " + account.getBalance());

	}

	public void deleteBooking(String bookingId) {
		Booking target = null;

		// 예약 ID로 예약 찾기
		for (Booking b : bookings) {
			if (b.getBookingId().equals(bookingId)) {
				target = b;
				break;
			}
		}

		// 예약이 없으면 종료
		if (target == null) {
			System.out.println("해당 예약을 찾을 수 없습니다.");
			return;
		}

		// 이미 취소된 예약이면 메시지 출력 후 종료
		if (target.getIsCancled()) {
			System.out.println("이미 취소된 예약입니다.");
			return;
		}

		// 취소 가능 날짜인지 확인 (체크인 3일 전까지 가능)
		LocalDate today = LocalDate.now();
		LocalDate checkIn = target.getStartDate();

		if (!today.isBefore(checkIn.minusDays(2))) {
			System.out.println("체크인 3일 전까지만 예약 취소가 가능합니다.");
			return;
		}

		// 고객, 게스트하우스, 계좌 정보 불러오기
		Customer customer = target.getCustomer();
		Guesthouse gh = target.getGuesthouse();
		Account account = customer.getAccount();

		// 예약 정보로 환불 금액 계산
		int people = target.getNumberOfPeople();
		int days = target.getBookingDays();
		double refundAmount = target.getTotalAmount() * 0.5; // 50% 환불

		// 환불 처리
		account.setBalance(account.getBalance() + refundAmount); // 계좌에 환불 금액 입금
		gh.setTotalSales(gh.getTotalSales() - refundAmount); // 게스트하우스 매출 차감
		gh.removePeople(target.getStartDate(), target.getEndDate(), people); // 예약 인원 감소

		target.setIsCancled(true); // 예약 상태를 취소됨으로 표시

		// 성공 메시지 출력
		System.out.println("예약이 성공적으로 취소되었습니다. 환불 금액: " + refundAmount);
		System.out.println("현재 잔액: " + account.getBalance() + ", 게스트하우스 총 매출: " + gh.getTotalSales());
	}

	/**
	 * 예약 변경
	 */
	@Override
	public void updateBooking(Customer c, Booking b) {

		// 1. 고객이 이 예약을 실제로 가지고 있는지 확인
		// 수정하려는 booking의 정보가 고객이 가지고 있는 예약이 맞다면 진행
		boolean bookingExists = false;

		for (Booking booking : c.getBookings()) {
			if (booking.getBookingId().equals(b.getBookingId())) {
				bookingExists = true;
				break;
			}
		}

		if (!bookingExists) {
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
	        
		    // 고객 예약 목록에도 반영!
		    c.getBookings().remove(original);
		    c.getBookings().add(b);

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

	@Override
	/**
	 * 예약 조회
	 * 
	 * <p>
	 * bookingId로 예약 조회
	 * </p>
	 */
	public Booking findBooking(int bookingId) {
		Booking find = null;
		for (Booking b : bookings) {
			if (b.getBookingId().hashCode() == bookingId) {
				find = b;
			} else {
				System.out.println(bookingId + " 예약 정보를 찾을 수 없습니다.");
			}
		}
		return find;
	}

	@Override
	/**
	 * 수용 가능 인원이 full인지 파악해서 참/거짓 출력
	 * 
	 * <p>
	 * canBook 활용해서 참/거짓 반환
	 * </p>
	 */
	public boolean canAccomodate(LocalDate date, int numPeople) {
		List<Guesthouse> guesthouses = new ArrayList<>();
		for (Guesthouse gh : guesthouses) {
			int current = gh.getDailyPeople().getOrDefault(date, 0);
			if (current + numPeople > gh.getMaxPeople()) {
				return false;
			}
		}
		return true;
	}

	@Override
	/**
	 * 해당 고객의 모든 예약 조회
	 * 
	 * <p>
	 * 고객명은 제외하고 출력
	 * </p>
	 */
	public List<Booking> findBooking(Customer cs) {
		return cs.getBookings();
	}

	@Override
	public List<Booking> findBookingByGHName(Guesthouse gh) {
		// TODO Auto-generated method stub
		return null;
	}
}
