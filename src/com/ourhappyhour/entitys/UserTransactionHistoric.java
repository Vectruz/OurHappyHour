package com.ourhappyhour.entitys;

import java.sql.Timestamp;

public class UserTransactionHistoric {
	
	private int id;
	private User user;
	private Float beforeTransaction;
	private Float transactionValue;
	private Float afterTransaction;
	private Timestamp date;
	
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
	public Float getBeforeTransaction() {
		return beforeTransaction;
	}
	public void setBeforeTransaction(Float beforeTransaction) {
		this.beforeTransaction = beforeTransaction;
	}
	public Float getTransactionValue() {
		return transactionValue;
	}
	public void setTransactionValue(Float transactionValue) {
		this.transactionValue = transactionValue;
	}
	public Float getAfterTransaction() {
		return afterTransaction;
	}
	public void setAfterTransaction(Float afterTransaction) {
		this.afterTransaction = afterTransaction;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

}
