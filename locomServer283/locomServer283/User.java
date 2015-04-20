package locomServer283;

import java.io.PrintWriter;

//user object to hold user related info and a printWriter to allow 
// all usersthreads to write to all other user's sockets
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
    
    //determines if the user's self is within range of a broadcast
    // this will be used to determine if the user should receive a broadcast
    public Boolean inRange(LocomLocation locomLocation, double radius){
		
    	return this.locomLocation.inRange(locomLocation, radius);
    }
    
    //The second determining factor for whether or not a user 
    //will receive a broadcasts are if there are any matching interests
    public Boolean isInterested(InterestTags tags){
    	//returns true if any tags match else returns false
    	return this.tags.hasInterests(tags);
    }
    //method to send a string to a user's socket
    public void send(String msg){
    	this.outStream.println(msg);
    	this.outStream.flush();
    }
    
    //GSON is not able to parse a Printwriter object and that information
    //the UserSendable object is 'parsable' and 'stringifyable'
    public UserSendable getUserSendable(){
		return new UserSendable(this);
    }
}


