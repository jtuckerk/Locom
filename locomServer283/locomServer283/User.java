package locomServer283;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {

	private String userName;
	private Location location;
	private InterestTags tags;
	private PrintWriter outStream;
	
    public User(String userName, Location location, InterestTags tags, PrintWriter outStream) {
	this.userName = userName;
	this.location = location;
	this.tags = tags;
	this.outStream = outStream;
	
	System.out.println("User Created: " + this.getuserName());
    }
    
    public String getuserName(){
    	return this.userName;
    }
    
    public Boolean inRange(Location location, int radius){
		
    	return this.location.inRange(location, radius);
    }
    
    public Boolean isInterested(String[] tags){
    	
    	//returns true if any tags match else returns false
    	return this.tags.hasInterests(tags);
    }
}
