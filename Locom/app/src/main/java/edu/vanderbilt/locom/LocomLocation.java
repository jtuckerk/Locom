package edu.vanderbilt.locom;

public class LocomLocation {

	private double longitude, latitude;
	
	//constructor
	public LocomLocation(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	//updates long and lat of locomLocation
	public void update(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	//returns true if distance between this locomLocation and the passed
	// in locomLocation is less than Radius
	public Boolean inRange(LocomLocation locomLocation, double Radius){
		 return (this.getDistance(locomLocation) < Radius);
	}
	
	public float getDistance(LocomLocation locomLocation){

        float[] results = {0,0,0};
        android.location.Location.distanceBetween( latitude, longitude, locomLocation.latitude, locomLocation.longitude, results);
		
		return results[0];
	}

	
}
