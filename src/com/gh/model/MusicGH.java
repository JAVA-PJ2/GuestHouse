package com.gh.model;

/**
 * {@code MusicGH} 클래스는 음악 관련 공간(예: 연습실, 스튜디오 등)의 특성을 나타냅니다.
 * <p>
 * 악기 보유 여부, 방음 여부, 악기 대여 가능 여부 등의 정보를 포함합니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class MusicGH extends Guesthouse {
	/**
	 * 악기를 보유하고 있는지 여부
	 */
	private boolean hasinstruments;

	/**
	 * 방음 시설이 갖추어져 있는지 여부
	 */
	private boolean isSoundProof;

	/**
	 * 악기 대여가 가능한지 여부
	 */
	private boolean instrumentRental;

	/**
	 * 모든 특성을 초기화하는 생성자입니다.
	 * 
	 * @param bookingId        예약 ID
	 * @param name             게스트하우스 이름
	 * @param type             공간 유형
	 * @param pricePerDays     하루 대여 요금
	 * @param maxPeople        수용 가능 인원
	 * @param currentPeople    현재 예약된 인원
	 * @param description      공간 설명
	 * @param hasinstruments   악기 보유 여부
	 * @param isSoundProof     방음 시설 여부
	 * @param instrumentRental 악기 대여 가능 여부
	 */
	public MusicGH(String bookingId, String name, String type, double pricePerDays, int maxPeople, int currentPeople,
			String description, boolean hasinstruments, boolean isSoundProof, boolean instrumentRental) {
		super(bookingId, name, type, pricePerDays, maxPeople, currentPeople, description);
		this.hasinstruments = hasinstruments;
		this.isSoundProof = isSoundProof;
		this.instrumentRental = instrumentRental;
	}

	/**
	 * 기본 생성자입니다.
	 * <p>
	 * 필드를 초기화하지 않고 객체를 생성합니다.
	 * </p>
	 */
	public MusicGH() {
		super();
	}

	/**
	 * 악기를 보유하고 있는지 여부를 반환합니다.
	 * 
	 * @return 악기 보유 여부
	 */
	public boolean isHasinstruments() {
		return hasinstruments;
	}

	/**
	 * 방음 시설이 갖추어져 있는지 여부를 반환합니다.
	 * 
	 * @return 방음 여부
	 */
	public boolean isSoundProof() {
		return isSoundProof;
	}

	/**
	 * 악기 대여가 가능한지 여부를 반환합니다.
	 * 
	 * @return 악기 대여 가능 여부
	 */
	public boolean isInstrumentRental() {
		return instrumentRental;
	}

	/**
	 * {@code MusicGH} 객체의 문자열 표현을 반환합니다.
	 * 
	 * @return 객체 상태를 포함한 문자열
	 */
	@Override
	public String toString() {
		return super.toString() + "MusicGH [hasinstruments=" + hasinstruments + ", isSoundProof=" + isSoundProof
				+ ", instrumentRental=" + instrumentRental + "]";
	}
}