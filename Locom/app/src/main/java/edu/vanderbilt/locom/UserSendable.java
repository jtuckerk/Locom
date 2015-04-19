package edu.vanderbilt.locom;

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

    public float distanceToBroadcast(Broadcast bcast){

        return location.getDistance(bcast.getLocation());
    }
}
