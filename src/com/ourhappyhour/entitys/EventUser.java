package com.ourhappyhour.entitys;

public class EventUser {
	
	private int id;
	private User user;
	private Event event;
	private Boolean confirmed;
	private Boolean customPayment;
	private Float customPaymentValue;
	private Float paid;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Float getPaid() {
		return paid;
	}
	public void setPaid(Float paid) {
		this.paid = paid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public Boolean getCustomPayment() {
		return customPayment;
	}
	public void setCustomPayment(Boolean customPayment) {
		this.customPayment = customPayment;
	}
	public Float getCustomPaymentValue() {
		return customPaymentValue;
	}
	public void setCustomPaymentValue(Float customPaymentValue) {
		this.customPaymentValue = customPaymentValue;
	}

}
