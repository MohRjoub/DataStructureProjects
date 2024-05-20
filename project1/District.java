package application;


public class District implements Comparable<District> {
	private LinkedList<Location> locations = new LinkedList<>();
	private String districtName;
	
	public District(String districtName) {
		this.districtName = districtName;
	}


	public LinkedList<Location> getLocations() {
		return locations;
	}


	public void insertLocation(Location location) {
		this.locations.insert(location);
	}


	public String getDistrictName() {
		return districtName;
	}


	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}


	@Override
	public String toString() {
		return "District [districtName=" + districtName + ",locations=" + locations +  "]";
	}


	@Override
	public int compareTo(District o) {
		return this.districtName.compareToIgnoreCase(o.getDistrictName());
	}
}
