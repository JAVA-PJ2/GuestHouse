package com.gh.model;
/**
 * {@code PetGH} 클래스는 반려동물에 대한 최소 나이 제한을 나타내는 클래스입니다.
 * <p>
 * 모든 인스턴스에서 공유되는 정적 필드 {@code minAgeRequired}를 사용하여,
 * 반려동물을 돌볼 수 있는 최소 연령을 설정했습니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class PetGH {
	private static int minAgeRequired;
	/**
     * 기본 생성자.
     * 최소 나이를 설정하지 않고 {@code PetGH} 객체를 생성합니다.
     */
    public PetGH() {
        super();
    }

    /**
     * 최소 나이를 설정하여 {@code PetGH} 객체를 생성합니다.
     */
    public PetGH(int minAgeRequired) {
        PetGH.minAgeRequired = minAgeRequired;
    }

    /**
     * 설정된 최소 나이를 반환합니다.
     */
    public static int getMinAgeRequired() {
        return minAgeRequired;
    }

    /**
     * 최소 나이를 설정합니다.
     */
    public static void setMinAgeRequired(int minAgeRequired) {
        PetGH.minAgeRequired = minAgeRequired;
    }

    /**
     * {@code PetGH} 객체의 문자열 표현을 반환합니다.
     */
    @Override
    public String toString() {
        return super.toString() + "PetGH []";
    }
}
