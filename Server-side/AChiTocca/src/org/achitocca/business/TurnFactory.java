package org.achitocca.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import java.util.logging.Logger;

import org.achitocca.business.model.ScheduledDay;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.User;



public class TurnFactory {
	private static final Logger log = Logger.getLogger(TurnFactory.class.getName());
	
	/*Creates a turn and persists it. Removes old turn if exists*/
	public static Turn turnCreate(String groupId, ArrayList<User> users) throws ParseException {
		/*retrieves users and turn definition*/
		
		Turn turn= new Turn();
		ArrayList<String> sUsers = new ArrayList<String>();
		
		
		Iterator<User> usrItr = users.iterator();
		log.info("Turn creation start for group "+groupId+" users with size:  "+users.size());
		
		while (usrItr.hasNext()) {
			
			User currentUser = (User)usrItr.next();
			log.info("User:"+currentUser.getExternalUserId());
			log.info("User weight:"+currentUser.getTurnWeight());
			
			int nextTurnOccurrence = currentUser.getTurnWeight();
			
			for (int i=0; i< nextTurnOccurrence; i++) {
								
				sUsers.add(currentUser.getExternalUserId());
				
			}
			
			//update user weight.
			//if nextOccurrence <= 0 increment by 1
			//if nextOccurrence > 0 set to 1 (the default)
			if (nextTurnOccurrence <=0) currentUser.setTurnWeight(nextTurnOccurrence ++);
			else currentUser.setTurnWeight(1);
			
			
		}
		turn.setGroupId(groupId);
		turn.setNextUser(0);
		turn.setScheduledUsers(sUsers);
		String msgTurn= new String();
		
		for (int j=0; j<turn.getScheduledUsers().size(); j++) {
			msgTurn = msgTurn + turn.getScheduledUsers().get(j)+", ";
		}
		
		msgTurn = msgTurn + "nextUser is:"+ turn.getNextUser();
		
		log.info("Turn created for group "+groupId+" as: " + msgTurn);
		log.info("Turn creation end");
		return turn;
	}
	
	/*Skips the current user respect skipDate. Returns next user. 
	 *Adds penalty +1 to skipped user, Add bonus -1 to next user.*/
	public static String userSkip(String groupId, Date skipDate) {
		return null;
		
	}
	/*Skips the current date and rebuilds the turn (shifting ahead) */
	public static void daySkip(String groupId, Date skipDate) {
		
		
	}
	/*Return the users id who is in turn for the aDate or null if not exixst*/
	public static String aChiTocca(Turn aTurn) {
		if (aTurn.getScheduledUsers()!=null)				
			return aTurn.getScheduledUsers().get(aTurn.getNextUser());
		else
			return null;
		
	}
	
	/*Move ahead to the next user*/
	public static Turn doNext(Turn aTurn, ArrayList<User> users) throws ParseException {
		int nextUser = aTurn.getNextUser();
		//if nextuser index can increment (not reach the last element)
		//increment the index and return the same turn
		if (aTurn.getNextUser() < aTurn.getScheduledUsers().size()-1) {
			nextUser++;
			aTurn.setNextUser(nextUser);
			return aTurn;
		
		//create a new turn
		}else {
			return turnCreate(aTurn.getGroupId(), users);
			
		}
		
	}
	
	
	private static Date nextDateCompute(Date currentDate, short dayOfWeek) throws ParseException {
		
		Date nextDate = new Date();
	
		
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		
		//get the dayOfWeek from currentDate
		int currentDayOfWeek= c.get(Calendar.DAY_OF_WEEK);
		
		
		if (dayOfWeek >= currentDayOfWeek) {
			c.add(Calendar.DATE, (dayOfWeek-currentDayOfWeek) );  // number of days to add
				
		}else {
			
			c.add(Calendar.DATE, (7-(currentDayOfWeek-dayOfWeek)));
		}
		
		//nextDate = sdf.format(c.getTime());  // dt is now the new date
		
		nextDate = c.getTime();
		
		return nextDate;
		
		
		
	}
	
	private static Date removeTime(Date date) {    
	    Calendar cal = Calendar.getInstance();  
	    cal.setTime(date);  
	    cal.set(Calendar.HOUR_OF_DAY, 0);  
	    cal.set(Calendar.MINUTE, 0);  
	    cal.set(Calendar.SECOND, 0);  
	    cal.set(Calendar.MILLISECOND, 0);  
	    return cal.getTime(); 
	}

}
