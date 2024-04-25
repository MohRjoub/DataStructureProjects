package application;

public class Martyr implements Comparable<Martyr>{
	// (Name, Date, Age, location, District, and Gender)
	private String name;
	private String dateOfDeath;
	private int age;
	private String location;
	private String district;
	private char gender;
	
	
	public Martyr(String name, String dateOfDeath, String age, String location, String district, char gender) {
			setName(name);
			setAge(age);
			setLocation(location);
			setDateOfDeath(dateOfDeath);
			setDistrict(district);
			setGender(gender);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDateOfDeath() {
		return dateOfDeath;
	}


	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}


	public void setAge(String age) {
		if (age.length()==0) {//if the record has no age set it age to -1
			this.age = -1;
		}else {
			try {
				int tempAge = Integer.parseInt(age);
				if (tempAge > 0) {					
					this.age = tempAge;
				}
				} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}


	public int getAge() {
		return this.age;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public char getGender() {
		return gender;
	}


	public void setGender(char gender) {//validate gender
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
	public String toString() {
		return "Martyr [Name=" + name + ", Date Of Death=" + dateOfDeath + ", Age=" + age + ", Location="
				+ location + ", District=" + district + ", Gender=" + gender + "]";
	}


	@Override
	public int compareTo(Martyr o) {
	//	return ((Integer)this.age).compareTo(o.getAge());
		return age - o.age;
	}
	@Override
	public boolean equals(Object o) {
		Martyr martyr;
		if (o != null) {
			martyr = (Martyr)o;
			return this.name.equalsIgnoreCase(martyr.getName()) && this.dateOfDeath.equalsIgnoreCase(martyr.getDateOfDeath()) 
					&& this.age == martyr.getAge() && this.location.equalsIgnoreCase(martyr.getLocation()) 
					&& this.district.equalsIgnoreCase(martyr.getDistrict()) && this.gender == martyr.getGender();
		}
		return false;
	}

}