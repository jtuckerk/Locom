package edu.vanderbilt.locom;

public class UserSendable {
	public String userName;
	public LocomLocation locomLocation;
	public InterestTags tags;
	
    public UserSendable(String userName, LocomLocation locomLocation, InterestTags tags) {
	this.userName = userName;
	this.locomLocation = locomLocation;
	this.tags = tags;
	}
    
    public UserSendable(User u){
    	this.userName = u.userName;
    	this.locomLocation = u.locomLocation;
    	this.tags = u.tags;
    }

    public float distanceToBroadcast(Broadcast bcast){

        return locomLocation.getDistance(bcast.getLocomLocation());
    }

    public void setTags(InterestTags newtags){
        this.tags = newtags;
    }
}
