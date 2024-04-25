package application;

import java.io.File;
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
	private DoublyLinkedList<District> records = new DoublyLinkedList<>();

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
		VBox finalBox = new VBox(districtScreen,locationScreen);
		finalBox.setAlignment(Pos.CENTER);
		finalBox.setSpacing(5);
		Scene scene = new Scene(finalBox,400, 400);
		open.setOnAction(new EventHandler<ActionEvent>() {// handle opening file using File Chooser
			public void handle(ActionEvent e) {
				fileChooser.setInitialDirectory(new File("C:\\Users\\ACTC\\Downloads"));
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null && (file.getName().equals("data (1).csv") || file.getName().equals("sample.csv"))) {// check the right file
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
							Martyr martyr = new Martyr(Name, Date, Age, Location, District, Gender.charAt(0));//create new Martyr object
							District district = records.search(new District(District));//check if the District already exist
							if (district != null) {
								Location location = district.getLocations().search(new Location(Location));//check if the Location already exist
								if (location != null) {
									Martyr martyr1 = location.findMartyr(martyr);//check if the Martyr already exist
									if (martyr1 == null) 
										location.getMartyrs().insert(martyr);//if not add it 
								} else {//if the Location not exist add it
									Location tempLocation = new Location(Location);
									tempLocation.insertMartyr(martyr);
									district.insertLocation(tempLocation);
								}
							} else {//if the District not exist add it
								Location tempLocation = new Location(Location);
								tempLocation.insertMartyr(martyr);
								District tempDistrict = new District(District);//create new District object
								tempDistrict.insertLocation(tempLocation);
								records.insert(tempDistrict);
							}
						}
						DistrictScreenHandler handeler1 = new DistrictScreenHandler(records);
						districtScreen.setOnAction(e1 -> {//handle District Screen button
							Scene scene1 = new Scene(handeler1.getVBox(),500, 500);
							primaryStage.setScene(scene1);
							primaryStage.show();
						});
						locationScreenHandler handeler2 = new locationScreenHandler(records);
						locationScreen.setOnAction(e1 -> {//handle location Screen button
							Scene scene1 = new Scene(handeler2.getVBox(),500, 500);
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
}
