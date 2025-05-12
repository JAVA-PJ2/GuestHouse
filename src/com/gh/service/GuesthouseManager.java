package com.gh.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gh.model.Guesthouse;
import com.gh.user.Customer;

public class GuesthouseManager {
    
    /**
     * 가중치 기반 추천 숙소 리스트 반환
     */
    public List<Guesthouse> getRecommendedByGH(List<Guesthouse> guesthouses, Customer customer) {
        List<Guesthouse> recommended = new ArrayList<>();

        // 고객의 예약 내역이 있을 경우 해당 내역에 맞는 가중치를 계산
        if (customer.getBookings().isEmpty()) {
            System.out.println("추천할 숙소가 없습니다. 고객님의 예약 내역을 먼저 확인해주세요.");
            return recommended;
        }

        // 고객의 예약 내역을 기반으로 각 게스트하우스의 가중치를 계산
        guesthouses.sort((g1, g2) -> {
            // 가중치 계산: 예약 수와 매출을 기반으로 비교
            double weightedScore1 = calculateWeight(g1, customer);
            double weightedScore2 = calculateWeight(g2, customer);

            return Double.compare(weightedScore2, weightedScore1);
        });

        // 상위 3개 숙소만 추천
        for (int i = 0; i < Math.min(3, guesthouses.size()); i++) {
            recommended.add(guesthouses.get(i));
        }

        return recommended;
    }

    private double calculateWeight(Guesthouse gh, Customer customer) {
        // 고객이 예약한 숙소와 현재 게스트하우스의 매칭
        long bookingCount = customer.getBookings().stream()
            .filter(b -> b.getGuesthouse().equals(gh))
            .count();
        
        // 매출 기반 점수와 예약 기반 점수를 합산 (가중치 조정)
        double salesWeight = gh.getTotalSales() * 0.4;
        double bookingWeight = bookingCount * 0.6;
        
        return salesWeight + bookingWeight;
    }
    
    /**
     * 특정 날짜의 전체 예약률을 계산합니다.
     * 
     * @param guesthouses 예약 대상 게스트하우스 목록
     * @param date 예약률을 계산할 날짜
     * @return 예약률 (0.0 ~ 100.0)
     */
    public double calcReservationRate(List<Guesthouse> guesthouses, LocalDate date) {
        int totalCapacity = 0;
        int totalReserved = 0;

        for (Guesthouse gh : guesthouses) {
            totalCapacity += gh.getMaxPeople(); // 해당 게하 최대 수용 인원

            int reserved = gh.getDailyPeople().getOrDefault(date, 0); // 해당 날짜 예약 인원
            totalReserved += reserved;
        }

        if (totalCapacity == 0) return 0.0;

        return (double) totalReserved / totalCapacity * 100;
    }

}
