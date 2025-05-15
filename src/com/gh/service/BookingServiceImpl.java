package com.gh.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;

import com.gh.exception.BookingCancelledException;
import com.gh.exception.BookingNotFoundException;
import com.gh.exception.InsufficientBalanceException;
import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Account;
import com.gh.user.Customer;

/**
 * {@code BookingServiceImpl} 클래스는 {@link BookingService} 인터페이스의 구현체로, 고객의 예약
 * 생성, 수정, 삭제, 조회, 추천 및 예약 대기열 처리 등의 기능을 제공합니다.
 * 
 * <p>
 * 싱글톤 패턴을 적용하여 하나의 인스턴스만 사용되도록 구현되어 있습니다.
 * </p>
 * 
 * @author 소유나, 양준용, 우승환
 */

public class BookingServiceImpl implements BookingService {
	private static final BookingServiceImpl service = new BookingServiceImpl();
	private static final GuesthouseManager guesthouseManager = new GuesthouseManager();

	private final List<Booking> bookings = new ArrayList<>();
	private final List<Guesthouse> guestHouses = new ArrayList<>();

	public List<Booking> getBookings() {
		return bookings;
	}

	private BookingServiceImpl() {
	}

	public static BookingServiceImpl getInstance() {
		return service;
	}

	@Override
	/**
	 * 고객의 예약을 생성하고, 결제 및 게스트하우스 인원 업데이트를 수행합니다.
	 * <p>
	 * 잔액 부족 시 {@link InsufficientBalanceException}이 발생하며, 인원이 가득 찬 경우 예약 대기열에
	 * 등록됩니다.
	 * </p>
	 *
	 * @param c 고객 객체
	 * @param b 예약 정보 객체
	 * @throws InsufficientBalanceException 고객 잔액이 부족할 경우
	 */

	public void addBooking(Customer c, Booking b) throws InsufficientBalanceException {
		if (b == null) {
			System.out.println("예약 정보가 유효하지 않습니다.");
			return;
		}

		Guesthouse gh = b.getGuesthouse(); // 예약 대상인 게스트하우스 객체 가져오기
		Account account = c.getAccount(); // 고객의 계좌 객체 가져오기

		int people = b.getNumberOfPeople(); // 예약 인원 수
		int days = b.getBookingDays(); // 예약 일수
		LocalDate startDate = b.getStartDate(); // 체크인 날짜
		LocalDate endDate = startDate.plusDays(days); // 체크아웃 날짜 계산
		b.setEndDate(endDate); // 예약 객체에 체크아웃 날짜설정

		// 게스트하우스의 최대 수용인원 < 예약 인원일 경우
		if (gh.getMaxPeople() < b.getNumberOfPeople()) {
			System.out.println("예약 인원이 게스트하우스 최대 수용 인원보다 많아 예약할 수 없습니다.");
			return;
		}

		double pricePerDay = gh.getPricePerDays(); // 1박당 가격
		double totalPrice = pricePerDay * days * people; // 총 결제 금액 계산
		b.setTotalAmount(totalPrice); // 예약 객체에 총 결제 금액 설정

		// 예약 가능 여부 확인(해당 날짜에 인원 수용 가능한지)
		boolean canBook = gh.canBook(startDate, endDate, people);

		if (!canBook) {
			System.out.println("예약 불가: 해당 날짜의 최대 수용 인원을 초과합니다.");
			enqueueWaitingRequest(c, b, LocalDateTime.now()); // 예약 대기열 추가
			return;
		}

		// 잔액 확인
		if (account.getBalance() < totalPrice) {
			throw new InsufficientBalanceException(
					"잔액 부족으로 예약할 수 없습니다. 필요 금액: " + totalPrice + ", 현재 잔액: " + account.getBalance());

		}

		// 결제 및 예약 처리
		account.setBalance(account.getBalance() - totalPrice);
		gh.setTotalSales(gh.getTotalSales() + totalPrice);
		gh.addPeople(startDate, endDate, people);

		// 예약 ID 설정 및 저장
		String bookingId = UUID.randomUUID().toString();
		b.setBookingId(bookingId);

		bookings.add(b);
		c.getBookings().add(b);

		System.out.println("예약이 완료되었습니다. 예약 번호: " + bookingId);
		System.out.println("차감 금액: " + totalPrice + ", 남은 잔액: " + account.getBalance());
		BookingFileManager.saveBookings(bookings, c);
	}

