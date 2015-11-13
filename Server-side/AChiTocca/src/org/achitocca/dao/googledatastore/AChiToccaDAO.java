package org.achitocca.dao.googledatastore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.achitocca.business.model.Group;
import org.achitocca.business.model.ScheduledDay;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.TurnDefinition;
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
	
	/*create a new group  the group*/
	public static void addGroup(String externalGroupId, String name, String externalAdminId) throws DAOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
		try {
			//create group
			Entity e_group = new Entity("Group");
			e_group.setProperty("externalGroupId", externalGroupId);
			e_group.setProperty("name", name);
			e_group.setProperty("externalAdminId", externalAdminId);
			datastore.put(e_group);
			
								
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
		
	
	/*get groups of user*/
	public static ArrayList<Group> getGroupOfUser(String externalUserId) throws DAOException {
		ArrayList<Group> groupResult = new ArrayList<Group>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastore.beginTransaction();
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
				Group group = retrieveGroup(groupKey, datastore);
				groupResult.add(group);
				
			}
			
				
			tx.commit();
			return groupResult;
						
		}catch (IllegalArgumentException e) {
			log.info(e.getMessage());
			throw new DAOException(e.getMessage());
			
		
		} finally {
		    if (tx.isActive()) {
		        tx.rollback();
		    }
		    
		}
		
		
	}
	/*retrieve a group basing on key*/
	private static Group retrieveGroup(Key groupKey, DatastoreService datastore) {
		//TODO
		return null;
	}
}
