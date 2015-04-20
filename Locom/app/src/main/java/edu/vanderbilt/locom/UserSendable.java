package edu.vanderbilt.locom;


//User Object without the Printwriter field. This should be the base class and
// the user with the output stream writer should be a child class of that. but oh well.
// maybe next time.
// Gson cannot parse or stringify the printWriter object so a regular User object
// cannot be sent.
public class UserSendable {
	public String userName;
	public LocomLocation locomLocation;
	public InterestTags tags;
	
    public UserSendable(String userName, LocomLocation locomLocation, InterestTags tags) {
	this.userName = userName;
	this.locomLocation = locomLocation;
	this.tags = tags;
	}

    //creates a sendable object from a non sendable User
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
