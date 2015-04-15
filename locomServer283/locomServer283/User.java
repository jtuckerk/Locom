package locomServer283;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {

	public String userName;
	public Location location;
	public InterestTags tags;
	public PrintWriter outStream;
	//@@ maybe not public
	
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
    
    public Boolean inRange(Location location, double radius){
		
    	return this.location.inRange(location, radius);
    }
    
    public Boolean isInterested(String[] tags){
    	
    	//returns true if any tags match else returns false
    	return this.tags.hasInterests(tags);
    }
    public void send(String msg){
    	this.outStream.println(msg);
    	this.outStream.flush();
    }
}
