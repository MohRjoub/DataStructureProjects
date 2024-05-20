package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class locationScreen extends Application {
	private ButtonType doneButton = new ButtonType("Done");
	private Alert alert = new Alert(Alert.AlertType.NONE, "", doneButton);
	private TextField addLocationTF = new TextField();
	private Button addLocationBtn = new Button("Add New Location");
	private Label addLocationLabel = new Label();
	private HBox addHBox = new HBox(addLocationTF, addLocationBtn, addLocationLabel);
	private Button updateLocationBtn = new Button("Update Location");
	private Label updateLocationLabel = new Label();
	private HBox updateHBox = new HBox(updateLocationBtn, updateLocationLabel);
	private Button deleteLocationBtn = new Button("Delete Location");
	private Label deleteLocationLabel = new Label();
	private HBox deleteHBox = new HBox(deleteLocationBtn, deleteLocationLabel);
	private Label LocationName = new Label();
	private Label totalNumOfMartyr = new Label();
	private Label maxNumOfMartyr = new Label();
	private Label earliestDate = new Label();
	private Label latestDate = new Label();
	private Button nextLocation = new Button("Next Location");
	private Button previousLocation = new Button("Previous Location");
	private Button loadFirst = new Button("Load First");
	private HBox hBox3 = new HBox(previousLocation, nextLocation);
	private ComboBox<String> districsBox = new ComboBox<>();
	private VBox finalVBox = new VBox(LocationName, totalNumOfMartyr, maxNumOfMartyr, earliestDate, latestDate, hBox3, addHBox, updateHBox, deleteHBox, loadFirst, districsBox);
	private BSTree<District> records;
	private Queue<District> queue;
	private Stack<District> stack;
	private District currDistrict;
	private Queue<Location> locationQueue;
	private Stack<Location> locationStack;
	private Location currLocation;
	private Scene scene1;
	public locationScreen(BSTree<District>records, District loadDistrict) {
		try {
			this.records = records;
			this.queue = this.records.toQueue();
			this.stack = new Stack<>();
			this.locationQueue = new Queue<>();
			this.locationStack = new Stack<>();
			if (!this.queue.isEmpty() && loadDistrict == null) {
				this.currDistrict = this.queue.dequeue();
				this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
				this.currLocation = this.locationQueue.dequeue();
				initialize(currLocation);
			}else if (!this.queue.isEmpty() && loadDistrict != null) {
				this.currDistrict = loadDistrict;
				this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
				this.currLocation = this.locationQueue.dequeue();
				initialize(currLocation);
			}
			addLocationTF.setPromptText("Enter District Name");
			addHBox.setAlignment(Pos.CENTER);
			addHBox.setSpacing(5);
			updateHBox.setAlignment(Pos.CENTER);
			updateHBox.setSpacing(5);
			deleteHBox.setAlignment(Pos.CENTER);
			deleteHBox.setSpacing(5);
			hBox3.setAlignment(Pos.CENTER);
			hBox3.setSpacing(5);
			finalVBox.setAlignment(Pos.CENTER);
			finalVBox.setSpacing(10);
			ObservableList<String> options = FXCollections.observableArrayList();
			Queue<District> queue1 = this.records.toQueue();
			if (currDistrict != null) {
				options.add(currDistrict.getDistrictName());
			}
			while (!queue1.isEmpty()) {
				String nameString = queue1.dequeue().getDistrictName();
				if (!nameString.equalsIgnoreCase(options.get(0))) {
					options.add(nameString);
				}
			}
			districsBox.setItems(options);
			districsBox.setPromptText("Choose District");
			districsBox.setOnAction(e ->{
				String selectedOption = districsBox.getValue();
				if (!selectedOption.equals("")) {
					TNode<District>node = this.records.find(new District(selectedOption));
					if (node != null) {
						this.currDistrict = node.getData();
						this.locationQueue.clear();
						this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
						this.locationStack = new Stack<>();
						if (!this.locationQueue.isEmpty()) {
							this.currLocation = this.locationQueue.dequeue();
							initialize(currLocation);
						}
					}
				}
			});
			
			addLocationBtn.setOnAction(e -> {
				String Name = addLocationTF.getText().trim();
				if (!Name.equals("")) {
					if (currDistrict != null) {
					TNode<Location> locationNode = this.currDistrict.getLocations().find(new Location(Name));
					if (locationNode == null) {
						Location location = new Location(Name);
						this.currDistrict.getLocations().insert(location);
						addLocationLabel.setText("Added Successfuly");
						this.locationQueue.clear();
						this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
						this.locationStack = new Stack<>();
						if (!this.locationQueue.isEmpty()) {
							this.currLocation = this.locationQueue.dequeue();
							initialize(currLocation);
						}
					} else {
						addLocationLabel.setText("Alreade Exist");
					}
				} else {
					addLocationLabel.setText("Please Enter A Valid Input");
				}}
			});

			updateLocationBtn.setOnAction(e -> {
				if (!this.records.isEmpty()) {
					updateLocationLabel.setText("");
				Label label = new Label("Select Location: ");
				TextField updateLocationTF = new TextField();
				updateLocationTF.setPromptText("Enter New Name");
				ObservableList<String> options1 = FXCollections.observableArrayList();
				Stack<Location> stack = this.currDistrict.getLocations().stackLevelOrder();
				while (!stack.isEmpty()) {
					String nameString = stack.pop().getLocationName();
					options1.add(nameString);
				}
				ComboBox<String> comboBox = new ComboBox<>(options1);
				comboBox.setPromptText("Choose Location");
				comboBox.setOnAction(e1 -> {
					String selectedOption = comboBox.getValue();
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setTitle("Confirmation Dialog");
					alert1.setHeaderText("Look, you are about to update a Location");
					alert1.setContentText("Are you sure ?");
					String name = updateLocationTF.getText().trim();
					if (!name.equals("")) {
						if (currDistrict != null) {
							if (this.currDistrict.getLocations().find(new Location(name)) == null) {
							Optional<ButtonType> result = alert1.showAndWait();
							if (result.get() == ButtonType.OK) {
								if (selectedOption != null) {
									TNode<Location> dLocation = this.currDistrict.getLocations().find(new Location(selectedOption));
									if (dLocation != null ) {
										this.currDistrict.getLocations().delete(dLocation.getData());
										dLocation.getData().setLocationName(name);
										this.currDistrict.getLocations().insert(dLocation.getData());
										this.locationQueue.clear();
										this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
										this.locationStack = new Stack<>();
										if (!this.locationQueue.isEmpty()) {
											this.currLocation = this.locationQueue.dequeue();
										}else {
											this.currLocation = null;
										}
										initialize(currLocation);
										updateLocationLabel.setText("Updated Successfuly");
									} else {
										updateLocationLabel.setText("Location Not Found");
									}
								}
							} else {
								updateLocationLabel.setText("Updated Not Completed");
							}
							} else {
								label.setText("Already Exist");
							}
						
						}else {
							updateLocationTF.setText("No Location");
						}
					} else {
						label.setText("Enter Name");
					}
				});
				VBox vbox = new VBox(label, updateLocationTF, comboBox);
				vbox.setPrefWidth(200);
				vbox.setPrefHeight(200);
				vbox.setAlignment(Pos.CENTER);
				alert.getDialogPane().setContent(vbox);
				alert.showAndWait();
				}else {
					updateLocationLabel.setText("No Location Found");
				}
			});

			deleteLocationBtn.setOnAction(e -> {
				if (!this.records.isEmpty() && !this.currDistrict.getLocations().isEmpty()) {
					deleteLocationLabel.setText("");
				Label label = new Label("Select Location: ");
				ObservableList<String> options1 = FXCollections.observableArrayList();
				Stack<Location> stack1 = this.currDistrict.getLocations().stackLevelOrder();
				while (!stack1.isEmpty()) {
					String nameString = stack1.pop().getLocationName();
					options1.add(nameString);
				}
				ComboBox<String> comboBox = new ComboBox<>(options1);
				comboBox.setPromptText("Choose Location");
				comboBox.setOnAction(e1 -> {
					String selectedOption = comboBox.getValue();
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setTitle("Confirmation Dialog");
					alert1.setHeaderText("Look, you are about to delete a Location");
					alert1.setContentText("Are you sure ?");
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						if (selectedOption != null) {
							Location dLocation = this.currDistrict.getLocations().delete(new Location(selectedOption));
							if (dLocation != null) {
								this.locationQueue.clear();
								this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
								this.locationStack = new Stack<>();
								if (!this.locationQueue.isEmpty()) {
									this.currLocation = this.locationQueue.dequeue();
								}else {
									this.currLocation = null;
								}
								initialize(currLocation);
								deleteLocationLabel.setText("Deleted Successfuly");
							} else {
								deleteLocationLabel.setText("Location Not Found");
							}
						}
					} else {
					}
				});
				VBox vbox = new VBox(label, comboBox);
				vbox.setPrefWidth(200);
				vbox.setPrefHeight(200);
				vbox.setAlignment(Pos.CENTER);
				alert.getDialogPane().setContent(vbox);
				alert.showAndWait();
			}else {
				deleteLocationLabel.setText("No Location Found");
			}
			});

			nextLocation.setOnAction(e -> {
				if (!this.locationQueue.isEmpty()) {
					Location location= locationQueue.dequeue();
					if (currLocation != null && locationStack.peek() != currLocation && location != null) {
						this.locationStack.push(currLocation);
					}
					this.currLocation = location;
					initialize(currLocation);
				}

			});

			previousLocation.setOnAction(e -> {
				if (!locationStack.isEmpty()) {
					Queue<Location> tempQueue = new Queue<>();
					tempQueue.enqueue(currLocation);
					while (!this.locationQueue.isEmpty()) {
						tempQueue.enqueue(this.locationQueue.dequeue());
					}
					while (!tempQueue.isEmpty()) {
						this.locationQueue.enqueue(tempQueue.dequeue());
					}
					this.currLocation = locationStack.pop();
					initialize(currLocation);
				}
			});
			
			loadFirst.setOnAction(e->{
				MartyrScreen martyrScreen = new MartyrScreen(this.records, currDistrict, currLocation);
				scene1 = new Scene(martyrScreen.getBorderPane(),500, 500);
				start(new Stage());
			});
			scene1 = new Scene(finalVBox,500, 500);
		} catch (Exception e) {

		}
	}
	public void initialize(Location location) {
		if (location != null) {
			int totalNum =0;
			int maxNum =0;
			int ocurr = 0;
			CustomDate date = null;
			LocationName.setText(location.getLocationName());
				Stack<CustomDate> stack3 = location.getDates().toStack();
				while (!stack3.isEmpty()) {
					CustomDate date1 = stack3.pop();
					ocurr = date1.getMartyrs().length();
					totalNum += ocurr;
					if (ocurr > maxNum) {
						maxNum = ocurr;
						date = date1;
					}
			}
			totalNumOfMartyr.setText("Total Number Of Martyrs: " + totalNum);
			if (date != null) {
				maxNumOfMartyr.setText(date.getDate() + " Has The Maximum Number Of Martyrs");
				TNode<CustomDate> node = location.getDates().smallest();
				if (node != null) {
					earliestDate.setText("Earliest Date: " + node.getData().getDate());
				}
				TNode<CustomDate> node1 = location.getDates().largest();
				if (node1 != null) {
					latestDate.setText("Latest Date: " + node1.getData().getDate());
				}
			}
		} else {
			LocationName.setText("");
			maxNumOfMartyr.setText("");
			earliestDate.setText("");
			latestDate.setText("");
			totalNumOfMartyr.setText("");
		}
	}
	public Scene getScene() {
		return scene1;
	}
	@Override
	public void start(Stage primaryStage){
		loadFirst.setOnAction(e->{
			MartyrScreen martyrScreen = new MartyrScreen(this.records, currDistrict, currLocation);
			scene1 = new Scene(martyrScreen.getBorderPane(),500, 500);
			primaryStage.hide();
			primaryStage.setScene(scene1);
			primaryStage.show();
		});
		primaryStage.setScene(scene1);
		primaryStage.show();
	}}
