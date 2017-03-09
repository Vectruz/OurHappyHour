package com.ourhappyhour.entitys;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Event {
	
	private Integer id;
	private String name;
	private String description;
	private String location;
	private Timestamp time;
	private Boolean disabled;
	private Float totalValue;
	private ArrayList<EventUser> eventUsers;
	
	public ArrayList<EventUser> getEventUsers() {
		return eventUsers;
	}
	public void setEventUsers(ArrayList<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}
	
	public Float getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(Float totalValue) {
		this.totalValue = totalValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

}
