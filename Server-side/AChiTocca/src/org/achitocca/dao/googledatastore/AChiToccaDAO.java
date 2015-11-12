package org.achitocca.dao.googledatastore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.achitocca.business.model.ScheduledDay;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.TurnDefinition;
import org.achitocca.business.model.User;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class AChiToccaDAO {
	private static final Logger log = Logger.getLogger(AChiToccaDAO.class.getName());
	
	/*Add a new Turn. If a turn already exists new turn overwrite the old*/
	public static void addOrUpdateTurn(String groupId, Turn aTurn) throws DAOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			
			//remove all (old) scheduled day for the group (the turn)
			
			Query ancestorQuery = new Query("ScheduledDay").setAncestor(KeyFactory.stringToKey(groupId)).setKeysOnly();
			List<Entity> results = datastore.prepare(ancestorQuery).asList(FetchOptions.Builder.withDefaults());
			for (Entity entity : results) {
				log.info("Delete "+ entity.getKey().toString());
				datastore.delete(entity.getKey());
			}
			
			
			//insert all new scheduled day of turn
			Iterator<ScheduledDay> schItr = aTurn.getScheduledDays().iterator();
			while (schItr.hasNext()) {
				ScheduledDay sDay = schItr.next();
				
				Entity e_scheduledDay = new Entity("ScheduledDay",KeyFactory.stringToKey(groupId));
				e_scheduledDay.setProperty("dayOfWeek",sDay.getDayOfWeek());
				e_scheduledDay.setProperty("dayDate", sDay.getDayDate());
				e_scheduledDay.setProperty("userId", sDay.getUserId());
				
				datastore.put(e_scheduledDay);
			}
			
			
			tx.commit();
						
		}catch (IllegalArgumentException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
			
	}    	
	
	/*create a new group inserting also users and turn definition for the group*/
	public static void addGroup(String externalGroupId, ArrayList<User> users, TurnDefinition turnDef) throws DAOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			//create group
			Entity e_group = new Entity("Group");
			e_group.setProperty("externalGroupId", externalGroupId);
			e_group.setProperty("daysOfWeek", turnDef.getDaysOfWeek());
			Key groupKey = datastore.put(e_group);
			
			//create users for the group
			Iterator<User> usrItr = users.iterator();
			while (usrItr.hasNext()) {
				User aUser = (User) usrItr.next();
				
				Entity e_user = new Entity("User", groupKey);
				
				e_user.setProperty("externalUserId", aUser.getUserId());
				//set the weight for the next turn 1=deafult means 1 turn for the user
				e_user.setProperty("turnWeight",1);
				
				datastore.put(e_user);
				
			}
					
			tx.commit();
						
		}catch (IllegalArgumentException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		}
		   
	}   
		
	
}
