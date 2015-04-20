package edu.vanderbilt.locom;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Broadcast {

	private String title;
	private String message;
	private LocomLocation locomLocation;
	private Double radius;
	private static Date sentDate;
	private Date eventDate;
	private Date timeoutDate;
    private InterestTags tags;
	
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
