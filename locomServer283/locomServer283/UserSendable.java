package locomServer283;

import java.io.PrintWriter;

public class UserSendable {
	public String userName;
	public Location location;
	public InterestTags tags;
	public PrintWriter outStream;
	//@@ maybe not public
	
    public UserSendable(String userName, Location location, InterestTags tags, PrintWriter outStream) {
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
