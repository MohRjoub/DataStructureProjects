package application;

public class Location implements Comparable<Location> {
	private String locationName;
	private BSTree<CustomDate> dates;

	public Location(String locationName) {
		this.locationName = locationName;
		dates = new BSTree<>();
	}

	public BSTree<CustomDate> getDates() {
		return dates;
	}

	public void setDates(BSTree<CustomDate> dates) {
		this.dates = dates;
	}

	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	@Override
	public int compareTo(Location o) {
		return locationName.compareToIgnoreCase(o.locationName);
	}

	@Override
	public String toString() {
		return "Location [locationName=" + locationName + "\n, dates=" + dates + "]";
	}

}
