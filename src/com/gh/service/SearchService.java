package com.gh.service;

import java.util.List;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Customer;

/**
 * {@code SearchService} 인터페이스는 예약 시스템 내에서 검색 기능을 정의합니다.
 * 
 * <p>
 * 고객의 예약 내역 조회, 특정 게스트하우스의 예약 내역 확인, 
 * 게스트하우스의 기능(feature) 존재 여부 확인 등 검색 관련 메서드를 제공합니다.
 * </p>
 * 
 * @author 소유나, 우승환, 양준용
 */

public interface SearchService extends GHService {
	/**
	 * 고객의 예약 정보를 검색합니다.
	 * 
	 * @param cs 조회할 고객
	 * @return 해당 고객의 모든 예약 목록
	 */
	List<Booking> findBooking(Customer cs);

	/**
	 * 특정 게스트하우스의 예약 목록을 조회합니다.
	 * 
	 * @param gh 조회 대상 게스트하우스
	 * @return 해당 게스트하우스의 예약 목록
	 */
	List<Booking> findBookingByGHName(Guesthouse gh);

	/**
	 * 특정한 게하에 지정한 기능(Feature)을 가졌는지 여부를 조회합니다.
	 * 
	 * @param gh      찾고자 하는 게스트하우스
	 * @param feature 찾고자 하는 기능명
	 * @return 해당 기능이 포함된 여부로 true: 존재함, false: 없음
	 */
	boolean hasFeature(Guesthouse gh, String feature);
}
