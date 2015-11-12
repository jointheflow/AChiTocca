package org.achitocca.business.model;

import java.util.ArrayList;

public class Turn {
	private String groupId;
	private ArrayList<ScheduledDay> scheduledDays;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ArrayList<ScheduledDay> getScheduledDays() {
		return scheduledDays;
	}
	public void setScheduledDays(ArrayList<ScheduledDay> scheduledDays) {
		this.scheduledDays = scheduledDays;
	}
}
