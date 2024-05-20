package application;

import java.time.LocalDate;
import java.util.Scanner;

public class CustomDate implements Comparable<CustomDate> {
	private  LocalDate date; 
	private LinkedList<Martyr> martyrs;

	public CustomDate(String date) {
		setDate(date);
		martyrs = new LinkedList<>();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(String date) {
		try {
			Scanner scanner = new Scanner(date);
			if (date.contains("/")) {
				scanner.useDelimiter("/");
				int month = Integer.parseInt(scanner.next());
				int day = Integer.parseInt(scanner.next());
				int year = Integer.parseInt(scanner.next());
				this.date = LocalDate.of(year, month, day);
			}else if (date.contains("-")) {
				scanner.useDelimiter("-");
				int year = Integer.parseInt(scanner.next());
				int month = Integer.parseInt(scanner.next());
				int day = Integer.parseInt(scanner.next());
				this.date = LocalDate.of(year, month, day);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public LinkedList<Martyr> getMartyrs() {
		return martyrs;
	}

	public void setMartyrs(LinkedList<Martyr> martyrs) {
		this.martyrs = martyrs;
	}

	@Override
	public int compareTo(CustomDate o) {
		return date.compareTo(o.date);
	}

	@Override
	public String toString() {
		return "CustomDate [date=" + date + ", martyrs=" + martyrs + "]";
	}

}
