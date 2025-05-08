package com.gh.model;

/**
 * Guesthouse 클래스는 게스트하우스의 정보를 담는 엔티티 클래스입니다.
 * 예약 ID, 이름, 타입, 가격, 최대 수용 인원, 현재 인원, 설명정보를 포함합니다.
 */
public class Guesthouse {
	private int bookingId;			//예약 고유번호
	private String name;			//게스트하우스 이름
	private String type;			//게스트하우스 유형
	private double pricePerDays;	//1박당 가격
	private int maxPeople;			//최대 수용 인원
	private int currentPeople;		//현재 예약된 인원
	private String description;		//게스트하우스 설명
	
	/**
	 * 기본 생서자
	 */
	public Guesthouse() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param bookingId		예약 고유 번호
	 * @param name			게스트하우스 이름
	 * @param type			게스트하우스 유형
	 * @param pricePerDays	1박당 가격
	 * @param maxPeople		최대 수용 인원
	 * @param currentPeople	현재 예약된 인원
	 * @param description	게스트하우스 설명
	 */
	public Guesthouse(int bookingId, String name, String type, double pricePerDays, int maxPeople, int currentPeople,
			String description) {
		super();
		this.bookingId = bookingId;
		this.name = name;
		this.type = type;
		this.pricePerDays = pricePerDays;
		this.maxPeople = maxPeople;
		this.currentPeople = currentPeople;
		this.description = description;
	}
	/**
	 * @return 예약 고유 번호
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId 예약 고유 번호 설정
	 */
	public void setBookingId(int bookingId) {
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
	 * @return 현재 예약된 인원
	 */
	public int getCurrentPeople() {
		return currentPeople;
	}
	/**
	 * @param currentPeople 현재 예약된 인원 설정
	 */
	public void setCurrentPeople(int currentPeople) {
		this.currentPeople = currentPeople;
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
	
	@Override
	public String toString() {
		return "Guesthouse [bookingId=" + bookingId + ", name=" + name + ", type=" + type + ", pricePerDays="
				+ pricePerDays + ", maxPeople=" + maxPeople + ", currentPeople=" + currentPeople + ", description="
				+ description + "]";
	}
	
} //class