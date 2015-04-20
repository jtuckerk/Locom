package locomServer283;

import java.io.PrintWriter;

public class User {

	public String userName;
	public LocomLocation locomLocation;
	public InterestTags tags;
	public PrintWriter outStream;
	//@@ maybe not public
	
    public User(String userName, LocomLocation locomLocation, InterestTags tags, PrintWriter outStream) {
	this.userName = userName;
	this.locomLocation = locomLocation;
	this.tags = tags;
	this.outStream = outStream;
    }
    
    public String getuserName(){
    	return this.userName;
    }
    
    public Boolean inRange(LocomLocation locomLocation, double radius){
		
    	return this.locomLocation.inRange(locomLocation, radius);
    }
    
    public Boolean isInterested(InterestTags tags){
    	
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


