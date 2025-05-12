package com.gh.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Customer;

/**
 * BookingFileManager는 고객의 예약 정보를 CSV 파일에 저장하고 불러오는 기능을 제공한다.
 * 
 * @author 소유나, 우승환, 양준용
 */
public class BookingFileManager {

	/**
	 * 고객의 모든 예약 정보를 CSV 파일로 저장한다. 기존 파일이 존재할 경우 덮어쓴다.
	 *
	 * @param bookings 고객의 예약 목록
	 * @param c        예약을 가진 고객 객체
	 */
	// 1. 전체 Booking 리스트를 csv에 저장(덮어쓰기)
	public static void saveBookings(List<Booking> bookings, Customer c) {
		String FILE_PATH = "booking-" + c.getName() + ".csv";
		// fileWriter로 전체 예약을 FILE_PATH에 저장
		try (BufferedWriter br = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {

			br.write("bookingId,startDate,endDate,bookingDays,numberOfPeople,isCancelled,guesthouseId,customerEmail");
			br.newLine();

			for (Booking b : bookings) {
				String line = String.join(", ", b.getBookingId(), b.getStartDate().toString(),
						b.getEndDate().toString(), String.valueOf(b.getBookingDays()),
						String.valueOf(b.getNumberOfPeople()), String.valueOf(b.getIsCancled()),
						b.getGuesthouse().getBookingId(), c.getEmail());
				br.write(line);
				br.newLine();
			}
		} catch (IOException e) {
			System.out.println("[" + c.getName() + "] 예약 저장 중 오류 발생: " + e.getMessage());
		}
	}

	/**
	 * 고객의 예약 정보를 CSV 파일에서 불러와 Customer 객체와 시스템에 복원한다. 예약된 게스트하우스 정보는 전달받은 리스트에서
	 * 참조하여 연결한다.
	 *
	 * @param c              예약을 불러올 고객 객체
	 * @param guesthouseList 시스템 내의 모든 게스트하우스 목록
	 */
	// 2. csv에서 Booking 리스트를 불러오기
	public static void loadBookings(Customer c, List<Guesthouse> guesthouseList) {
		String fileName = "booking-" + c.getName() + ".csv";
		File file = new File(fileName);

		if (!file.exists()) {
			System.out.println("[" + c.getName() + "] 예약 정보 파일이 없어 새로 생성됩니다.");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			// 첫 줄 헤더 스킵
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");

				if (parts.length < 7)
					continue;

				String bookingId = parts[0].trim();
				LocalDate startDate = LocalDate.parse(parts[1].trim());
				LocalDate endDate = LocalDate.parse(parts[2].trim());
				int bookingDays = Integer.parseInt(parts[3].trim());
				int numberOfPeople = Integer.parseInt(parts[4].trim());
				boolean isCancled = Boolean.parseBoolean(parts[5].trim());
				String guesthouseId = parts[6].trim();

				// guesthouseId로 Guesthouse 객체 찾기
				Guesthouse gh = null;
				for (Guesthouse g : guesthouseList) {
					if (g.getBookingId().equals(guesthouseId)) {
						gh = g;
						break;
					}
				}

				if (gh == null) {
					System.out.println("[" + c.getName() + "] 게스트하우스 ID " + guesthouseId + "에 해당하는 숙소가 없습니다.");
					continue;
				}

				// Booking 객체 생성
				Booking booking = new Booking(startDate, bookingDays, numberOfPeople, gh);
				booking.setBookingId(bookingId);
				booking.setEndDate(endDate);
				booking.setIsCancled(isCancled);

				if (!isCancled) {
					gh.addPeople(startDate, endDate, numberOfPeople);
				}

				// 리스트에 추가
				c.getBookings().add(booking);
				BookingServiceImpl.getInstance().getBookings().add(booking);
			}
			System.out.println("[" + c.getName() + "] 예약 정보 불러오기 완료.");
		} catch (IOException e) {
			System.out.println("[" + c.getName() + "] 예약 정보 파일 없음 또는 오류: " + e.getMessage());
		}
	}
}
