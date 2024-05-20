package application;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main extends Application {
	private BSTree<District> records = new BSTree<>();
	@Override
	public void start(Stage primaryStage) {
		try {
			FileChooser fileChooser = new FileChooser();
			Label label = new Label("No files selected");
			Button open = new Button("Open File");
			VBox openFileBox = new VBox(30, label, open);
			openFileBox.setAlignment(Pos.CENTER);
			Button locationScreen = new Button("Location Screen");
			Button districtScreen = new Button("District Screen");
			Button martyrScreen = new Button("Martyr Screen");
			VBox finalBox = new VBox(districtScreen, locationScreen, martyrScreen);
			finalBox.setAlignment(Pos.CENTER);
			finalBox.setSpacing(5);
			Scene scene = new Scene(finalBox,400, 400);
			open.setOnAction(new EventHandler<ActionEvent>() {// handle opening file using File Chooser
				public void handle(ActionEvent e) {
					fileChooser.setInitialDirectory(new File("C:\\Users\\ACTC\\Downloads"));
					File file = fileChooser.showOpenDialog(primaryStage);
					if (file != null
							&& (file.getName().equals("data_2.csv") || file.getName().equals("sample_2.csv"))) {// check the right file
						label.setText(file.getName() + "  selected");
						Scanner in;
						try {
							in = new Scanner(file);
							in.nextLine();
							while (in.hasNext()) {// read data from the file and save it to the linked list
								String string = in.nextLine().trim();
								Scanner scanner = new Scanner(string);
								scanner.useDelimiter(",");
								String Name = scanner.next();
								String Date = scanner.next();
								String Age = scanner.next();
								String Location = scanner.next();
								String District = scanner.next();
								String Gender = scanner.next();
								Martyr martyr = new Martyr(Name, Age, Gender.charAt(0));// create new Martyr object
								TNode<District> node = records.find(new District(District));// check if the District already exist
								if (node != null) {
									District district = node.getData();
									TNode<Location> node1 = district.getLocations().find(new Location(Location));
									if (node1 != null) {// check if the Location already exist
										Location location = node1.getData();
										TNode<CustomDate> dateNode = location.getDates().find(new CustomDate(Date));
										if (dateNode != null) {// check if the Date already exist
											CustomDate date = dateNode.getData();
											if (date.getMartyrs().search(martyr) == null) {// check if the Martyr already exist
												date.getMartyrs().insert(martyr);// if not add it
											}
										} else {// if not add it
											CustomDate tempDate = new CustomDate(Date);
											tempDate.getMartyrs().insert(martyr);
											location.getDates().insert(tempDate);
										}
									} else {// if the Location not exist add it
										Location tempLocation = new Location(Location);
										CustomDate tempDate = new CustomDate(Date);
										tempDate.getMartyrs().insert(martyr);
										tempLocation.getDates().insert(tempDate);
										district.getLocations().insert(tempLocation);
									}
								} else {// if the District not exist add it
									CustomDate tempDate = new CustomDate(Date);
									tempDate.getMartyrs().insert(martyr);
									Location tempLocation = new Location(Location);
									tempLocation.getDates().insert(tempDate);
									District tempDistrict = new District(District);// create new District object
									tempDistrict.getLocations().insert(tempLocation);
									records.insert(tempDistrict);
								}
							}
							DistrictScreen handeler1 = new DistrictScreen(records);
							districtScreen.setOnAction(e1 -> {//handle District Screen button
								primaryStage.hide();
								handeler1.start(primaryStage);
							});
							locationScreen handeler2 = new locationScreen(records, null);
							locationScreen.setOnAction(e1 -> {//handle location Screen button
								primaryStage.hide();
								handeler2.start(primaryStage);
							});
							MartyrScreen handeler3 = new MartyrScreen(records, null, null);
							martyrScreen.setOnAction(e1 -> {//handle location Screen button
								Scene scene1 = new Scene(handeler3.getBorderPane(),500, 500);
								primaryStage.setScene(scene1);
								primaryStage.show();
							});
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception e1) {
							System.out.println(e1.getMessage());
						}
					} else {
						try {
							label.setText("Not the correct file");
						} catch (Exception e2) {
						}
					}
				}
			});
			Scene intialScene = new Scene(openFileBox, 400, 400);
			primaryStage.setTitle("Martyrs Info");
			primaryStage.setScene(intialScene);
			primaryStage.show();
			} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
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
}
