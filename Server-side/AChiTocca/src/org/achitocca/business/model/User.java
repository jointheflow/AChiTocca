package org.achitocca.business.model;

public class User {
	private String userId;
	/* 1=means user will be inserted in the turn 1 times, negative value=means user will skip the turn n times when n is the negative,
	 * positive value= means user will do the turn n times more*/
	private int turnWeight;
	
	private String externalUserId;
	
	public String getUserId() {
		return userId;
	}
	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getTurnWeight() {
		return turnWeight;
	}
	public void setTurnWeight(int turnWeight) {
		this.turnWeight = turnWeight;
	}


	public String getExternalUserId() {
		return externalUserId;
	}


	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	} 
	
	

}
