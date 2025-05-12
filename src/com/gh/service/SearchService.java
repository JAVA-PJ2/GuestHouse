package com.gh.service;

import java.util.List;

import com.gh.model.Booking;
import com.gh.model.Guesthouse;
import com.gh.user.Customer;

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
