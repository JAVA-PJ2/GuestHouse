package com.gh.service;

import java.time.LocalDate;
import java.util.List;

import com.gh.model.Guesthouse;
import com.gh.user.Customer;

/**
 *  예약 통계 및 분석기능을 제공합니다
 *  가격 프로모션, 추천숙소, 예약률 계산등의 기능이 포함됩니다.
 */
public interface AnalyticsService {
	
	/**
	 * 이름으로 특정 게스트하우스의 1박당 가격에 할인율을 적용합니다.
	 * @param name    		게하이름
	 * @param discountRate	적욜할 할인율(예 : 0.1은 10%할인)
	 */
	void applyPromotion(String name, double discountRate);
	
	/**
	 * 고객에게 추천할 숙소 이름 목록을 반환합니다.
	 * 
	 * <p>
	 * 총 매출(40%)과 예약 수(60%)의 가중치를 기반으로 점수를 계산하여 전체 게스트하우스 중 상위 5개를 선정하고, 숙소 이름만
	 * 반환합니다.
	 * </p>
	 *
	 * @param customer 추천 기준이 되는 고객 정보
	 * @return 추천 숙소 이름(String)의 리스트 (최대 5개)
	 */
	List<Guesthouse> getRecommendedByGH(List<Guesthouse> gh, Customer customer);
	
	/**
	 * 특정 날짜의 예약률을 계산하여 반환합니다.
	 * @param date			예약률을 계산할 날짜
	 * @return				에약률
	 */
	double calcReservationRate(LocalDate date);
}
