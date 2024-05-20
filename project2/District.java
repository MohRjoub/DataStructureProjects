package application;

public class District implements Comparable<District> {
	private String districtName;
	private BSTree<Location> locations;

	public District(String districtName) {
		locations = new BSTree<>();
		this.districtName = districtName;
	}

	public BSTree<Location> getLocations() {
		return locations;
	}

	public void setLocations(BSTree<Location> locations) {
		this.locations = locations;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Override
	public int compareTo(District o) {
		return districtName.compareToIgnoreCase(o.districtName);
	}

	@Override
	public String toString() {
		return "District [districtName=" + districtName + ", locations=" + locations + "]";
	}

}
