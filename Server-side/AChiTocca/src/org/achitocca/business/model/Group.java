package org.achitocca.business.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Group {
	private String groupId;
	private String externalGroupId;
	private User admin;
	private ArrayList<User> users = new ArrayList<User>();
	
	private ArrayList<String> turn = new ArrayList<String>();
	private int nextUser = 0;
	
	
	
	public ArrayList<String> getTurn() {
		return turn;
	}


	public void setTurn(ArrayList<String> turn) {
		this.turn = turn;
	}


	public int getNextUser() {
		return nextUser;
	}


	public void setNextUser(int nextUser) {
		this.nextUser = nextUser;
	}


	//check if external user belongs to the group
	public boolean userBelongToGroup(String externalUserId) {
		Iterator<User> usrItr = users.iterator();
		while(usrItr.hasNext()) {
			if (usrItr.next().getExternalUserId().equals(externalUserId)) return true;
			
		}
		return false;
		
	}
	
	
	//add a user only if he is not a member
	public void addUser(User u) {
		
		if (!userBelongToGroup(u.getExternalUserId())) {
			users.add(u);
		}
			
		
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getExternalGroupId() {
		return externalGroupId;
	}
	public void setExternalGroupId(String externalGroupId) {
		this.externalGroupId = externalGroupId;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	
	
	
}
