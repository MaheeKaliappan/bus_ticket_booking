package com.zoho.busticketbooking.model;

public class Bus {
	
	private Integer busId;
	private String  busName;
	private String  busStartFrom;
	private String  busReachTo;
	private Integer busSeatCount;
	private Integer busTicketRate;
	private String  busTiming;
	private String  busDate;
	private String busTicketStatus;
	
	
	public String getBusTicketStatus() {
		return busTicketStatus;
	}
	public void setBusTicketStatus(String busTicketStatus) {
		this.busTicketStatus = busTicketStatus;
	}
	public Integer getBusId() {
		return busId;
	}
	public void setBusId(Integer busId) {
		this.busId = busId;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getBusStartFrom() {
		return busStartFrom;
	}
	public void setBusStartFrom(String busStartFrom) {
		this.busStartFrom = busStartFrom;
	}
	public String getBusReachTo() {
		return busReachTo;
	}
	public void setBusReachTo(String busReachTo) {
		this.busReachTo = busReachTo;
	}
	public Integer getBusSeatCount() {
		return busSeatCount;
	}
	public void setBusSeatCount(Integer busSeatCount) {
		this.busSeatCount = busSeatCount;
	}
	public Integer getBusTicketRate() {
		return busTicketRate;
	}
	public void setBusTicketRate(Integer busTicketRate) {
		this.busTicketRate = busTicketRate;
	}
	public String getBusTiming() {
		return busTiming;
	}
	public void setBusTiming(String busTiming) {
		this.busTiming = busTiming;
	}
	public String getBusDate() {
		return busDate;
	}
	public void setBusDate(String busDate) {
		this.busDate = busDate;
	}

}
