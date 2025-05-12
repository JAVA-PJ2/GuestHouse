package com.gh.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.model.MusicGH;
import com.gh.model.PartyGH;
import com.gh.model.PetGH;
import com.gh.service.BookingServiceImpl;
import com.gh.user.Account;
import com.gh.user.Customer;

public class Main {
	private static final Scanner sc = new Scanner(System.in);
	private static final BookingServiceImpl service = BookingServiceImpl.getInstance();

	public static void main(String[] args) {
		// 게스트하우스 생성
		List<Guesthouse> gh = new ArrayList<>();
		gh.add(new MusicGH("GH001", "뮤직존 스튜디오", "음악", 100.0, 5, 0, "방음 완비, 드럼/기타 보유", true, true, true));
		gh.add(new PetGH("GH002", "펫팰리스 도그하우스", "반려견", 80.0, 3, 0, "중형견 전용, 응급대응 지원", 30.0, null, true));
		gh.add(new PetGH("GH003", "펫팰리스 캣하우스", "반려묘", 75.0, 2, 0, "고양이 전용, 조용한 실내", 25.0, null, false));
		gh.add(new PartyGH("GH004", LocalDate.now(), LocalDate.now().plusDays(1), 1, 1, null, "파티존 루프탑", "파티", 150.0,
				10, 0, "루프탑 파티 공간, DJ 부스 완비", 19));
		gh.add(new Guesthouse("GH005", "일반 게스트하우스", "일반", 50.0, 4, 0, "저렴하고 깨끗한 일반 숙소"));

		// 고객 생성
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("홍길동", "hong@naver.com", new Account("hong123", 1000.0), new ArrayList<>()));
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

		// CRUD 메뉴
		while (true) {
			System.out.printf("\n===== %s 님의 게스트하우스 예약 시스템 =====\n", customer.getName());
			System.out.println("고객: " + customer.getName() + " | 잔액: " + customer.getAccount().getBalance());
			System.out.println("1. 예약하기");
			System.out.println("2. 예약 변경하기");
			System.out.println("3. 예약 취소하기");
			System.out.println("4. 이 고객의 모든 예약 조회하기");
			System.out.println("5. 추천 숙소 리스트");
			System.out.println("6. 종료");
			System.out.print("번호를 선택하세요: ");

			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {
			case 1:
				System.out.println("\n[예약 가능한 게스트하우스 목록]");
				for (int i = 0; i < gh.size(); i++) {
					System.out.println((i + 1) + ". " + gh.get(i).getName() + " - " + gh.get(i).getDescription());
				}

				System.out.print("예약할 게스트하우스 번호: ");
				int ghChoice = Integer.parseInt(sc.nextLine()) - 1;
				Guesthouse selectedGH = gh.get(ghChoice);

				System.out.print("시작 날짜 입력 (yyyy-mm-dd): ");
				LocalDate startDate = LocalDate.parse(sc.nextLine());

				System.out.print("숙박일수 입력: ");
				int days = Integer.parseInt(sc.nextLine());

				System.out.print("인원 수 입력: ");
				int people = Integer.parseInt(sc.nextLine());

				Booking newBooking = new Booking(startDate, days, people, selectedGH);
//				customer.getBookings().add(newBooking);
				service.addBooking(customer, newBooking);
				break;

			case 2:
				System.out.println("[예약 변경] 고객의 예약 목록:");
				List<Booking> userBookings = customer.getBookings();

				if (userBookings.isEmpty()) {
					System.out.println("예약 내역이 없습니다.");
					break;
				}

				for (int i = 0; i < userBookings.size(); i++) {
					Booking b = userBookings.get(i);
					System.out.printf("%d. [%s] %s ~ %s (%d명)\n", i + 1, b.getGuesthouse().getName(), b.getStartDate(),
							b.getEndDate(), b.getNumberOfPeople());
				}

				System.out.print("변경할 예약 번호를 선택하세요: ");
				int selection = Integer.parseInt(sc.nextLine()) - 1;

				if (selection < 0 || selection >= userBookings.size()) {
					System.out.println("잘못된 번호입니다.");
					break;
				}

				Booking selected = userBookings.get(selection);

				System.out.print("새 시작일 (yyyy-mm-dd): ");
				LocalDate newStart = LocalDate.parse(sc.nextLine());

				System.out.print("새 숙박일수 입력: ");
				int newDays = Integer.parseInt(sc.nextLine());

				System.out.print("새 인원 수 입력: ");
				int newPeople = Integer.parseInt(sc.nextLine());

				Booking modified = new Booking(newStart, newDays, newPeople, selected.getGuesthouse());
				modified.setBookingId(selected.getBookingId());

				service.updateBooking(customer, modified);
				break;

			case 3: // 예약 취소
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
					service.deleteBooking(customer, toCancel.getBookingId());
					cancelList.remove(cancelIndex);
					System.out.println("예약이 성공적으로 취소되었습니다.");
				} else {
					System.out.println("예약 취소가 취소되었습니다.");
				}
				break;

			case 4:
				// 해당 고객의 모든 예약 조회
				for (Booking b : service.findBooking(customer)) {
					System.out.println("- " + b.toString());
				}
				break;

			case 5:
				List<Guesthouse> recommended = service.getRecommendedByGH(customer);
			    if (recommended.isEmpty()) {
			        System.out.println("추천할 숙소가 없습니다.");
			    } else {
			        for (Guesthouse g : recommended) {
			            System.out.println(g.getName());
			        }
			    }
			    break;

			case 6:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);

			default:
				System.out.println("잘못된 입력입니다.");
			}
		}

	}
}