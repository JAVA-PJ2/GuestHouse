package com.gh.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.gh.exception.BookingCancelledException;
import com.gh.exception.BookingNotFoundException;
import com.gh.exception.InsufficientBalanceException;
import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.model.MusicGH;
import com.gh.model.PartyGH;
import com.gh.model.PetGH;
import com.gh.service.BookingFileManager;
import com.gh.service.BookingServiceImpl;
import com.gh.service.GuesthouseManager;
import com.gh.user.Account;
import com.gh.user.Customer;

/**
 * 메인 애플리케이션 클래스입니다.
 * 
 * <p>
 * 콘솔 기반의 게스트하우스 예약 시스템을 실행하며, 고객 인증부터 예약/변경/취소/조회/추천/통계 등 다양한 기능을 제공합니다.
 * </p>
 *
 * @author 소유나, 양준용, 우승환
 */
public class Main {
	/**
	 * 표준 입력을 처리하기 위한 Scanner 인스턴스
	 */
	private static final Scanner sc = new Scanner(System.in);
	/**
	 * 예약 관련 서비스 구현체
	 */
	private static final BookingServiceImpl service = BookingServiceImpl.getInstance();
	/**
	 * 게스트하우스 관련 기능 처리 매니저 클래스
	 */
	private static final GuesthouseManager manager = new GuesthouseManager();

	/**
	 * 프로그램 진입점 (main 메서드)
	 *
	 * <p>
	 * 고객 인증을 통해 사용자 정보를 받아오고, CRUD 기반 메뉴를 반복 출력하며 사용자 요청에 따라 예약 기능을 수행합니다.
	 * </p>
	 *
	 * @param args 커맨드라인 인자 (사용되지 않음)
	 * @throws InsufficientBalanceException 잔액 부족 시 발생
	 * @throws BookingNotFoundException     예약을 찾지 못한 경우 발생
	 * @throws BookingCancelledException    이미 취소된 예약에 접근한 경우 발생
	 */
	public static void main(String[] args)
			throws InsufficientBalanceException, BookingNotFoundException, BookingCancelledException {
		// 게스트하우스 생성
		List<Guesthouse> gh = new ArrayList<>();
		gh.add(new MusicGH("GH001", "뮤직존 스튜디오", "음악", 100.0, 5, 0, "방음 완비, 드럼/기타 보유", true, true, true));
		gh.add(new PetGH("GH002", "펫팰리스 도그하우스", "반려동물", 80.0, 3, 0, "중형견 전용, 응급대응 지원", 30.0, null, true));
		gh.add(new PetGH("GH003", "펫팰리스 캣하우스", "반려동물", 75.0, 2, 0, "고양이 전용, 조용한 실내", 25.0, null, false));
		gh.add(new PartyGH("GH004", LocalDate.now(), LocalDate.now().plusDays(1), 1, 1, null, "파티존 루프탑", "파티", 150.0,
				10, 0, "루프탑 파티 공간, DJ 부스 완비", 19));
		gh.add(new Guesthouse("GH005", "일반 게스트하우스", "파티", 50.0, 4, 0, "저렴하고 깨끗한 일반 숙소"));

		// 고객 생성
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("홍길동", "hong@naver.com", new Account("hong123", 1000000.0), new ArrayList<>()));
		customers.add(new Customer("김철수", "kim@naver.com", new Account("kim123", 800.0), new ArrayList<>()));
		customers.add(new Customer("이영희", "lee@naver.com", new Account("lee123", 1200.0), new ArrayList<>()));

		// 고객 인증
		Customer customer = null;
		while (customer == null) {
			System.out.print("고객 이름을 입력하세요: ");
			String inputName = sc.nextLine();

			for (Customer c : customers) {
				if (c.getName().equals(inputName)) {
					customer = c;
					break;
				}
			}
			if (customer == null)
				System.out.println("등록되지 않은 고객입니다. 다시 입력하세요.\n");
		}

		// 저장된 예약 내역 불러오기
		BookingFileManager.loadBookings(customer, gh);

