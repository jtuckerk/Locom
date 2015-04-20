package edu.vanderbilt.locom;

import android.nfc.Tag;

import java.io.PrintWriter;

//user object to hold user related info and a printWriter to allow
// all usersthreads to write to all other user's sockets
//** not really used on the front end.
public class User {

	public String userName;
	public LocomLocation locomLocation;
	public InterestTags tags;
	public PrintWriter outStream;
	
    public User(String userName, LocomLocation locomLocation, InterestTags tags, PrintWriter outStream) {
	this.userName = userName;
	this.locomLocation = locomLocation;
	this.tags = tags;
	this.outStream = outStream;
	
	System.out.println("User Created: " + this.getuserName());
    }
    
    public String getuserName(){
    	return this.userName;
    }
    
    public Boolean inRange(LocomLocation locomLocation, double radius){
		
    	return this.locomLocation.inRange(locomLocation, radius);
    }
    
    public Boolean isInterested(String[] tags){
    	
    	//returns true if any tags match else returns false
    	return this.tags.hasInterests(tags);
    }
    public void send(String msg){
    	this.outStream.println(msg);
    	this.outStream.flush();
    }
    
    public UserSendable getUserSendable(){
		return new UserSendable(this);
    }
}


