package org.achitocca.business.model;

import java.util.ArrayList;

public class Turn {
	private String groupId;
	private ArrayList<User> users;
	private ArrayList<String> scheduledUsers;
	private int nextUser;
	
	
	
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
