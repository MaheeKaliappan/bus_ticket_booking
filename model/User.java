package com.zoho.busticketbooking.model;

public class User {
	private Integer userId;
	private String  userName;
	private String  userContactNumber;
	private Integer userTicketCount;
	private Integer userTicketAmount;
	private String  userTicketBookingDate;
	private String userTicketBookingNumber;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserContactNumber() {
		return userContactNumber;
	}
	public void setUserContactNumber(String userContactNumber) {
		this.userContactNumber = userContactNumber;
	}
	public Integer getUserTicketCount() {
		return userTicketCount;
	}
	public void setUserTicketCount(Integer userTicketCount) {
		this.userTicketCount = userTicketCount;
	}
	public Integer getUserTicketAmount() {
		return userTicketAmount;
	}
	public void setUserTicketAmount(Integer userTicketAmount) {
		this.userTicketAmount = userTicketAmount;
	}
	public String getUserTicketBookingDate() {
		return userTicketBookingDate;
	}
	public void setUserTicketBookingDate(String userTicketBookingDate) {
		this.userTicketBookingDate = userTicketBookingDate;
	}
	public String getUserTicketBookingNumber() {
		return userTicketBookingNumber;
	}
	public void setUserTicketBookingNumber(String userTicketBookingNumber) {
		this.userTicketBookingNumber = userTicketBookingNumber;
	}

}
