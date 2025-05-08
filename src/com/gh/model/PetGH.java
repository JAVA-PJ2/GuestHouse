package com.gh.model;

/**
 * {@code PetGH} 클래스는 반려동물 전용 게스트하우스를 나타내는 클래스입니다.
 * <p>
 * 반려동물의 종류, 케어 시스템 비용, 응급 대처 가능 여부 등의 정보를 포함하며,
 * {@code Guesthouse} 클래스를 상속합니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class PetGH extends Guesthouse {

    /**
     * 반려동물의 종류를 정의하는 열거형입니다.
     * <p>현재는 개(DOG), 고양이(CAT)만 지원합니다.</p>
     */
    private enum PetType {
        DOG, CAT
    }

    /**
     * 반려동물 케어 시스템 추가 요금
     */
    private double careSystemPrice;

    /**
     * 반려동물의 종류
     */
    private PetType petType;

    /**
     * 응급 상황 대처 가능 여부
     */
    private boolean emergency;

    /**
     * 반려동물 케어 시스템 가격을 반환합니다.
     *
     * @return 케어 시스템 가격
     */
    public double getCareSystemPrice() {
        return careSystemPrice;
    }

    /**
     * 반려동물의 종류를 반환합니다.
     *
     * @return {@code PetType} (DOG 또는 CAT)
     */
    public PetType getPetType() {
        return petType;
    }

    /**
     * 응급 상황 대처 가능 여부를 반환합니다.
     *
     * @return 응급 상황 대응 가능 시 {@code true}, 아니면 {@code false}
     */
    public boolean isEmergency() {
        return emergency;
    }

    /**
     * 기본 생성자입니다.
     * 필드를 초기화하지 않고 객체를 생성합니다.
     */
    public PetGH() {
        super();
    }

    /**
     * 모든 필드를 초기화하여 {@code PetGH} 객체를 생성합니다.
     *
     * @param bookingId 예약 ID
     * @param name 게스트하우스 이름
     * @param type 공간 유형
     * @param pricePerDays 하루 대여 요금
     * @param maxPeople 수용 가능 인원
     * @param currentPeople 현재 예약된 인원
     * @param description 설명
     * @param careSystemPrice 반려동물 돌봄 시스템 가격
     * @param petType 반려동물 종류 (DOG, CAT)
     * @param emergency 응급 상황 대처 가능 여부
     */
    public PetGH(int bookingId, String name, String type, double pricePerDays, int maxPeople, int currentPeople,
                 String description, double careSystemPrice, PetType petType, boolean emergency) {
        super(bookingId, name, type, pricePerDays, maxPeople, currentPeople, description);
        this.careSystemPrice = careSystemPrice;
        this.petType = petType;
        this.emergency = emergency;
    }

    /**
     * {@code PetGH} 객체의 상태를 문자열로 반환합니다.
     *
     * @return 객체 상태를 표현한 문자열
     */
    @Override
    public String toString() {
        return super.toString() + "PetGH [careSystemPrice=" + careSystemPrice 
                + ", petType=" + petType 
                + ", emergency=" + emergency + "]";
    }
}