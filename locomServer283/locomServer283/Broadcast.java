package locomServer283;

import java.util.Calendar;
import java.util.Date;


// A class to represent all information related to a broadcast
public class Broadcast {

	private String title;					//event title	
	private String message;					//event description info 
	private LocomLocation locomLocation;	//simple location of broadcast/event
	private Double radius;					//radius the broadcast should reach
	private static Date sentDate;			//date event is broadcast
	private Date eventDate;					//date event is scheduled
	private Date timeoutDate;				//date event gets removed from the server and from a users list
	private InterestTags tags;				//tags corresponding to the event
	
	public Broadcast(String title, String message, InterestTags tags, LocomLocation locomLocation, double radius, Date eventDate, Date sentDate, Date timeoutDate){
		this.title = title;
		this.message = message;
		this.locomLocation = locomLocation;
		this.radius = radius;
		this.sentDate = sentDate;
		this.eventDate = eventDate;
		this.timeoutDate = timeoutDate;
		this.tags = tags;
	}
	public Broadcast(String title, String message, InterestTags tags, LocomLocation locomLocation, double radius, Date eventDate, Date sentDate){
		this(title, message, tags, locomLocation, radius, eventDate, sentDate, defaultTimeout(sentDate));
	}
	
	//used in the alternate constructor to set a default timeout of 3 days from the sent date
	private static Date defaultTimeout(Date sendDate){
		
	    Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(sentDate); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 72); // adds one hour
	    
		return cal.getTime(); // returns new date object, 72 hours in the future
	}

	public Date getTimeout(){	
		return this.timeoutDate;
	}
	
	public LocomLocation getLocation(){
		return this.locomLocation;
	}
	public double getRadius(){
		return this.radius;
	}
	public InterestTags getTags(){
		return tags;
	}
}
