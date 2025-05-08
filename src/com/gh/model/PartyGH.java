package com.gh.model;

/**
 * {@code PartyGH} 클래스는 반려동물 관련 파티 또는 이벤트에 대한 최소 나이 제한을 나타내는 클래스입니다.
 * <p>
 * 해당 파티에 참여하기 위한 최소 연령을 설정하고 관리합니다.
 * 모든 인스턴스가 이 값을 공유합니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class PartyGH {
    
    /**
     * 파티에 참여하기 위한 최소 연령.
     * 모든 인스턴스에서 공유되는 정적 필드입니다.
     */
    private static int minAgeRequired;

    /**
     * 기본 생성자.
     * 최소 연령을 설정하지 않고 {@code PartyGH} 객체를 생성합니다.
     */
    public PartyGH() {
        super();
    }

    /**
     * 최소 연령을 설정하여 {@code PartyGH} 객체를 생성합니다.
     */
    public PartyGH(int minAgeRequired) {
        super();
        this.minAgeRequired = minAgeRequired;
    }

    /**
     * 현재 설정된 최소 연령을 반환합니다.
     */
    public static int getMinAgeRequired() {
        return minAgeRequired;
    }

    /**
     * 최소 연령을 설정합니다.
     */
    public static void setMinAgeRequired(int minAgeRequired) {
        PartyGH.minAgeRequired = minAgeRequired;
    }

    /**
     * {@code PartyGH} 객체의 문자열 표현을 반환합니다.
     */
    @Override
    public String toString() {
        return super.toString() + "PartyGH []";
    }
}