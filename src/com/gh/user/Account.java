package com.gh.user;

/**
 * {@code Account} 클래스는 사용자 계정 정보를 나타냅니다.
 * <p>
 * 사용자 이름과 계좌 잔액 정보를 포함하며, 예약 시스템에서 고객의 계정 정보를 관리하는 데 사용됩니다.
 * </p>
 * 
 * @author YangJunYong
 */
public class Account {

    /**
     * 사용자 이름
     */
    private String username;

    /**
     * 계좌 잔액
     */
    private String balance;

    /**
     * 기본 생성자입니다.
     * <p>객체를 초기값 없이 생성할 때 사용됩니다.</p>
     */
    public Account() {
        super();
    }

    /**
     * 사용자 이름과 계좌 잔액을 초기화하여 {@code Account} 객체를 생성합니다.
     * 
     * @param username 사용자 이름
     * @param balance 계좌 잔액
     */
    public Account(String username, String balance) {
        super();
        this.username = username;
        this.balance = balance;
    }

    /**
     * 사용자 이름을 반환합니다.
     * 
     * @return 사용자 이름
     */
    public String getUsername() {
        return username;
    }

    /**
     * 계좌 잔액을 반환합니다.
     * 
     * @return 계좌 잔액
     */
    public String getBalance() {
        return balance;
    }

    /**
     * {@code Account} 객체의 문자열 표현을 반환합니다.
     * 
     * @return 사용자 이름과 잔액 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return "Account [username=" + username + ", balance=" + balance + "]";
    }
}
