package org.achitocca.business.model;

import java.util.ArrayList;

public class Group {
	private String groupId;
	private String externalGroupId;
	private ArrayList<User> users;
	private User admin;
	private TurnDefinition turnDef;
	private Turn currentTurn;
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
	public TurnDefinition getTurnDef() {
		return turnDef;
	}
	public void setTurnDef(TurnDefinition turnDef) {
		this.turnDef = turnDef;
	}
	public Turn getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(Turn currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	
	
}