	/**
	 * 예약 ID를 기반으로 해당 고객의 예약을 취소합니다.
	 * <p>
	 * 취소 시 일부 환불이 적용되며, 이후 예약 대기열 자동 처리가 수행됩니다.
	 * </p>
	 *
	 * @param c         고객 객체
	 * @param bookingId 취소할 예약 ID
	 * @throws BookingCancelledException    이미 취소된 예약이거나 취소 기한이 지난 경우
	 * @throws InsufficientBalanceException 예약 변경 중 잔액이 부족한 경우
	 * @throws BookingNotFoundException     해당 ID에 대한 예약이 존재하지 않는 경우
	 */
	public void deleteBooking(Customer c, String bookingId)
			throws BookingCancelledException, InsufficientBalanceException, BookingNotFoundException {
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
			throw new BookingNotFoundException("해당 예약을 찾을 수 없습니다.");
		}

		// 이미 취소된 예약이면 메시지 출력 후 종료
		if (target.getIsCancled()) {
			throw new BookingCancelledException("취소된 예약은 변경이나 재취소가 불가능합니다.");
		}

		// 취소 가능 날짜인지 확인 (체크인 3일 전까지 가능)
		LocalDate today = LocalDate.now();
		LocalDate checkIn = target.getStartDate();

		if (!today.isBefore(checkIn.minusDays(2))) {
			throw new BookingCancelledException("체크인 3일 전 이후에는 취소가 불가능합니다.");
		}

		// 고객, 게스트하우스, 계좌 정보 불러오기
		Guesthouse gh = target.getGuesthouse();
		Account account = c.getAccount();

		// 예약 정보로 환불 금액 계산
		int people = target.getNumberOfPeople();
		int days = target.getBookingDays();
		double refundAmount = people * days * target.getGuesthouse().getPricePerDays() * 0.5; // 50% 환불

		// 환불 처리
		account.setBalance(account.getBalance() + refundAmount); // 계좌에 환불 금액 입금
		gh.setTotalSales(gh.getTotalSales() - refundAmount); // 게스트하우스 매출 차감
		gh.removePeople(target.getStartDate(), target.getEndDate(), people); // 예약 인원 감소

		target.setIsCancled(true); // 예약 상태를 취소됨으로 표시

		// 성공 메시지 출력
		System.out.println("예약이 성공적으로 취소되었습니다. 환불 금액: " + refundAmount);
		System.out.println("현재 잔액: " + account.getBalance() + ", 게스트하우스 총 매출: " + gh.getTotalSales());

