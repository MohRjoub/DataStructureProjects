package application;

public class Location implements Comparable<Location> {
	private LinkedList<Martyr> martyrs = new LinkedList<>();
	private String locationName; 
	
	
	public Location(String locationName) {
		this.locationName = locationName;
	}


	public LinkedList<Martyr> getMartyrs() {
		return martyrs;
	}

	public Martyr findMartyr(Martyr data) {
		Node<Martyr> current = martyrs.getHead().getNext();
		for (; current != null && current.getData().compareTo(data) <= 0; current = current.getNext()) {
			if (current.getData().equals(data)) {
				return current.getData();
			}
		}
		return null;	
		}
	public Martyr findMartyrByName(String name) {
			Node<Martyr> current = martyrs.getHead().getNext();
			for (; current != null ; current = current.getNext()) {
				if (current.getData().getName().equals(name)) {
					return current.getData();
				}
			}
			return null;	
			}
	public Martyr findMartyrByPartName(String name) {
		Node<Martyr> current = martyrs.getHead().getNext();
		for (; current != null ; current = current.getNext()) {
			if (current.getData().getName().contains(name)) {
				return current.getData();
			}
		}
		return null;	
		}
	public void deleteMartyr(Martyr martyr) {
		this.martyrs.delete(martyr);
	}

	
	public void searchMartyr(Martyr martyr) {
		this.martyrs.search(martyr);
	}
	
	
	public void insertMartyr(Martyr martyr) {
		this.martyrs.insert(martyr);
	}
	

	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	@Override
	public String toString() {
		return "Location [locationName=" + locationName + ",martyrs=" + martyrs +"]";
	}


	@Override
	public int compareTo(Location o) {
		return this.locationName.compareToIgnoreCase(o.getLocationName());
	}

}
