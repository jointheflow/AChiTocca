package org.achitocca.business.model;

import java.util.Date;

public class ScheduledDay {
	private Date dayDate;
	private short dayOfWeek;
	private String userId;
	
	public Date getDayDate() {
		return dayDate;
	}
	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}
	public short getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(short dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
