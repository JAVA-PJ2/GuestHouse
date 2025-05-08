package com.gh.user;

/**
 * Account 클래스는 사용자 계정 정보를 나타냅니다.
 * 사용자 이름(username)과 잔액(balance)을 포함합니다.
 * 
 * @author YangJunYong
 */
public class Account {
	private String username;  // 사용자 이름
	private String balance;   // 계좌 잔액

	/**
	 * 기본 생성자입니다.
	 * 객체를 초기값 없이 생성할 때 사용됩니다.
	 */
	public Account() {
		super();
	}

	/**
	 * 사용자 이름과 잔액을 초기화하는 생성자입니다.
	 */
	public Account(String username, String balance) {
		super();
		this.username = username;
		this.balance = balance;
	}

	/**
	 * 사용자 이름을 반환합니다.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 계좌 잔액을 반환합니다.
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * 객체의 문자열 표현을 반환합니다.
	 */
	@Override
	public String toString() {
		return "Account [username=" + username + ", balance=" + balance + "]";
	}
}
