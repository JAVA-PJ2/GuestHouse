package com.gh.model;

/**
 * {@code PartyGH} 클래스는 반려동물 관련 파티 게스트하우스를 나타내는 클래스입니다.
 * <p>
 * 이 클래스는 {@code Guesthouse}를 상속하며, 모든 인스턴스에 공통적으로 적용되는 
 * 파티 참여를 위한 최소 연령을 관리합니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class PartyGH extends Guesthouse {

    /**
     * 파티에 참여하기 위한 최소 연령입니다.
     * <p>
     * 정적(static) 필드로서, 모든 {@code PartyGH} 인스턴스에서 공유됩니다.
     * </p>
     */
    private static int minAgeRequired;

    /**
     * 기본 생성자입니다.
     * <p>객체를 생성하지만 최소 연령은 설정하지 않습니다.</p>
     */
    public PartyGH() {
        super();
    }

    /**
     * 파티 공간의 정보를 포함하여 {@code PartyGH} 객체를 생성합니다.
     *
     * @param bookingId       예약 ID
     * @param name            파티 공간 이름
     * @param type            공간 유형
     * @param pricePerDays    하루 대여 요금
     * @param maxPeople       수용 가능 최대 인원
     * @param currentPeople   현재 인원
     * @param description     공간 설명
     * @param minAgeRequired  파티 참여를 위한 최소 연령
     */
    public PartyGH(int bookingId, String name, String type, double pricePerDays,
                   int maxPeople, int currentPeople, String description, int minAgeRequired) {
        super(bookingId, name, type, pricePerDays, maxPeople, currentPeople, description);
        PartyGH.minAgeRequired = minAgeRequired;
    }

    /**
     * 현재 설정된 최소 연령을 반환합니다.
     *
     * @return 파티 참여를 위한 최소 연령
     */
    public static int getMinAgeRequired() {
        return minAgeRequired;
    }

    /**
     * 파티 참여를 위한 최소 연령을 설정합니다.
     *
     * @param minAgeRequired 설정할 최소 연령
     */
    public static void setMinAgeRequired(int minAgeRequired) {
        PartyGH.minAgeRequired = minAgeRequired;
    }

    /**
     * {@code PartyGH} 객체의 문자열 표현을 반환합니다.
     *
     * @return 예약 정보와 최소 연령을 포함한 문자열
     */
    @Override
    public String toString() {
        return super.toString() + "PartyGH [minAgeRequired=" + minAgeRequired + "]";
    }
}