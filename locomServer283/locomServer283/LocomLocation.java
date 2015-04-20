package locomServer283;

//Simple location abstraction for longitude and latitude
//provides a function to find the distance between locations
//
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
	
	public double getDistance(LocomLocation loc){
		 	double result;
		 	
	        result = distance( latitude, loc.latitude, longitude,loc.longitude, 0.0, 0.0);
			System.out.println("distance between (" + latitude +"," + longitude +")and("+ loc.latitude +"," + loc.longitude+ ") "+  " Calculated as: " + result);
			return result;
	}
	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 */
	private double distance(double lat1, double lat2, double lon1, double lon2,
	        double el1, double el2) {

	    final int R = 6371; // Radius of the earth in km

	    Double latDistance = deg2rad(lat2 - lat1);
	    Double lonDistance = deg2rad(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;
	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    return Math.sqrt(distance);
	}

	private double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}
	
}
