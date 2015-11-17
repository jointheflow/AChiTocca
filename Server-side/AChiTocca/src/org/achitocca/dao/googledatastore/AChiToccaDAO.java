package org.achitocca.dao.googledatastore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.achitocca.business.model.Group;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.User;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class AChiToccaDAO {
	private static final Logger log = Logger.getLogger(AChiToccaDAO.class.getName());
	
	/*create a new group  the group*/
	public static Group createGroup(String externalGroupId, String name, String externalAdminId) throws DAOException {
		Key groupKey=null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			//create group
			Entity e_group = new Entity("Group");
			e_group.setProperty("externalGroupId", externalGroupId);
			e_group.setProperty("name", name);
			e_group.setProperty("externalAdminId", externalAdminId);
			e_group.setProperty("nextUser", 0);
			e_group.setProperty("turn", new ArrayList<String>(Arrays.asList(externalAdminId)));
			groupKey = datastore.put(e_group);
			
			//create the admin user and as first users
			Entity e_user = new Entity("User", groupKey);
			e_user.setProperty("externalUserId", externalAdminId);
			e_user.setProperty("turnWeight", 1);
			Key userKey= datastore.put(e_user);
			
			Group group = new Group();
			User admin = new User();
			admin.setExternalUserId(externalAdminId);
			admin.setUserId(KeyFactory.keyToString(userKey));
			admin.setTurnWeight(1);
			
			group.addUser(admin);
			group.setExternalGroupId(externalGroupId);
			group.setGroupId(KeyFactory.keyToString(groupKey));
			group.setAdmin(admin);
			group.setTurn((ArrayList<String>)e_group.getProperty("turn"));
			
			tx.commit();
			return group;
			
		}catch (IllegalArgumentException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
		   
	}   
		
	
	/*get groups of user*/
	public static ArrayList<Group> getGroupOfUser(String externalUserId) throws DAOException {
		ArrayList<Group> groupResult = new ArrayList<Group>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Transaction tx = datastore.beginTransaction();
		try {
			
			//retrieves all user with externalUserId
			Filter propertyFilter =
					  new FilterPredicate("externalUserId",
					                      FilterOperator.EQUAL,
					                      externalUserId);
			
			Query q = new Query("User").setFilter(propertyFilter).setKeysOnly();
			PreparedQuery pq = datastore.prepare(q);
			
			for (Entity entity : pq.asIterable()) {
				Key userKey = entity.getKey();
				log.info("Found key "+ entity.getKey().toString());
				//for each user get the parent id (groupid).
				Key groupKey = userKey.getParent();
				
							
				//retrieve group and add to the result
				Group group = getGroup(groupKey);
				groupResult.add(group);
				
			}
			
				
			//tx.commit();
			return groupResult;
						
		}catch (IllegalArgumentException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    /*if (tx.isActive()) {
		        tx.rollback();
		    }*/
		    
		}
		
		
	}
	/*retrieve a group basing on key*/
	private static Group getGroup(Key groupKey) throws DAOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Transaction tx = datastore.beginTransaction();		
		//get group
		try {
			Entity e_group = datastore.get(groupKey);
			Group group = new Group();
								
			//get users of group
			Query ancestorUserQuery = new Query("User").setAncestor(groupKey);
			List<Entity> results = datastore.prepare(ancestorUserQuery).asList(FetchOptions.Builder.withDefaults());
			for (Entity entity : results) {
				log.info("Get user "+ entity.getKey().toString());
				User u = new User();
				u.setExternalUserId((String)entity.getProperty("externalUserId"));
				u.setTurnWeight(castToInteger(entity.getProperty("turnWeight")));
				u.setUserId(KeyFactory.keyToString(entity.getKey()));
				group.addUser(u);
				
			}
			
			
			
			ArrayList<String> turn = (ArrayList<String>) e_group.getProperty("turn");
			
			User admin = new User();
			admin.setExternalUserId((String) e_group.getProperty("externalAdminId"));
			group.setAdmin(admin);
			group.setExternalGroupId((String) e_group.getProperty("externalGroupId"));
			group.setGroupId(KeyFactory.keyToString(e_group.getKey()));
			group.setTurn(turn);
			
			
			
			return group;
		
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException (e.getMessage());
		}
	}
	
	/*add a list of users to a group*/
	public static Group addUsersToGroup(String groupId, String externalUserId, ArrayList<String> users) throws DAOException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			
			Entity e_group = datastore.get(KeyFactory.stringToKey(groupId));
			
			Key groupKey = e_group.getKey();
			log.info("Found key "+ e_group.getKey().toString());
			
			
			//add users
			Group g= getGroup(groupKey);
			
			ArrayList<String> usersInTurn = g.getTurn();
			
			//check if externalUserId already belong to the group
			if (!g.userBelongToGroup(externalUserId)) throw new DAOException("User "+externalUserId+" cannot add users because he does not belong to group "+groupId);
			
			Iterator<String> usrItr = users.iterator();
			while (usrItr.hasNext()) {
				String usrStr = usrItr.next();
				
				//add user to the users list and also to the current turn only if not already exsists
				if (!g.userBelongToGroup(usrStr)) {
					Entity e_user = new Entity("User", groupKey);
					e_user.setProperty("externalUserId", usrStr);
					e_user.setProperty("turnWeight", 1);
					Key userKey = datastore.put(e_user);
					User user = new User ();
					user.setExternalUserId(usrStr);
					user.setUserId(KeyFactory.keyToString(userKey));
					user.setTurnWeight(1);
					//add users to turn
					usersInTurn.add(usrStr);
					
					g.addUser(user);
				}
			}
			
			//update turn adding users
			e_group.setProperty("turn", usersInTurn);
			datastore.put(e_group);
			
			tx.commit();
			return g;
						
		}catch (IllegalArgumentException | EntityNotFoundException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		    
		}
		
		
	}
	
	
	/*Use this method to manage Integer because are thared as Long*/
	private static Integer castToInteger(Object obj_val) {
		if (obj_val.getClass().getSimpleName().equals("Long"))
			return (Integer)(new Long ((Long)obj_val)).intValue();
		else
			return (Integer) obj_val;
					
		
	}
	
	/*Given a groupid get the turn model*/
	public static Turn getTurn(String groupId) throws DAOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Transaction tx = datastore.beginTransaction();
		try {
			
			Entity e_group = datastore.get(KeyFactory.stringToKey(groupId));
			ArrayList<String> usersId = (ArrayList<String>) e_group.getProperty("turn");
			
			Turn turn = new Turn();
			
			turn.setGroupId(KeyFactory.keyToString(e_group.getKey()));
			turn.setNextUser(castToInteger(e_group.getProperty("nextUser")));
			turn.setScheduledUsers(usersId);
			
			
			ArrayList<User> users = new ArrayList<User>();
			//get every user references from group and set attributes
			Iterator<String> usrItr = usersId.iterator();
			while (usrItr.hasNext()) {
				String extUserId = usrItr.next();
			
				//retrieves the user with externalUserId
				Filter propertyFilter =
						  new FilterPredicate("externalUserId",
						                      FilterOperator.EQUAL,
						                      extUserId);
				
				Query q = new Query("User").setFilter(propertyFilter).setAncestor(KeyFactory.stringToKey(groupId));
				PreparedQuery pq = datastore.prepare(q);
				Entity e_user = pq.asSingleEntity();
				//create user model
				User user = new User();
				//set attributes from db
				user.setExternalUserId(extUserId);
				user.setUserId(KeyFactory.keyToString(e_user.getKey()));
				user.setTurnWeight(castToInteger(e_user.getProperty("turnWeight")));
				//add user
				users.add(user);
			}
												
			turn.setUsers(users);
			
			
			log.info("Found turn for group"+ turn.getGroupId());
				
			//tx.commit();
			return turn;
			
			
				
			
						
		}catch (IllegalArgumentException | EntityNotFoundException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    /*if (tx.isActive()) {
		        tx.rollback();
		    }*/
		    
		}
		
	}
	
	/*Update turn*/
	public static void updateTurn(Turn turn) throws DAOException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			
			//get group reference and set turn attributes
			Entity e_group = datastore.get(KeyFactory.stringToKey(turn.getGroupId()));
			e_group.setProperty("nextUser", turn.getNextUser());
			e_group.setProperty("turn", turn.getScheduledUsers());
			datastore.put(e_group);
			log.info("group updated:"+turn.getGroupId());
			
			//get every user references and set attributes
			Iterator<User> usrItr = turn.getUsers().iterator();
			while (usrItr.hasNext()) {
				User u = usrItr.next();
				log.info("Get user "+ u.getExternalUserId());
				Entity e_user = datastore.get(KeyFactory.stringToKey(u.getUserId()));
				e_user.setProperty("turnWeight", u.getTurnWeight());
				datastore.put(e_user);
				log.info("User updated:"+u.getExternalUserId());
			}
			
						
			tx.commit();
			
			
		}catch (IllegalArgumentException | EntityNotFoundException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
		   
		
		
	}
}
