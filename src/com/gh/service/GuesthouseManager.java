package com.gh.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gh.model.Guesthouse;
import com.gh.user.Customer;

public class GuesthouseManager {
	/**
     * 가중치 기반 추천 숙소 리스트 반환
     * 
     * <p>
     *  가중치는 다음 기준으로 계산이 된다.
     *  <ul>
     * 	  <li>해당 게스트하우스의 총 매출: 40%</li>
     * 	  <li>고객의 해당 게스트하우스 예약 횟수: 60%</li>
     * 	</ul>
     *  추천 목록은 가중치를 기준으로 내림차순 정렬되어 반환된다.
     * <p>
     * 
     * @param gueshoueses 추천 대상 게스트하우스 목록
     * @param customer 추천 기준이 되는 고객 정보
     * @return 가중치 기반으로 정렬된 게스트하우스 목록 (추천 순서대로)
     *  
     */
    public List<Guesthouse> getRecommendedByGH(List<Guesthouse> guesthouses, Customer customer) {
        List<Guesthouse> recommended = guesthouses;
        Map<Guesthouse, Double> weightMap = new HashMap<>();

        // 고객의 예약 내역이 있을 경우 해당 내역에 맞는 가중치를 계산
        if (customer.getBookings().isEmpty()) {
            System.out.println("추천할 숙소가 없습니다. 고객님의 예약 내역을 먼저 확인해주세요.");
            return recommended;
        }

        for (Guesthouse gh : guesthouses) {
        	double sales = gh.getTotalSales();
        	long bookingCount = gh.getBookingCount(customer); // 고객 기준 예약 수
        	double weight = sales * 0.4 + bookingCount * 0.6;
        	weightMap.put(gh, weight);
        }
        
        // 가중치 기준 내림차순 정렬
        List<Guesthouse> sorted = new ArrayList<>(guesthouses);
        sorted.sort((gh1, gh2) -> Double.compare(weightMap.get(gh2), weightMap.get(gh1)));
        return sorted;
    }
    
    /**
     * 특정 날짜에, 특정 게스트하우스의 예약률 계산
     */
    public double calcReservationRate(Guesthouse guesthouse, LocalDate date) {
        int capacity = guesthouse.getMaxPeople();
        int reserved = guesthouse.getDailyPeople().getOrDefault(date, 0);

        if (capacity == 0) return 0.0;

        return (double) reserved / capacity * 100;
    }
    
    /**
     * 특정 게하의 존재 여부 구현
     * 
     * @param gh
     * @param feature
     * @return
     */
    public boolean hasFeature(List<Guesthouse> gh, String feature) {
    	boolean found = false;
        for (Guesthouse g : gh) {
            if (g.getType().equalsIgnoreCase(feature)) {
                System.out.println("- " + g.getName() + " (" + g.getType() + ")");
                found = true;
            }
        }
        return found;
    }
}
