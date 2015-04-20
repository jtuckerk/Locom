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
    
    public Boolean isInterested(InterestTags tags, InterestTags userTags){
    	
    	//returns true if any tags match else returns false
    	System.out.println("inside is interested");
    	this.tags.printUserInterests();
    	return this.tags.hasInterests(tags, userTags);
    }
    public void send(String msg){
    	this.outStream.println(msg);
    	this.outStream.flush();
    }
    
    public UserSendable getUserSendable(){
		return new UserSendable(this);
    }
}


