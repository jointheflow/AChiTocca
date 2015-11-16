package org.achitocca.dto;

import java.util.ArrayList;


public class GroupDto {
	public String groupId;
	public String externalGroupId;
	public ArrayList<String> users = new ArrayList<String>();
	public ArrayList<String> turn = new ArrayList<String>();
	public int nextUser =0;
	public String externalAdminId;
	
	
	public String getExternalAdminId() {
		return externalAdminId;
	}
	public void setExternalAdminId(String externalAdminId) {
		this.externalAdminId = externalAdminId;
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
	public ArrayList<String> getUsers() {
		return users;
	}
	public void setUsersId(ArrayList<String> usersId) {
		this.users = usersId;
	}
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
	
	
}
