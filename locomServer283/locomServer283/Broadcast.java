package locomServer283;

import java.util.Calendar;
import java.util.Date;

public class Broadcast {

	private String title;
	private String message;
	private static Date sentDate;
	private Date eventDate;
	private Date timeoutDate;
	
	public Broadcast(String title, String message, Date eventDate, Date sentDate, Date timeoutDate){
		
	}
	public Broadcast(String title, String message, Date eventDate, Date sentDate){
		this(title, message, eventDate, sentDate, defaultTimeout(sentDate));
	}
	private static Date defaultTimeout(Date sendDate){
		
	    Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(sentDate); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 72); // adds one hour
	    
		return cal.getTime(); // returns new date object, 72 hours in the future
	}

	public Date getTimeout(){
		
		return this.timeoutDate;
	}
}
