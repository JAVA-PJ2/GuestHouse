package com.gh.user;

import java.util.List;
import com.gh.model.Booking;

/**
 * {@code Customer} 클래스는 고객 정보를 나타냅니다.
 * <p>
 * 고객의 이름, 이메일, 계정 정보, 예약 목록 등을 포함합니다.
 * </p>
 * 이 클래스는 예약 시스템에서 사용자 정보를 저장하고 처리하는 데 사용됩니다.
 * 
 * @author YangJunYong
 */
public class Customer {

    /**
     * 고객 이름
     */
    private String name;

    /**
     * 고객 이메일
     */
    private String email;

    /**
     * 고객 계정 정보
     */
    private Account account;

    /**
     * 고객이 진행한 예약 목록
     */
    private List<Booking> bookings;

    /**
     * 기본 생성자.
     * 고객 정보를 설정하지 않고 {@code Customer} 객체를 생성합니다.
     */
    public Customer() {
        super();
    }

    /**
     * 고객 정보를 모두 설정하여 {@code Customer} 객체를 생성합니다.
     * 
     * @param name 고객 이름
     * @param email 고객 이메일
     * @param account 고객 계정 정보
     * @param bookings 고객의 예약 목록
     */
    public Customer(String name, String email, Account account, List<Booking> bookings) {
        super();
        this.name = name;
        this.email = email;
        this.account = account;
        this.bookings = bookings;
    }

    /**
     * 고객 이름을 반환합니다.
     * 
     * @return 고객 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 고객 이름을 설정합니다.
     * 
     * @param name 고객 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 고객 이메일을 반환합니다.
     * 
     * @return 고객 이메일
     */
    public String getEmail() {
        return email;
    }

    /**
     * 고객 이메일을 설정합니다.
     * 
     * @param email 고객 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 고객 계정 정보를 반환합니다.
     * 
     * @return 고객 계정 정보
     */
    public Account getAccount() {
        return account;
    }

    /**
     * 고객 계정 정보를 설정합니다.
     * 
     * @param account 고객 계정 정보
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 고객의 예약 목록을 반환합니다.
     * 
     * @return 고객 예약 목록
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * 고객의 예약 목록을 설정합니다.
     * 
     * @param bookings 고객 예약 목록
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * {@code Customer} 객체의 문자열 표현을 반환합니다.
     * 
     * @return 고객 정보를 문자열로 표현한 값
     */
    @Override
    public String toString() {
        return "Customer [name=" + name + ", email=" + email + ", account=" + account + ", bookings=" + bookings + "]";
    }
}
