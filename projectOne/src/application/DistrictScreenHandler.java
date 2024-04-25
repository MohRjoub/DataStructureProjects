package application;

import java.util.Date;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DistrictScreenHandler {
	
	private DoublyLinkedList<District> districts;
	private TextField addDistrictTF = new TextField();
	private Button addDistrictBtn = new Button("Add New District");
	private Label addDistrictLabel = new Label();
	private HBox addHBox = new HBox(addDistrictTF, addDistrictBtn, addDistrictLabel);
	private TextField updateDistrictTF = new TextField();
	private Button updateDistrictBtn = new Button("Update District");
	private Label updateDistrictLabel = new Label();
	private HBox updateHBox = new HBox(updateDistrictTF, updateDistrictBtn, updateDistrictLabel);
	private Button deleteDistrictBtn = new Button("Delete District");
	private Label deleteDistrictLabel = new Label();
	private HBox deleteHBox = new HBox(deleteDistrictBtn, deleteDistrictLabel);
	private Label districtName = new Label();
	private Label numOfMartyrs = new Label();
	private Label numOfMaleAndFemale = new Label();
	private HBox hBox1 = new HBox(numOfMartyrs, numOfMaleAndFemale);
	private Label averageAge = new Label();
	private Label maxNumOfMartyr = new Label();
	private HBox hBox2 = new HBox(averageAge, maxNumOfMartyr);
	private Button nextDistrict = new Button("Next District");
	private Button previousDistrict = new Button("Previous District");
	private HBox hBox3 = new HBox(previousDistrict, nextDistrict);
	private Button totalNumberOfMartyrsBtn = new Button("Total Number Of Martyrs");
	private TextField totalNumberOfMartyrsTF = new TextField();
	private Label totalNumberOfMartyrsLabel = new Label();
	private HBox totalHBox = new HBox(totalNumberOfMartyrsTF, totalNumberOfMartyrsBtn, totalNumberOfMartyrsLabel);
	private Button loadFirst = new Button("Load First Location");
	private VBox finalVBox = new VBox(districtName, hBox1, hBox2, hBox3, addHBox, updateHBox, deleteHBox, totalHBox,
			loadFirst);
	private Integer i = -1;

	public DistrictScreenHandler(DoublyLinkedList<District> districts) {
		try {
			this.districts = districts;
			addDistrictTF.setPromptText("Enter District Name");
			updateDistrictTF.setPromptText("Enter New Name");
			totalNumberOfMartyrsTF.setPromptText("Enter Date ex: 10/6/2001");
			addHBox.setAlignment(Pos.CENTER);
			addHBox.setSpacing(5);
			updateHBox.setAlignment(Pos.CENTER);
			updateHBox.setSpacing(5);
			deleteHBox.setAlignment(Pos.CENTER);
			deleteHBox.setSpacing(5);
			hBox1.setAlignment(Pos.CENTER);
			hBox1.setSpacing(5);
			hBox2.setAlignment(Pos.CENTER);
			hBox2.setSpacing(5);
			hBox3.setAlignment(Pos.CENTER);
			hBox3.setSpacing(5);
			totalHBox.setAlignment(Pos.CENTER);
			totalHBox.setSpacing(5);
			finalVBox.setAlignment(Pos.CENTER);
			finalVBox.setSpacing(10);
			getPrevORNext(++i, "next");
			addDistrictBtn.setOnAction(e -> {// handle add District Button
				String Name = addDistrictTF.getText().trim();
				if (!Name.equals("")) {
					District district = districts.search(new District(Name));
					if (district == null) {
						this.districts.insert(new District(Name));
						addDistrictLabel.setText("Added Successfuly");
					} else {
						addDistrictLabel.setText("Alreade Exist");
					}
				} else {
					addDistrictLabel.setText("Please Enter A Valid Input");
				}
			});
			updateDistrictBtn.setOnAction(e -> {// handle update District Button
				District district = getPrevORNext(i, "previous");
				if (district != null) {
					String name = updateDistrictTF.getText().trim();
					if (!name.equals("")) {
						this.districts.delete(district);
						district.setDistrictName(name);
						this.districts.insert(district);
						updateDistrictLabel.setText("Name Updated Successfully");
						districtName.setText(name);
					}else {
						updateDistrictLabel.setText("Enter Name");
					}
				}
			});
			deleteDistrictBtn.setOnAction(e1 -> {// handle delete District Button
				District district = getPrevORNext(i, "next");
				if (district != null) {
					if (districts.delete(district) != null) {
						deleteDistrictLabel.setText("Deleted Successfuly");
						if (i > 0)
							i--;
						getPrevORNext(i, "next");
					} else {
						deleteDistrictLabel.setText("District Not Found");
					}
				}
			});
			nextDistrict.setOnAction(e -> {// handle next District Button
				if (districts.length()>1) {
					if (i < districts.length()-1) {
						getPrevORNext(++i, "next");
					}
				}else if (districts.length()==1) {
					getPrevORNext(i, "next");
				}

			});
			previousDistrict.setOnAction(e -> {// handle previous District Button
				if (i > 0) {
					getPrevORNext(--i, "previous");
				}
			});
			totalNumberOfMartyrsBtn.setOnAction(e -> {// handle total number of martyrs for a given date Button
				try {
					String date = totalNumberOfMartyrsTF.getText().trim();
					if (dateValidation(date)) {
						int numberOfMartyrs = getNumOfMartyrs(date);
						totalNumberOfMartyrsLabel.setText("Number Of Martyrs Is " + numberOfMartyrs);
					} else {
						totalNumberOfMartyrsLabel.setText("Enter A Valid Date");
					}
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			});
			
			loadFirst.setOnAction(e2 -> {// handle load the first location in this district to location screen Button
				if (this.districts.length()>0) {
							locationScreenHandler lScreen = new locationScreenHandler(districts, i);
							this.finalVBox.getChildren().clear();
							this.finalVBox.getChildren().addAll(lScreen.getVBox().getChildren());
				
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public VBox getVBox() {
		return finalVBox;
	}

	public boolean dateValidation(String data) {// method to check if the date is valid
		Scanner in = new Scanner(data.trim());
		in.useDelimiter("/");
		String mounthString = in.next();
		String dayString = in.next();
		String yearString = in.next();
		if (yearString.length() == 4 && dayString.length() <= 2 && dayString.length() > 0 && mounthString.length() <= 2
				&& mounthString.length() > 0) {
			int year = Integer.valueOf(yearString) - 1900;
			int mounth = Integer.valueOf(mounthString);
			int day = Integer.valueOf(dayString);
			Date date1 = new Date();
			Date date2 = new Date(year, mounth, day);
			if (date2.compareTo(date1) <= 0 && yearString.length() == 4 && dayString.length() <= 2
					&& dayString.length() > 0 && mounthString.length() <= 2 && mounthString.length() > 0 && mounth <= 12 && mounth > 0
					&& day <= 31 && day > 0) {
				return true;
			}
			return false;
		}
		return false;

	}
	public int getNumOfMartyrs(String date) {// method to calculate number Of Martyrs
		int sum = 0;
		int districtLength = this.districts.length();
		for (int i = 0; i < districtLength; i++) {
			District district = this.districts.get(i).getData();
			int locationLength = district.getLocations().length();
			for (int j = 0; j < locationLength; j++) {
				Location location = district.getLocations().get(j).getData();
				int martyrsLength = location.getMartyrs().length();
				for (int k = 0; k < martyrsLength; k++) {
					Martyr martyr = location.getMartyrs().get(k).getData();
					if (martyr.getDateOfDeath().equals(date))
						sum++;
				}
			}
		}
		return sum;
	}
	private District getPrevORNext(Integer i, String type) {// method to navigate throw districts and get it's statistics
			DNode<District>dNode = districts.get(i);// get the current District node 
		if (dNode != null) {
			District district = dNode.getData();
			districtName.setText(district.getDistrictName());
			double total = 0;
			int males = 0;
			int females = 0;
			double averageAge = 0;
			int maxNum = 0;
			String dateString = "";
			for (Node<Location>Node=district.getLocations().getHead().getNext();Node != null;Node=Node.getNext()) {//iterate throw Locations linked list
				Location location = Node.getData();
				int numInLocation = location.getMartyrs().length();
				total += numInLocation;
				for (Node<Martyr>node=location.getMartyrs().getHead().getNext(); node != null; node=node.getNext()) {//iterate throw Martyrs linked list
					Martyr martyr = node.getData();
					averageAge += martyr.getAge();
					if (martyr.getGender() == 'M') {
						males++;
					} else if (martyr.getGender() == 'F') {
						females++;
					}
					int ocurr = 0;
					for (Node<Martyr>node1=location.getMartyrs().getHead().getNext(); node1 != null; node1=node1.getNext()) {//loop to calculate date that has the maximum number of martyrs
						if (martyr.getDateOfDeath().equals(node1.getData().getDateOfDeath())) {
							ocurr++;
						}
					}
					if (ocurr > maxNum) {
						maxNum = ocurr;
						dateString = martyr.getDateOfDeath();
					}
				}
			}
			maxNumOfMartyr.setText(dateString + " Has The Maximum Number Of Martyrs");
			numOfMartyrs.setText("Number Of Martyrs " + total);
			numOfMaleAndFemale.setText(",Number Of Male , Female Martyrs " + males + " , " + females);
			if(total>0)
			this.averageAge.setText("Average martyrs ages " + String.format("%.1f", averageAge / total));
			else
				this.averageAge.setText("Average martyrs ages 0");
			return district;
		} else {
			maxNumOfMartyr.setText("");
			districtName.setText("");
			numOfMartyrs.setText("");
			numOfMaleAndFemale.setText("");
			this.averageAge.setText("");
			return null;
		}
	}
}
