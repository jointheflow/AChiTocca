package org.achitocca.dto;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupDto {
	public String groupId="fake-groupId";
	public String externalGroupId="fake-externalGroupId";
	public ArrayList<String> usersId = new ArrayList<>(Arrays.asList("idUser1", "idUser2", "idUser3"));
	public ArrayList<String> turn = new ArrayList<>(Arrays.asList("idUser1", "idUser2", "idUser3"));
	public int nextUser =0;
	
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
	public ArrayList<String> getUsersId() {
		return usersId;
	}
	public void setUsersId(ArrayList<String> usersId) {
		this.usersId = usersId;
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
