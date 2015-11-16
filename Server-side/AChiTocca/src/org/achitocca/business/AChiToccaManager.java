package org.achitocca.business;

import java.util.ArrayList;
import java.util.Date;

import org.achitocca.business.model.Group;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.TurnDefinition;
import org.achitocca.business.model.User;
import org.achitocca.dao.googledatastore.AChiToccaDAO;
import org.achitocca.dao.googledatastore.DAOException;

public class AChiToccaManager {
	//create a new turn for the group and persist to the datatsore
	public static Turn doCreateNewTurn(String userId, String groupId, String fbToken) {
		//check token against fbplatform
		
		
		//check if group exists
		
		//check if users belong to the group
		
		//get all users of the group
		
		//get turn definition for the group
		
		//get current turn
		
		
		//invoke turncreation
		
		//put new turn on the datastore (implies removing the previous)
		
		//return Turn
		
		return null;
	}
	
	/*Given a userid returns all group who user belongs*/
	public static ArrayList<Group> getGroupsOfUser(String externalUserId, String fbToken) throws DAOException {
		
		//TODO: check token vs user
		//invoke DAO
		return AChiToccaDAO.getGroupOfUser(externalUserId);
	}
	
	/*create a new group and return idkey of group*/
	public static Group doCreateGroup(String externalGroupId, String externalUserId, String name, String fbToken, TurnDefinition turnDef) throws DAOException{
		//TODO: check token vs externalUserId (admin)
		
		Group group = AChiToccaDAO.createGroup(externalGroupId, name, externalUserId);
		
		
		return group;
	} 
	
	/*Add users to group*/
	public static Group doAddUsersToGroup(String groupId, String externalUserId, ArrayList<String> users, String fbToken) throws DAOException {
		//TODO: check token vs externalUserId
		
		Group group = AChiToccaDAO.addUsersToGroup(groupId, externalUserId, users);
		
		return group;
		
	}
	/*get the next user scheduled for the group*/
	public static User aChiTocca(String groupId, String userId, String fbToken) {
		return null;
		
	}
	
	/*move ahead the turn pointer to the next user of the turn. If turn not exists create new turn.
	 * It is not possible to do the same call in the same day. If you need, user forceNexet=true*/
	public static void doNextUser(String groupId, String userId, String fbToken, Date date, boolean forceNext) {
		
		
	}
	
	/*Skip to the next user but add penalty to the skipped user. If turn not exists create new turn.
	 * It is not possible to do the same call in the same day. If you need, user forceNexet=true*/
	public static void doSkipUser(String groupId, String userId, String fbToken, Date date, boolean forceNext) {
		
		
	}
	
	/*shake the nexts remained user and return the new current user*/
	public static User shake(String groupId, String userId, String fbToken) {
		return null;
	}

}