		// CRUD 메뉴
		while (true) {
			System.out.printf("\n===== %s 님의 게스트하우스 예약 시스템 =====\n", customer.getName());
			System.out.println("고객: " + customer.getName() + " | 잔액: " + customer.getAccount().getBalance());
			System.out.println("1. 예약하기");
			System.out.println("2. 예약 변경하기");
			System.out.println("3. 예약 취소하기");
			System.out.println("4. 이 고객의 모든 예약 조회하기");
			System.out.println("5. 추천 숙소 리스트");
			System.out.println("6. 특정 날짜의 전체 예약률 확인");
			System.out.println("7. 특정 특성의 게스트하우스 보기");
			System.out.println("8. 종료");
			System.out.print("번호를 선택하세요: ");

			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {
			/**
			 * 예약
			 */
			case 1:
				System.out.println("\n[예약 가능한 게스트하우스 목록]");
				for (int i = 0; i < gh.size(); i++) {
					System.out.println((i + 1) + ". " + gh.get(i).getName() + " - " + gh.get(i).getDescription());
				}

				System.out.print("예약할 게스트하우스 번호: ");
				int ghChoice = Integer.parseInt(sc.nextLine()) - 1;
				Guesthouse selectedGH = gh.get(ghChoice);

				LocalDate startDate = null;
				while (startDate == null) {
					System.out.print("시작 날짜 입력 (yyyy-MM-dd): ");
					String input = sc.nextLine().trim();
					try {
						startDate = LocalDate.parse(input);
						if (startDate.isBefore(LocalDate.now())) {
							System.out.println("[오류] 현재 날짜 이후만 예약 가능합니다.");
							startDate = null;
						}
					} catch (Exception e) {
						System.out.println("[오류] 날짜 형식이 올바르지 않습니다. 예: 2025-05-12");
					}
				}

				System.out.print("숙박일수 입력: ");
				int days = Integer.parseInt(sc.nextLine());

				System.out.print("인원 수 입력: ");
				int people = Integer.parseInt(sc.nextLine());

				Booking newBooking = new Booking(startDate, days, people, selectedGH);
				try {
					service.addBooking(customer, newBooking);
				} catch (InsufficientBalanceException e) {
					System.out.println("[예약 실패]" + e.getMessage());
				}
				break;

			/**
			 * 예약 변경
			 */
			case 2:
				System.out.println("[예약 변경] 고객의 예약 목록:");
				List<Booking> userBookings = customer.getBookings();

				if (userBookings.isEmpty()) {
					System.out.println("예약 내역이 없습니다.");
					break;
				}

				//리팩토링 전 코드
//				for (int i = 0; i < userBookings.size(); i++) {
//					Booking b = userBookings.get(i);
//					System.out.printf("%d. [%s] %s ~ %s (%d명)\n", i + 1, b.getGuesthouse().getName(), b.getStartDate(),
//							b.getEndDate(), b.getNumberOfPeople());
//				}

				// forEach로 람다식 리팩토링 후 코드
				AtomicInteger num = new AtomicInteger(1);

				userBookings.forEach(b -> {
					System.out.printf("%d. [%s] %s ~ %s (%d명)\n", num.getAndIncrement(), b.getGuesthouse().getName(),
							b.getStartDate(), b.getEndDate(), b.getNumberOfPeople());
				});

				System.out.print("변경할 예약 번호를 선택하세요: ");
				int selection = Integer.parseInt(sc.nextLine()) - 1;

				if (selection < 0 || selection >= userBookings.size()) {
					System.out.println("잘못된 번호입니다.");
					break;
				}

				Booking selected = userBookings.get(selection);

				LocalDate updateStartDate = null;
				while (updateStartDate == null) {
					System.out.print("새 시작 날짜 입력 (yyyy-MM-dd): ");
					String updateInput = sc.nextLine().trim();
					try {
						updateStartDate = LocalDate.parse(updateInput);
						if (updateStartDate.isBefore(LocalDate.now())) {
							System.out.println("[오류] 현재 날짜 이후만 예약 가능합니다.");
							updateStartDate = null;
						}
					} catch (Exception e) {
						System.out.println("[오류] 날짜 형식이 올바르지 않습니다. 예: 2025-05-12");
					}
				}

				System.out.print("새 숙박일수 입력: ");
				int newDays = Integer.parseInt(sc.nextLine());

				System.out.print("새 인원 수 입력: ");
				int newPeople = Integer.parseInt(sc.nextLine());

				Booking modified = new Booking(updateStartDate, newDays, newPeople, selected.getGuesthouse());
				modified.setBookingId(selected.getBookingId());

				try {
					service.updateBooking(customer, modified);
				} catch (InsufficientBalanceException e) {
					System.out.println("[예약 변경 실패]" + e.getMessage());
				}
				break;

			/**
			 * 예약 취소
			 */
			case 3:
				List<Booking> cancelList = customer.getBookings();

				if (cancelList.isEmpty()) {
					System.out.println("예약 내역이 없습니다.");
					break;
				}

				System.out.println("[예약 취소] 고객의 예약 목록:");
				for (int i = 0; i < cancelList.size(); i++) {
					Booking b = cancelList.get(i);
					System.out.printf("%d. [%s] %s ~ %s (%d명)\n", i + 1, b.getGuesthouse().getName(), b.getStartDate(),
							b.getEndDate(), b.getNumberOfPeople());
				}

				System.out.print("취소할 예약 번호를 선택하세요: ");
				int cancelIndex = Integer.parseInt(sc.nextLine()) - 1;

				if (cancelIndex < 0 || cancelIndex >= cancelList.size()) {
					System.out.println("잘못된 번호입니다.");
					break;
				}

				Booking toCancel = cancelList.get(cancelIndex);

				System.out.print("정말 예약을 취소하시겠습니까? (Y/N): ");
				String confirm = sc.nextLine().trim().toUpperCase();

				if (confirm.equals("Y")) {
					try {
						service.deleteBooking(customer, toCancel.getBookingId());
						cancelList.remove(cancelIndex); // 예외 없을 때만 삭제
					} catch (BookingCancelledException e) {
						System.out.println("[삭제 실패] " + e.getMessage());
					}
				} else {
					System.out.println("예약 취소가 취소되었습니다.");
				}

				break;

			/**
			 * 해당 고객의 모든 예약 조회
			 */
			case 4:
				for (Booking b : service.findBooking(customer)) {
					System.out.println("- " + b.toString());
				}
				break;

			/**
			 * 추천 숙소 리스트
			 */
			case 5:
				List<Guesthouse> recommended = service.getRecommendedByGH(gh, customer);
				if (recommended.isEmpty()) {
					System.out.println("추천할 숙소가 없습니다.");
				} else {
					for (Guesthouse g : recommended) {
						System.out.println(g.getName());
					}
				}
				break;

			/**
			 * 예약률 확인 가능한 게스트하우스 목록
			 */
			case 6:
				System.out.println("예약률 확인 가능한 게스트하우스 목록");
				for (int i = 0; i < gh.size(); i++) {
					System.out.println((i + 1) + ". " + gh.get(i).getName());
				}

				System.out.println("예약률을 확인할 게스트하우스 번호: ");
				int ghIndex = Integer.parseInt(sc.nextLine()) - 1;
				Guesthouse select = gh.get(ghIndex);

				System.out.println("날짜를 입력하세요(yyyy-mm-dd)");
				LocalDate checkDate = LocalDate.parse(sc.nextLine());

				double rate = manager.calcReservationRate(select, checkDate);
				System.out.printf("[%s] '%s' 예약률 : %.2f%%\n", checkDate, select.getName(), rate);
				break;

			/**
			 * 검색할 게스트하우스 유형
			 */
			case 7:
				System.out.println("검색할 게스트하우스 유형(예: 음악, 반려동물, 파티 등)을 입력하세요:");
				String typeToSearch = sc.nextLine();

				boolean found = manager.hasFeature(gh, typeToSearch);
				if (!found) {
					System.out.println("해당 유형의 게스트하우스가 존재하지 않습니다.");
				}
				break;

			case 8:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);

			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
}