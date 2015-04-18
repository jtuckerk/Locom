package edu.vanderbilt.locom;

import java.io.PrintWriter;

public class UserSendable {
	public String userName;
	public Location location;
	public InterestTags tags;
	
    public UserSendable(String userName, Location location, InterestTags tags) {
	this.userName = userName;
	this.location = location;
	this.tags = tags;
	}
    
    public UserSendable(User u){
    	this.userName = u.userName;
    	this.location = u.location;
    	this.tags = u.tags;
    }
}