		processWaitingList(); // 예약이 취소되었으니 대기열 자동 예약 시도
		BookingFileManager.saveBookings(bookings, c);
	}

	/**
	 * 고객의 기존 예약을 새로운 정보로 변경합니다.
	 * <p>
	 * 기존 예약을 환불한 후 새로운 예약을 진행하며, 잔액 부족 시 예외를 발생시킵니다.
	 * </p>
	 *
	 * @param c 고객 객체
	 * @param b 수정된 예약 정보 객체 (기존 예약과 동일한 bookingId 필수)
	 * @throws InsufficientBalanceException 변경 예약 금액이 부족할 경우
	 * @throws BookingCancelledException    취소된 예약을 수정하려는 경우
	 */
	@Override
	public void updateBooking(Customer c, Booking b) throws InsufficientBalanceException, BookingCancelledException {

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
			throw new BookingCancelledException("고객의 예약 정보가 없습니다.");
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
			throw new BookingCancelledException("해당 예약을 찾을 수 없습니다: " + b.getBookingId());
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

				// 기존 예약 복구
				account.setBalance(account.getBalance() - originalPrice);
				gh.setTotalSales(gh.getTotalSales() + originalPrice);
				gh.addPeople(original.getStartDate(), original.getEndDate(), original.getNumberOfPeople());
				throw new InsufficientBalanceException(
						"잔액 부족으로 예약 변경이 불가합니다. 필요 금액: " + totalPrice + " / 현재 잔액: " + account.getBalance());
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

		BookingFileManager.saveBookings(bookings, c);
	}

	@Override
	/**
	 * 예약 ID의 해시코드를 기반으로 예약을 조회합니다.
	 *
	 * @param bookingId 해시코드 형태의 예약 ID
	 * @return 예약 객체
	 * @throws BookingCancelledException 예약을 찾을 수 없는 경우
	 */
	public Booking findBooking(int bookingId) throws BookingCancelledException {
		for (Booking b : bookings) {
			if (b.getBookingId().hashCode() == bookingId) {
				return b;
			}
		}
		throw new BookingCancelledException(bookingId + " 예약 정보를 찾을 수 없습니다.");

	}

	@Override
	/**
	 * 특정 게스트하우스의 예약 목록을 반환합니다.
	 *
	 * @param gh 게스트하우스 객체
	 * @return 해당 게스트하우스에 대한 예약 리스트
	 */
	public List<Booking> findBookingByGHName(Guesthouse gh) {
		List<Booking> find = new ArrayList<>();

		for (Booking b : bookings) {
			if (b.getGuesthouse() != null && b.getGuesthouse().getBookingId().equals(gh.getBookingId())) {
				find.add(b);
			}
		}

		if (find.isEmpty()) {
			System.out.println(gh.getName() + " 해당 숙소의 예약 정보가 없습니다. ");
		} else {
			for (Booking b : find) {
				b.toString();
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
	/**
	 * 가중치 기반 추천 숙소 리스트 반환
	 */
	public List<Guesthouse> getRecommendedByGH(List<Guesthouse> gh, Customer customer) {
		// GuesthouseManager의 메소드를 사용하여 추천 숙소 리스트를 가져옴
		return guesthouseManager.getRecommendedByGH(gh, customer);
	}

	/////////// 우선순위큐 예약 대기열 구현 ///////////////

	private static class WaitingRequest {
		Customer customer;
		Booking booking;
		LocalDateTime requestDateTime;

		public WaitingRequest(Customer customer, Booking booking, LocalDateTime requestDateTime) {
			this.customer = customer;
			this.booking = booking;
			this.requestDateTime = requestDateTime;
		}

		public LocalDateTime getRequestDate() {
			return requestDateTime;
		}

		@Override
		// 디버깅용, 추후 대기열 리스트 출력 가능 (리팩토링 전 코드)
//		public String toString() {
//			return "예약 대기열 : [고객=" + customer.getName() + ", 요청일=" + requestDateTime + ", 인원="
//					+ booking.getNumberOfPeople() + "]";
//		}

		// Optional로 null-safe 리팩토링 진행 후 코드
		public String toString() {
			String customerName = Optional.ofNullable(customer).map(Customer::getName).orElse("'고객정보 없음'");

			String numberOfPeople = Optional.ofNullable(booking).map(Booking::getNumberOfPeople) // int
					.map(String::valueOf) // String
					.orElse("'인원 정보 없음'");

			String rqTime = Optional.ofNullable(requestDateTime).map(String::valueOf).orElse("'시간 정보 없음'");

			return "예약 대기열 : [고객=" + customerName + ", 요청일=" + rqTime + ", 인원=" + numberOfPeople + "]";
		}
	}

	// 예약 날짜, 시간을 기준으로 정렬
	private final PriorityQueue<WaitingRequest> waitingList = new PriorityQueue<>(
			Comparator.comparing(WaitingRequest::getRequestDate));

	/**
	 * 예약이 불가능한 경우, 예약 요청을 대기열에 우선순위 기반으로 등록합니다.
	 *
	 * @param c               고객 객체
	 * @param b               예약 정보 객체
	 * @param requestDateTime 요청한 날짜 및 시간 (우선순위 기준)
	 */
	@Override
	public void enqueueWaitingRequest(Customer c, Booking b, LocalDateTime requestDateTime) {
		// 이 메소드는 addBooking에서 수용인원이 꽉 차 예약이 불가능할 경우 호출해서 사용한다.
		// 호출할 경우 예약한 고객정보 c, 예약 정보 b, 예약한 현재 시간 requestDate를 인자로 받는다.
		// 우선순위 큐를 구현하여 requestDate를 기준으로 c와 b의 정보를 저장한다.

		WaitingRequest request = new WaitingRequest(c, b, requestDateTime);
		waitingList.offer(request);

		System.out.println("예약 대기열에 등록되었습니다: " + c.getName() + ", 우선순위: ");
	}

	/**
	 * 예약 대기열에서 조건을 만족하는 예약 요청을 찾아 자동으로 예약을 수행합니다.
	 * <p>
	 * 한 번에 단 하나의 요청만 처리되며, 나머지는 다시 대기열로 복귀됩니다.
	 * </p>
	 *
	 * @throws InsufficientBalanceException 예약에 필요한 잔액이 부족할 경우
	 */
	@Override
	// 리팩토링 전 코드
//	public void processWaitingList() throws InsufficientBalanceException {
//		// 이 메소드는 deleteBooking에서 취소가 발생했을 경우 호출해서 실행되는 메소드이다.
//		// 취소해서 최대 수용 인원 - 현재 수용 인원으로 빈자리가 발생했을 경우, 우선순위큐의 대기열에 우선순위가 높은 예약부터 빈자리에 자동
//		// 예약이 된다.
//		//
//		// 만약, 빈자리가 1개인데, 우선순위가 1순위가 2명, 2순위가 1명일 경우 2순위가 자동으로 예약된다.
//		// 이 메소드에서 addBooking 메소드를 호출해서 메소드 재사용하고 코드 길이를 줄이는게 좋을 것 같다.
//
//		// 대기열이 비어있으면 처리할 필요 없음
//		if (waitingList.isEmpty()) {
//			return;
//		}
//
//		// 대기열 순서대로 순회하면서 처리 가능한 예약 찾기
//		List<WaitingRequest> skipped = new ArrayList<>();
//		boolean hasBooked = false;
//
//		while (!waitingList.isEmpty()) {
//			WaitingRequest req = waitingList.poll(); // 우선순위 가장 높은 요청 꺼내기
//			Customer customer = req.customer;
//			Booking booking = req.booking;
//			Guesthouse gh = booking.getGuesthouse();
//
//			int people = booking.getNumberOfPeople();
//			LocalDate startDate = booking.getStartDate();
//			LocalDate endDate = booking.getEndDate();
//
//			// 해당 예약이 게스트하우스에 들어갈 수 있는지 확인
//			if (gh.canBook(startDate, endDate, people)) {
//				System.out.println("========================");
//				System.out.println("대기열 자동 예약 처리 중...");
//				addBooking(customer, booking); // 예약 확정
//				hasBooked = true;
//			} else {
//				// 현재는 못 넣지만, 나중에 다시 넣기 위해 보관
//				skipped.add(req);
//			}
//		}
//
//		// 처리하지 못한 요청들을 다시 대기열로 복구
//		waitingList.addAll(skipped); // 어차피 걍 넣어도 우선순위 기준으로 정렬 됨
//
//		if (!hasBooked) {
//			System.out.println("대기열에 예약 가능한 요청이 없습니다.");
//		}
//
//	}

	public void processWaitingList() {
	    if (waitingList.isEmpty()) return;

	    boolean[] hasBooked = {false};

	    waitingList.removeIf(req -> {
	        Booking booking = req.booking;
	        Guesthouse gh = booking.getGuesthouse();

	        boolean available = gh.canBook(booking.getStartDate(), booking.getEndDate(), booking.getNumberOfPeople());

	        if (available) {
	            System.out.println("========================");
	            System.out.println("대기열 자동 예약 처리 중...");
	            try {
	                addBooking(req.customer, booking);
	                hasBooked[0] = true;
	                return true; // 예약 성공 → 큐에서 제거
	            } catch (InsufficientBalanceException e) {
	                e.printStackTrace(); // 실패해도 큐 유지
	            }
	        }
	        return false; // 예약 실패 또는 수용 불가 → 대기열 유지
	    });

	    if (!hasBooked[0]) {
	        System.out.println("대기열에 예약 가능한 요청이 없습니다.");
	    }
	}


}
