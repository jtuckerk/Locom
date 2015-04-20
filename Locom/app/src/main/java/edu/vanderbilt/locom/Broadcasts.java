package edu.vanderbilt.locom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Broadcasts {

	private List<Broadcast> broadcasts = new ArrayList<>();

	
	public Broadcasts(){
		
		//Broadcast welcomeBroadcast = new Broadcast("Welcome", "You are now connected", new Date(), new Date());
		//this.broadcasts.add(welcomeBroadcast);
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
