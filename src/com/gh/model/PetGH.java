package com.gh.model;

/**
 * {@code PetGH} 클래스는 반려동물을 돌볼 수 있는 최소 나이 제한을 나타냅니다.
 * <p>
 * 정적(static) 필드 {@code minAgeRequired}를 통해, 모든 인스턴스에서 공통으로 적용되는 최소 연령을 설정 및 관리합니다.
 * </p>
 * 
 * 예: 반려동물 호텔이나 돌봄 서비스에서 이용 가능한 최소 나이 기준을 설정하는 데 사용됩니다.
 * 
 * @author YangJunYong
 */
public class PetGH {

    /**
     * 반려동물을 돌볼 수 있는 최소 연령.
     * <p>모든 인스턴스에서 공유되는 정적(static) 필드입니다.</p>
     */
    private static int minAgeRequired;

    /**
     * 기본 생성자입니다.
     * <p>최소 연령을 설정하지 않고 객체를 생성합니다.</p>
     */
    public PetGH() {
        super();
    }

    /**
     * 최소 연령을 설정하여 {@code PetGH} 객체를 생성합니다.
     * 
     * @param minAgeRequired 반려동물을 돌볼 수 있는 최소 연령
     */
    public PetGH(int minAgeRequired) {
        PetGH.minAgeRequired = minAgeRequired;
    }

    /**
     * 현재 설정된 최소 연령을 반환합니다.
     * 
     * @return 반려동물을 돌볼 수 있는 최소 연령
     */
    public static int getMinAgeRequired() {
        return minAgeRequired;
    }

    /**
     * 반려동물을 돌볼 수 있는 최소 연령을 설정합니다.
     * 
     * @param minAgeRequired 설정할 최소 연령
     */
    public static void setMinAgeRequired(int minAgeRequired) {
        PetGH.minAgeRequired = minAgeRequired;
    }

    /**
     * {@code PetGH} 객체의 문자열 표현을 반환합니다.
     * 
     * @return 클래스 이름과 최소 연령 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return super.toString() + "PetGH [minAgeRequired=" + minAgeRequired + "]";
    }
}