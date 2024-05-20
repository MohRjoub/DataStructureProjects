package application;


public class Martyr implements Comparable<Martyr> {
	// Name, Age, and Gender
	private String name;
	private int age;
	private char gender;

	public Martyr(String name, String age, char gender) {
		setName(name);
		setAge(age);
		setGender(gender);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(String age) {
		if (age.length() == 0) {// if the record has no age set it age to -1
			this.age = -1;
		} else {
			try {
				int tempAge = Integer.parseInt(age.trim());
				if (tempAge > 0) {
					this.age = tempAge;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public int getAge() {
		return age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public int compareTo(Martyr o) {
		if (this.age - o.age != 0) {
			return this.age - o.age;
		}
		return this.gender - o.gender;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			Martyr other = (Martyr) obj;
			return gender == other.gender && name.equalsIgnoreCase(other.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Martyr [name=" + name + ", age=" + age + ", gender=" + gender + "]\n";
	}

}
