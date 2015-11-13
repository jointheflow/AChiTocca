package org.achitocca.business.model;

import java.util.ArrayList;

public class Turn {
	private String groupId;
	private ArrayList<ScheduledDay> scheduledDays;
	private ArrayList<String> scheduledUsers;
	private int nextUser;
	
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
	public ArrayList<String> getScheduledUsers() {
		return scheduledUsers;
	}
	public void setScheduledUsers(ArrayList<String> scheduledUsers) {
		this.scheduledUsers = scheduledUsers;
	}
	public int getNextUser() {
		return nextUser;
	}
	public void setNextUser(int nextUser) {
		this.nextUser = nextUser;
	}
	
}
