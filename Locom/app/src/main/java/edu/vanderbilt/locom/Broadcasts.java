package edu.vanderbilt.locom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//object to be held by user  to contain all broadcasts
//received that have not timed out.This will be used to populate the users list on the
// home screen
public class Broadcasts {

	private List<Broadcast> broadcasts = new ArrayList<>();

	
	public Broadcasts(){
		Broadcast welcomeBroadcast = new Broadcast("Welcome", "You are now connected",
                new InterestTags(new String[]{}),
                new LocomLocation(0.0, 0.0),
                50000.0,
                new Date(), new Date());
		this.broadcasts.add(welcomeBroadcast);
	}
	
	public void add(Broadcast broadcast){
		this.broadcasts.add(broadcast);
	}
	private void remove(Broadcast broadcast){
		this.broadcasts.remove(broadcast);
	}
	
	//times out all broadcasts in set
	public void timeout(){
		Date now = new Date();
		for (Broadcast b : this.broadcasts){
			if (b.getTimeout().getTime() < now.getTime()){
				this.remove(b);
			}
		}
	}
    public List<Broadcast> getList(){

        return broadcasts;
    }
	
}
