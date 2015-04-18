package edu.vanderbilt.locom;

public class Location {

	private double longitude, latitude;
	
	//constructor
	public Location(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	//updates long and lat of location
	public void update(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	//returns true if distance between this location and the passed
	// in location is less than Radius
	public Boolean inRange(Location location, double Radius){
		 return (this.getDistance(location) < Radius);
	}
	
	public double getDistance(Location location){
		double xSqrd = Math.pow((this.longitude - location.longitude), 2);
		double ySqrd = Math.pow((this.latitude - location.latitude), 2);
		
		return Math.sqrt(xSqrd+ySqrd);
	}
	
	
}
