package edu.vanderbilt.locom;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        this.tags = tags;
		this.locomLocation = locomLocation;
		this.radius = radius;
		this.sentDate = sentDate;
		this.eventDate = eventDate;
		this.timeoutDate = timeoutDate;
	}
	public Broadcast(String title, String message, InterestTags tags, LocomLocation locomLocation, double radius, Date eventDate, Date sentDate){
		this(title, message, tags, locomLocation, radius, eventDate, sentDate, defaultTimeout(sentDate));
	}

    //used in the alternate constructor to set a default timeout of 3 days from the sent date
	private static Date defaultTimeout(Date sentDate){
		Calendar cal = new GregorianCalendar();
	    //Calendar cal = Calendar.getInstance(); // creates calendar

        System.out.println(sentDate.toString());
        cal.setTime(sentDate); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 72); // adds one hour
	    
		return cal.getTime(); // returns new date object, 72 hours in the future
	}

	public Date getTimeout(){	
		return this.timeoutDate;
	}
	
	public LocomLocation getLocomLocation(){
		return this.locomLocation;
	}
	public double getRadius(){
		return this.radius;
	}
    public String getTitle(){ return title; }
    public String getMessageBody(){ return message;}
    public Date getEventDate(){return eventDate; }
    public InterestTags getTags(){ return tags;}
}
