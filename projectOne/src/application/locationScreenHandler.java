package application;


import java.util.Date;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class locationScreenHandler {

	private ButtonType doneButton = new ButtonType("Done");
	private Alert alert = new Alert(Alert.AlertType.NONE, "", doneButton);
	private Label label2 = new Label();
	private BorderPane borderPane = new BorderPane();
	private TextField textField = new TextField();
	private Button button = new Button();
	private HBox hBox = new HBox(textField, button);
	private DoublyLinkedList<District> districts;
	private LinkedList<Location> locations;
	private TextField addLocationTF = new TextField();
	private Button addLocationBtn = new Button("Add New Location");
	private Label addLocationLabel = new Label();
	private HBox addHBox = new HBox(addLocationTF, addLocationBtn, addLocationLabel);
	private TextField updateLocationTF = new TextField();
	private Button updateLocationBtn = new Button("Update Location");
	private Label updateLocationLabel = new Label();
	private HBox updateHBox = new HBox(updateLocationTF, updateLocationBtn, updateLocationLabel);
	private Button deleteLocationBtn = new Button("Delete Location");
	private Label deleteLocationLabel = new Label();
	private HBox deleteHBox = new HBox(deleteLocationBtn, deleteLocationLabel);
	private Label districtName = new Label();
	private Label locationName = new Label();
	private HBox namesHBox = new HBox(districtName, locationName);
	private Label numOfMartyrs = new Label();
	private Label numOfMaleAndFemale = new Label();
	private HBox hBox1 = new HBox(numOfMartyrs, numOfMaleAndFemale);
	private Label averageAge = new Label();
	private Label oldestLabel = new Label();
	private Label youngestLabel = new Label();
	private Button nextDistrict = new Button("Next District");
	private Button previousDistrict = new Button("Previous District");
	private Button nextLocation = new Button("Next Location");
	private Button previousLocation = new Button("Previous Location");
	private HBox hBox3 = new HBox(previousDistrict, previousLocation, nextLocation, nextDistrict);
	private Button addMartyr = new Button("Add New Martyr");
	private Button deleteMartyr = new Button("Delete Martyr");
	private HBox martyrHBox1 = new HBox(addMartyr, deleteMartyr);
	private Button updateMartyr = new Button("Update Martyr");
	private Button searchMartyr = new Button("Search For Martyr");
	private HBox martyrHBox2 = new HBox(updateMartyr, searchMartyr);
	private VBox finalVBox = new VBox(namesHBox, hBox1, oldestLabel, youngestLabel, averageAge,
			hBox3, addHBox, updateHBox, deleteHBox, martyrHBox1, martyrHBox2);
	private TextField field = new TextField();
	private TextField field1 = new TextField();
	private TextField field2 = new TextField();
	private TextField field5 = new TextField();
	private Button button1 = new Button();
	private Label label = new Label();
	private VBox box = new VBox(field, field1, field2, field5, button1, label);
	private Integer i = -1, j = -1;

	public locationScreenHandler(DoublyLinkedList<District> districts, Integer j) {//constructor
		this.j = j;
		this.districts = districts;
		initialize();
		this.locations = getPrevORNextDistrict(j, "next").getLocations();
		getPrevORNextLocation(++i, "next");
	}

	public locationScreenHandler(DoublyLinkedList<District> districts) {//constructor
		this.districts = districts;
		initialize();
		this.locations = getPrevORNextDistrict(++j, "next").getLocations();
		getPrevORNextLocation(++i, "next");
	}

	public VBox getVBox() {
		return finalVBox;
	}

	public void initialize() {// method to initialize fx
		try {
			addLocationTF.setPromptText("Enter Location Name");
			updateLocationTF.setPromptText("Enter New Name");
			addHBox.setAlignment(Pos.CENTER);
			addHBox.setSpacing(5);
			updateHBox.setAlignment(Pos.CENTER);
			updateHBox.setSpacing(5);
			deleteHBox.setAlignment(Pos.CENTER);
			deleteHBox.setSpacing(5);
			hBox1.setAlignment(Pos.CENTER);
			hBox1.setSpacing(5);
			hBox3.setAlignment(Pos.CENTER);
			hBox3.setSpacing(5);
			namesHBox.setAlignment(Pos.CENTER);
			namesHBox.setSpacing(5);
			martyrHBox1.setAlignment(Pos.CENTER);
			martyrHBox1.setSpacing(5);
			martyrHBox2.setAlignment(Pos.CENTER);
			martyrHBox2.setSpacing(5);
			finalVBox.setAlignment(Pos.CENTER);
			finalVBox.setSpacing(10);
			borderPane.setCenter(label2);
			hBox.setSpacing(5);
			borderPane.setTop(hBox);
			alert.getDialogPane().setContent(borderPane);
			textField.setPromptText("Enter Name");
			borderPane.setPrefWidth(400);
			borderPane.setPrefHeight(100);
			hBox.setAlignment(Pos.CENTER);
			field.setPromptText("Enter Name");
			field1.setPromptText("Enter Date Of Death ex:10/31/2015");
			field2.setPromptText("Enter Age");
			field5.setPromptText("Enter Gender");
			box.setAlignment(Pos.CENTER);
			box.setSpacing(5);
			box.setPrefWidth(300);

			addLocationBtn.setOnAction(e -> {//handle add Location Button
				District district = getPrevORNextDistrict(j, "next");
				if (district != null) {
					String name = addLocationTF.getText().trim();
					if (!name.equals("")) {
						Location location = district.getLocations().search(new Location(name));
						if (location == null) {
							district.getLocations().insert(new Location(name));
							addLocationLabel.setText("Added Succeffly");
							getPrevORNextLocation(++i, "next");
						} else {
							addLocationLabel.setText("Already Exist");
						}
					} else {
						addLocationLabel.setText("Enter Name");
					}
				}
			});
			nextDistrict.setOnAction(e -> {//handle next District Button
				if (j < districts.length() - 1) {
					District district = getPrevORNextDistrict(++j, "next");
					this.locations = district.getLocations();
					getPrevORNextLocation(++i, "next");
				}
			});
			previousDistrict.setOnAction(e -> {//handle previous District Button
				if (j > 0) {
					District district = getPrevORNextDistrict(--j, "previous");
					this.locations = district.getLocations();
					getPrevORNextLocation(++i, "next");
				}
			});
			nextLocation.setOnAction(e -> {//handle next Location Button
				if (i < locations.length() - 1) {
					getPrevORNextLocation(++i, "next");
				} else {
					this.i = -1;
					getPrevORNextLocation(++i, "next");
				}
			});
			previousLocation.setOnAction(e -> {//handle previous Location Button
				if (i > 0) {
					getPrevORNextLocation(--i, "previous");
				}
			});
			deleteLocationBtn.setOnAction(e1 -> {//handle delete Location Button
				Location location = getPrevORNextLocation(i, "next");
				if (location != null) {
					if (this.locations.delete(location) != null) {
						deleteLocationLabel.setText("Deleted Successfuly");
						if (i > 0)
							i--;
						getPrevORNextLocation(++i, "next");
					} else {
						deleteLocationLabel.setText("Location Not Found");
					}
				} else {
					deleteLocationLabel.setText("No Locations To Delete");
				}
			});
			updateLocationBtn.setOnAction(e -> {//handle update Location Button
				String name = updateLocationTF.getText().trim();
				if (!name.equals("")) {
					Location location = getPrevORNextLocation(i, "next");
					District district = getPrevORNextDistrict(j, "next");
					if (district != null && location != null) {
						location.setLocationName(name);
						district.getLocations().delete(location);
						district.getLocations().insert(location);
						getPrevORNextLocation(++i, "next");
						updateLocationLabel.setText("Updated Succeffly");
					} else {
						updateLocationLabel.setText("No Locations To Update");
					}
				} else {
					updateLocationLabel.setText("Enter Name");
				}
			});
			searchMartyr.setOnAction(e -> {//handle search for Martyr Button
				alert.setTitle("Search Martyr By Name");
				button.setText("Search");
				alert.getDialogPane().setContent(borderPane);
				button.setOnAction(e1 -> {
					Location location = getPrevORNextLocation(i, "next");
					if (location != null) {
						Martyr martyr = location.findMartyrByPartName(textField.getText().trim());
						if (martyr != null) {
							alert.setWidth(800);
							label2.setText(martyr.toString());
						} else {
							label2.setText("Not Found");
						}
					} else {
						label2.setText("Not Found");
					}
				});
				alert.showAndWait();
				alert.setWidth(414.3999938964844);
			});
			addMartyr.setOnAction(e -> {//handle add Martyr Button
				alert.setTitle("Add Martyr");
				button1.setText("Add");
				alert.getDialogPane().setContent(box);
				button1.setTextAlignment(TextAlignment.CENTER);
				button1.setOnAction(e1 -> {
					try {
						String name = field.getText().trim();
						String dateOfDeath = field1.getText().trim();
						String age = field2.getText().trim();
						if (!name.equals("") && !dateOfDeath.equals("") && dataValidation(dateOfDeath)
								&& !field5.getText().trim().equals("") && !age.equals("")) {
							char gender = field5.getText().trim().charAt(0);
							if (gender == 'M' || gender == 'F') {
								Location location2 = getPrevORNextLocation(i, "next");
								String districtName = getPrevORNextDistrict(j, "next").getDistrictName();
								String LocationName = location2.getLocationName();
								Martyr martyr = location2.findMartyr(
										new Martyr(name, dateOfDeath, age, LocationName, districtName, gender));
								if (martyr == null) {
									location2.getMartyrs().insert(
											new Martyr(name, dateOfDeath, age, LocationName, districtName, gender));
									label.setText("Added Successfully");
									getPrevORNextLocation(++i, "next");
								} else
									label.setText("Already Exist");
							} else {
								label.setText("Enter M or F Only");
							}
						} else
							label.setText("Enter Valid Data");
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}
				});
				alert.showAndWait();
			});
			updateMartyr.setOnAction(e -> {//handle update Martyr Button
				try {
					button.setText("Find");
					alert.getDialogPane().setContent(borderPane);
					alert.setTitle("Update Martyr");
					button1.setText("Update");
					button.setOnAction(e1 -> {
						String oldName = textField.getText().trim();
						Location location = getPrevORNextLocation(i, "next");
						if (location != null) {
						Martyr martyr = location.findMartyrByName(oldName);
						if (martyr != null) {
							alert.setHeight(400);
							alert.getDialogPane().setContent(box);
							button1.setOnAction(e2 -> {
								String newName = field.getText().trim();
								String dateOfDeath = field1.getText().trim();
								String age = field2.getText().trim();
								if (!newName.equals("") && !dateOfDeath.equals("") && dataValidation(dateOfDeath)
										&& !field5.getText().trim().equals("") && !age.equals("")) {
									char gender = field5.getText().trim().charAt(0);
									if (gender == 'M' || gender == 'F') {
										Martyr martyr1 = location.findMartyr(new Martyr(newName, dateOfDeath, age,
												martyr.getLocation(), martyr.getDistrict(), gender));
										if (martyr1 == null) {
											martyr.setAge(age);
											martyr.setDateOfDeath(dateOfDeath);
											martyr.setGender(gender);
											martyr.setName(newName);
											location.deleteMartyr(martyr);
											location.insertMartyr(martyr);
											label.setText("Updated Successfully");
										} else
											label.setText("Already Exist");
									} else {
										label.setText("Enter M or F Only");
									}
								} else if (!newName.equals("") && dateOfDeath.equals("") && field5.getText().trim().equals("") && age.equals("")) {
									Martyr martyr1 = location.findMartyr(
											new Martyr(newName, martyr.getDateOfDeath(), martyr.getAge() + "",
													martyr.getLocation(), martyr.getDistrict(), martyr.getGender()));
									if (martyr1 == null) {
										martyr.setName(newName);
										label.setText("Updated Successfully");
									} else
										label.setText("Already Exist");
								} else if (!age.equals("") && newName.equals("") && dateOfDeath.equals("") && field5.getText().trim().equals("")) {
									Martyr martyr1 = location
											.findMartyr(new Martyr(martyr.getName(), martyr.getDateOfDeath(), age,
													martyr.getLocation(), martyr.getDistrict(), martyr.getGender()));
									if (martyr1 == null) {
										martyr.setAge(age);
										location.deleteMartyr(martyr);
										location.insertMartyr(martyr);
										label.setText("Updated Successfully");
									} else
										label.setText("Already Exist");
								} else if (!dateOfDeath.equals("") && dataValidation(dateOfDeath)
										&& field5.getText().trim().equals("") && age.equals("") && newName.equals("")) {
									Martyr martyr1 = location
											.findMartyr(new Martyr(martyr.getName(), dateOfDeath, martyr.getAge() + "",
													martyr.getLocation(), martyr.getDistrict(), martyr.getGender()));
									if (martyr1 == null) {
										martyr.setDateOfDeath(dateOfDeath);
										label.setText("Updated Successfully");
									} else
										label.setText("Already Exist");
								} else if (!field5.getText().trim().equals("") && dateOfDeath.equals("") && age.equals("") && newName.equals("")) {
									char gender = field5.getText().trim().charAt(0);
									if (gender == 'M' || gender == 'F') {
										Martyr martyr1 = location.findMartyr(new Martyr(martyr.getName(),
												martyr.getDateOfDeath(), martyr.getAge() + "", martyr.getLocation(),
												martyr.getDistrict(), gender));
										if (martyr1 == null) {
											martyr.setGender(gender);
											label.setText("Updated Successfully");
										} else
											label.setText("Already Exist");
									} else {
										label.setText("Enter M or F Only");
									}
								} else
									label.setText("Enter ALL OR One Data");
							});
						} else
							label2.setText("Not Found");
						} else
							label2.setText("No Locatios");
					});
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
				alert.showAndWait();
				alert.setHeight(184.0);
			});
			deleteMartyr.setOnAction(e -> {//handle delete Martyr Button
				alert.setTitle("Deleted Martyr By Name");
				button.setText("Delete");
				alert.getDialogPane().setContent(borderPane);
				button.setOnAction(e1 -> {
					Location location = getPrevORNextLocation(i, "next");
					if (location != null) {
						Martyr martyr = location.findMartyrByName(textField.getText().trim());
						if (martyr != null) {
							location.deleteMartyr(martyr);
							label2.setText("Deleted Successfully");
							getPrevORNextLocation(i, "next");
						} else {
							label2.setText("Deleted Unsuccessfully");
						}
					} else {
						label2.setText("Deleted Unsuccessfully");
					}
				});
				alert.showAndWait();
			});
		} catch (Exception e) {
		}
	}

	private Location getPrevORNextLocation(Integer i, String string) {// method to get Previous OR Next Location
		if (locations.get(i) != null) {
			Location location = locations.get(i).getData();
			locationName.setText(location.getLocationName());
			double total = location.getMartyrs().length();
			int males = 0;
			int females = 0;
			double averageAge = 0;
			int oldest = 0;
			int youngest=200;

			for (int k = 0; k < total; k++) {
				Martyr martyr = location.getMartyrs().get(k).getData();
				averageAge += martyr.getAge();
				if (martyr.getGender() == 'M') {
					males++;
				} else if (martyr.getGender() == 'F') {
					females++;
				}
				if (oldest < martyr.getAge()) {
					oldest = martyr.getAge();
					oldestLabel.setText("Oldest Martyr: "+martyr.getName());
				}
				if (youngest > martyr.getAge()) {
					youngest = martyr.getAge();
					youngestLabel.setText("Youngest Martyr: "+martyr.getName());

				}
			}
			numOfMartyrs.setText("Number Of Martyrs " + total);
			numOfMaleAndFemale.setText(",Number Of Male , Female Martyrs " + males + " , " + females);
			if (total > 0) {
				this.averageAge.setText("Average martyrs ages " + String.format("%.1f", averageAge / total));
			}else {
				this.averageAge.setText("Average martyrs ages 0");
			}
			return location;
		} else {
			oldestLabel.setText("");
			youngestLabel.setText("");
			locationName.setText("");
			numOfMartyrs.setText("");
			numOfMaleAndFemale.setText("");
			this.averageAge.setText("");
			return null;
		}
	}

	private District getPrevORNextDistrict(Integer j, String type) {// method to get Previous OR Next District
		if (districts.get(j) != null) {
			District district = districts.get(j).getData();
			districtName.setText(district.getDistrictName() + " District,");
			this.i = -1;
			return district;
		} else {
			districtName.setText("");
			numOfMartyrs.setText("");
			numOfMaleAndFemale.setText("");
			this.averageAge.setText("");
			return null;
		}
	}

	public boolean dataValidation(String data) {// method to check if the date is valid
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
					&& dayString.length() > 0 && mounthString.length() <= 2 && mounthString.length() > 0 && mounth <= 12
					&& day <= 31) {
				return true;
			}
			return false;
		}
		return false;
	}
}