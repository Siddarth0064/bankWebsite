package com.bloombloom.jdbc.dto;

public class CustomerBankDeatils {
    
	private int accNumber;
	private int balance;
	public CustomerBankDeatils(int accNumber, int balance) {
		super();
		this.accNumber = accNumber;
		this.balance = balance;
	}
	public int getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		System.out.println("set balance get called by withdraw endpoint");
		this.balance = balance;
	}
	
	@Override
	public String toString() {// TODO Auto-generated method stub
		return balance+" "+accNumber;
	}
	
	
	
}
