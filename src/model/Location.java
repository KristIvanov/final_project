package model;

public class Location {
	
	
	private long location_id;
	

	private double lattitude;
	private double longtitude;
	
	
	public Location(double lattitude, double longtitude) {
		this.lattitude = lattitude;
		this.longtitude = longtitude;
	}

	public long getLocation_id() {
		return location_id;
	}


	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public double getLattitude() {
		return lattitude;
	}


	public double getLongtitude() {
		return longtitude;
	}
	
	
	
	
}
