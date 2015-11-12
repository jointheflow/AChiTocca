package org.achitocca.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import java.util.logging.Logger;

import org.achitocca.business.model.ScheduledDay;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.TurnDefinition;
import org.achitocca.business.model.User;



public class TurnFactory {
	private static final Logger log = Logger.getLogger(TurnFactory.class.getName());
	
	/*Creates a turn and persists it. Removes old turn if exists*/
	public static Turn turnCreate(String groupId, Date startDate, ArrayList<User> users, TurnDefinition defTurn, int startDayIterator) throws ParseException {
		/*retrieves users and turn definition*/
		
		Turn turn= new Turn();
		ArrayList<ScheduledDay> sDays = new ArrayList<ScheduledDay>();
		
		Iterator<Short> dayItr = defTurn.getDaysOfWeek().iteratorFrom(startDayIterator);
		Iterator<User> usrItr = users.iterator();
		log.info("Turn creation start for group "+groupId+" users with size:  "+users.size()+" startDate:"+startDate);
		
		while (usrItr.hasNext()) {
			
			User currentUser = (User)usrItr.next();
			log.info("User:"+currentUser.getUserId());
			log.info("User weight:"+currentUser.getTurnWeight());
			
			int nextTurnOccurrence = currentUser.getTurnWeight();
			
			for (int i=0; i< nextTurnOccurrence; i++) {
				ScheduledDay sDay = new ScheduledDay();
				Short nextDay = (Short)(dayItr.next());
				log.info("Next day of week: "+nextDay);
				sDay.setDayOfWeek(nextDay);
				
				Date nextComputedDate = nextDateCompute(startDate, nextDay);
				sDay.setDayDate(nextComputedDate);
				//now, the start date became the last computedDate 
				startDate = nextComputedDate;
				
				sDay.setUserId(currentUser.getUserId());
								
				sDays.add(sDay);
				
			}
			
			//update user weight.
			//if nextOccurrence <= 0 increment by 1
			//if nextOccurrence > 0 set to 1 (the default)
			if (nextTurnOccurrence <=0) currentUser.setTurnWeight(nextTurnOccurrence ++);
			else currentUser.setTurnWeight(1);
			
			
		}
		turn.setGroupId(groupId);
		turn.setScheduledDays(sDays);
		String msgTurn= new String();
		SimpleDateFormat sdf = new SimpleDateFormat("(E dd/MM/yyyy)");
		for (int j=0; j<turn.getScheduledDays().size(); j++) {
			msgTurn = msgTurn + turn.getScheduledDays().get(j).getUserId()+"-"
								+turn.getScheduledDays().get(j).getDayOfWeek()+"-"
								+sdf.format(turn.getScheduledDays().get(j).getDayDate())+", ";
		}
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
	public static String aChiTocca(Date aDate, Turn aTurn) {
		
		//get the day of week of the date
		Calendar c = Calendar.getInstance();
		c.setTime(aDate);
		//get the dayOfWeek from currentDate
		int aDateDayOfWeek= c.get(Calendar.DAY_OF_WEEK);
		
		//iterate over scheduled day until a schedueled day with date and dayofweekis found
		Iterator<ScheduledDay> schItr = aTurn.getScheduledDays().iterator();
		while (schItr.hasNext()) {
			ScheduledDay sDay = (ScheduledDay)schItr.next();
			if ( (sDay.getDayOfWeek()==aDateDayOfWeek) &&
				 removeTime(sDay.getDayDate()).equals(removeTime(aDate))
				) return sDay.getUserId();
				 
		}
		
		return null;
		
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
