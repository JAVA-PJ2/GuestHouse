package com.gh.model;

/**
 * {@code MusicGH} 클래스는 음악 관련 시설의 특징을 나타내는 클래스입니다.
 * <p>
 * 악기 보유 여부, 방음 여부, 악기 대여 가능 여부 등의 정보를 포함합니다.
 * </p>
 * 예: 연습실이나 음악 스튜디오 정보 모델링에 활용될 수 있습니다.
 * 
 * @author YangJunYong 
 */
public class MusicGH {
    /**
     * 악기를 보유하고 있는지 여부.
     */
    private boolean hasinstruments;
    
    /**
     * 방음 시설이 갖추어져 있는지 여부.
     */
    private boolean isSoundProof;
    
    /**
     * 악기 대여가 가능한지 여부.
     */
    private boolean instrumentRental;

    /**
     * 모든 필드를 설정하는 생성자.
     */
    public MusicGH(boolean hasinstruments, boolean isSoundProof, boolean instrumentRental) {
        super();
        this.hasinstruments = hasinstruments;
        this.isSoundProof = isSoundProof;
        this.instrumentRental = instrumentRental;
    }

    /**
     * 기본 생성자.
     * 필드를 설정하지 않고 객체를 생성합니다.
     */
    public MusicGH() {
        super();
    }

    /**
     * 악기를 보유하고 있는지 여부를 반환합니다.
     */
    public boolean isHasinstruments() {
        return hasinstruments;
    }

    /**
     * 방음 시설이 갖추어져 있는지 여부를 반환합니다.
     */
    public boolean isSoundProof() {
        return isSoundProof;
    }

    /**
     * 악기 대여가 가능한지 여부를 반환합니다.
     */
    public boolean isInstrumentRental() {
        return instrumentRental;
    }

    /**
     * {@code MusicGH} 객체의 문자열 표현을 반환합니다.
     */
    @Override
    public String toString() {
        return super.toString() + "MusicGH [hasinstruments=" + hasinstruments + ", isSoundProof=" + isSoundProof + ", instrumentRental="
                + instrumentRental + "]";
    }
}