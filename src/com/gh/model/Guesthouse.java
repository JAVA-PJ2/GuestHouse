package com.gh.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gh.user.Customer;
import com.gh.service.BookingServiceImpl;

/**
 * Guesthouse 클래스는 게스트하우스의 정보를 담는 엔티티 클래스입니다. 예약 ID, 이름, 타입, 가격, 최대 수용 인원, 현재
 * 인원, 설명정보를 포함합니다.
 * 
 * @author 우승환
 */
public class Guesthouse {
	private String bookingId; // 예약 고유번호
	private String name; // 게스트하우스 이름
	private String type; // 게스트하우스 유형
	private double pricePerDays; // 1박당 가격
	private int maxPeople; // 최대 수용 인원
	private String description; // 게스트하우스 설명
	private double totalSales; // 게스트하우스의 총 매출
	private Map<LocalDate, Integer> dailyPeople = new HashMap<>();
	private List<String> features = new ArrayList<>(); // 게스트하우스 특성 목록 (예: 음악, 반려동물, 파티 등)

	/**
	 * 기본 생성자
	 */
	public Guesthouse() {
		super();
	}

	/**
	 * @param bookingId     예약 고유 번호
	 * @param name          게스트하우스 이름
	 * @param type          게스트하우스 유형
	 * @param pricePerDays  1박당 가격
	 * @param maxPeople     최대 수용 인원
	 * @param currentPeople 현재 예약된 인원
	 * @param description   게스트하우스 설명
	 */
	public Guesthouse(String bookingId, String name, String type, double pricePerDays, int maxPeople, int currentPeople,
			String description) {
		super();
		this.bookingId = bookingId;
		this.name = name;
		this.type = type;
		this.pricePerDays = pricePerDays;
		this.maxPeople = maxPeople;
		this.description = description;
	}

	/**
	 * @return 예약 고유 번호
	 */
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * @param bookingId 예약 고유 번호 설정
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * @return 게스트 하우스 이름
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 게스트하우스 이름 설정
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 게스트하우스 유형
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 게스트하우스 유형 설정
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 1박당 가격
	 */
	public double getPricePerDays() {
		return pricePerDays;
	}

	/**
	 * @param pricePerDays 1박당 가격 설정
	 */
	public void setPricePerDays(double pricePerDays) {
		this.pricePerDays = pricePerDays;
	}

	/**
	 * @return 최대 수용 인원
	 */
	public int getMaxPeople() {
		return maxPeople;
	}

	/**
	 * @param maxPeople 최대 수용 인원 설정
	 */
	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	/**
	 * @return 게스트하우스 설명
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description 게스트하우스 설명 설정
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param totalSales 게스트하우스의 총 매출 설정
	 */
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	/**
	 * @return 게스트하우스의 총 매출 반환
	 */
	public double getTotalSales() {
		return totalSales;
	}

	/**
	 * 날짜별 인원 추가
	 * 
	 * @param start     체크인
	 * @param end       체크아웃
	 * @param numPeople 투숙인원수
	 */
	public void addPeople(LocalDate start, LocalDate end, int numPeople) {
		LocalDate date = start;
		while (!date.isAfter(end.minusDays(1))) {
			int updated = dailyPeople.getOrDefault(date, 0) + numPeople;
			dailyPeople.put(date, updated);
			date = date.plusDays(1);
		}
	}

	/**
	 * 날짜별 인원 제거
	 * 
	 * @param start     체크인
	 * @param end       체크아웃
	 * @param numPeople 투숙인원수
	 */
	public void removePeople(LocalDate start, LocalDate end, int numPeople) {
		LocalDate date = start;
		while (!date.isAfter(end.minusDays(1))) {
			int updated = dailyPeople.getOrDefault(date, 0) - numPeople;
			if (updated <= 0) {
				dailyPeople.remove(date);
			} else {
				dailyPeople.put(date, updated);
			}
			date = date.plusDays(1);
		}
	}

	/**
	 * 해당 날짜 구간 예약 가능 여부
	 *
	 * @param start     체크인
	 * @param end       체크아웃
	 * @param numPeople 투숙인원수
	 * @return 예약 가능 여부
	 */
	public boolean canBook(LocalDate start, LocalDate end, int numPeople) {
		LocalDate date = start;
		while (!date.isAfter(end.minusDays(1))) {
			int current = dailyPeople.getOrDefault(date, 0);
			if (current + numPeople > maxPeople) {
				return false;
			}
			date = date.plusDays(1);
		}
		return true;
	}

	/**
	 * 해당 게스트하우스에 대한 예약 수를 계산
	 * 
	 * @return count
	 */
	public long getBookingCount(Customer cs) {
		// 예약 시스템의 booking 리스트에서 해당 게스트하우스를 기준으로 예약 계산
		long count = 0;
		// findBooking을 통해 해당 고객의 예약 리스트를 가져옴
		for (Booking b : BookingServiceImpl.getInstance().findBooking(cs)) {
			if (b.getGuesthouse().equals(this)) {
				count++;
			}
		}
		return count;
	}

	// 필요 시 외부 접근용 getter
	public Map<LocalDate, Integer> getDailyPeople() {
		return dailyPeople;
	}

	/**
	 * 특성 리스트에 해당 특성이 포함되는지 확인
	 * 
	 * @param feature 확인할 특성
	 * @return 해당 특성이 포함되어있으면 true, 없으면 false
	 */
	public boolean hasFeature(String feature) {
		return features.contains(feature);
	}

	@Override
	public String toString() {
		return "Guesthouse [bookingId=" + bookingId + ", name=" + name + ", type=" + type + ", pricePerDays="
				+ pricePerDays + ", maxPeople=" + maxPeople + ", description=" + description + "]";
	}
}