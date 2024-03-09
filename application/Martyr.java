package application;

public class Martyr implements Comparable<Martyr>{
//(Name, Age, Event location, Date of death, and Gender)
	private String name;
	private int age;
	private String eventLocation;
	private String dateOfDeath;
	private char gender;
	
	
	
	public Martyr(String name, String age, String eventLocation, String dateOfDeath, char gender) {
		setName(name);
		setAge(age);
		setEventLocation(eventLocation);
		setDateOfDeath(dateOfDeath);
		setGender(gender);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(String age) {
		if (age.length()==0) {
			this.age = 0;
		}else {			
			this.age = Integer.parseInt(age);
		}
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		if (gender =='F' || gender=='M') 
		this.gender = gender;
		else if (gender=='N') {
			this.gender = 'U';	
		}
		else {
			System.out.println("Enter M or F only");
		}
	}
	@Override
	public int compareTo(Martyr o) {

		return o.getName().compareTo(this.getName());
	}
	@Override
	public String toString() {
		return "Martyr [name=" + name + ", age=" + age + ", eventLocation=" + eventLocation + ", dateOfDeath="
				+ dateOfDeath + ", gender=" + gender + "]";
	}
	
	
}
