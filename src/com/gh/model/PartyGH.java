package com.gh.model;

/**
 * {@code PartyGH} 클래스는 반려동물 관련 파티 또는 이벤트에 대한 최소 나이 제한을 나타냅니다.
 * <p>
 * 파티에 참여하기 위한 최소 연령을 설정하고 관리하며, 이 값은 모든 인스턴스에 공통적으로 적용됩니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class PartyGH {

    /**
     * 파티에 참여하기 위한 최소 연령.
     * <p>모든 인스턴스에서 공유되는 정적(static) 필드입니다.</p>
     */
    private static int minAgeRequired;

    /**
     * 기본 생성자입니다.
     * <p>최소 연령을 설정하지 않고 {@code PartyGH} 객체를 생성합니다.</p>
     */
    public PartyGH() {
        super();
    }

    /**
     * 최소 연령을 설정하여 {@code PartyGH} 객체를 생성합니다.
     * 
     * @param minAgeRequired 파티 참여를 위한 최소 연령
     */
    public PartyGH(int minAgeRequired) {
        super();
        PartyGH.minAgeRequired = minAgeRequired;
    }

    /**
     * 현재 설정된 최소 연령을 반환합니다.
     * 
     * @return 최소 연령
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
     * @return 클래스 이름과 최소 연령 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return super.toString() + "PartyGH [minAgeRequired=" + minAgeRequired + "]";
    }
}