package locomServer283;

import java.io.PrintWriter;

public class UserSendable {
	public String userName;
	public LocomLocation locomLocation;
	public InterestTags tags;
	
    public UserSendable(String userName, LocomLocation locomLocation, InterestTags tags, PrintWriter outStream) {
	this.userName = userName;
	this.locomLocation = locomLocation;
	this.tags = tags;
	}
    
    public UserSendable(User u){
    	this.userName = u.userName;
    	this.locomLocation = u.locomLocation;
    	this.tags = u.tags;
    }
}
