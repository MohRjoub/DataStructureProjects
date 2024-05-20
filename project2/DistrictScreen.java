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

public class DistrictScreen extends Application{
	private ButtonType doneButton = new ButtonType("Done");
	private Alert alert = new Alert(Alert.AlertType.NONE, "", doneButton);
	private TextField addDistrictTF = new TextField();
	private Button addDistrictBtn = new Button("Add New District");
	private Label addDistrictLabel = new Label();
	private HBox addHBox = new HBox(addDistrictTF, addDistrictBtn, addDistrictLabel);
	private Button updateDistrictBtn = new Button("Update District");
	private Label updateDistrictLabel = new Label();
	private HBox updateHBox = new HBox(updateDistrictBtn, updateDistrictLabel);
	private Button deleteDistrictBtn = new Button("Delete District");
	private Label deleteDistrictLabel = new Label();
	private HBox deleteHBox = new HBox(deleteDistrictBtn, deleteDistrictLabel);
	private Label districtName = new Label();
	private Label numOfMartyr = new Label();
	private Button nextDistrict = new Button("Next District");
	private Button previousDistrict = new Button("Previous District");
	private HBox hBox3 = new HBox(previousDistrict, nextDistrict);
	private Button loadFirst = new Button("Load First Location");
	private VBox finalVBox = new VBox(districtName, numOfMartyr, hBox3, addHBox, updateHBox, deleteHBox, loadFirst);
	private BSTree<District> records;
	private Queue<District> queue;
	private Stack<District> stack;
	private District currDistrict;
	private Scene scene1;

	public DistrictScreen(BSTree<District> records) {
		try {
			this.records = records;
			this.queue = this.records.toQueue();
			this.stack = new Stack<>();
			if (!this.queue.isEmpty()) {
				this.currDistrict = this.queue.dequeue();
				initialize(currDistrict);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Scene getScene() {
		return scene1;
	}

	public void initialize(District district) {
		if (district != null) {
			int total = 0;
			Queue<Location> locations = district.getLocations().toQueue();
			districtName.setText(district.getDistrictName());
			while (!locations.isEmpty()) {
				Stack<CustomDate> stack3 = locations.dequeue().getDates().toStack();
				while (!stack3.isEmpty()) {
					total += stack3.pop().getMartyrs().length();
				}
			}
			numOfMartyr.setText("Total number of martyrs: " + total);
		} else {
			districtName.setText("");
			numOfMartyr.setText("");

		}
	}

	@Override
	public void start(Stage primaryStage){
		try {
			addDistrictTF.setPromptText("Enter District Name");
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

			addDistrictBtn.setOnAction(e -> {
				String Name = addDistrictTF.getText().trim();
				if (!Name.equals("")) {
					TNode<District> districtNode = this.records.find(new District(Name));
					if (districtNode == null) {
						District district = new District(Name);
						this.records.insert(district);
						addDistrictLabel.setText("Added Successfuly");
						if (currDistrict != null) {
							int comp = currDistrict.compareTo(district);
							if (comp < 0) {
								Queue<District> tempQueue = new Queue<>();
								District district1 = null;
								while (!this.queue.isEmpty() && (district1 = this.queue.dequeue()).compareTo(district) < 0) {
									tempQueue.enqueue(district1);
								}
								tempQueue.enqueue(district);
								if (district1 != null && tempQueue.getFront().compareTo(district1) != 0) {
									tempQueue.enqueue(district1);
								}
								while (!this.queue.isEmpty()) {
									tempQueue.enqueue(this.queue.dequeue());
								}
								while (!tempQueue.isEmpty()) {
									this.queue.enqueue(tempQueue.dequeue());
								}
							} else {
								Stack<District> tempStack = new Stack<>();
								District district1 = null;
								while (!stack.isEmpty() && (district1 = stack.pop()).compareTo(district) > 0) {
									tempStack.push(district1);
								}
								tempStack.push(district);
								if (district1 != null && tempStack.peek().compareTo(district1) != 0) {
									tempStack.push(district1);
								}
								while (!this.stack.isEmpty()) {
									tempStack.push(this.stack.pop());
								}
								while (!tempStack.isEmpty()) {
									this.stack.push(tempStack.pop());
								}
							}
						} else {
							this.queue.enqueue(district);
						}
					} else {
						addDistrictLabel.setText("Alreade Exist");
					}
				} else {
					addDistrictLabel.setText("Please Enter A Valid Input");
				}
			});

			updateDistrictBtn.setOnAction(e -> {
				if (!this.records.isEmpty()) {
					updateDistrictLabel.setText("");
				Label label = new Label("Select District: ");
				TextField updateDistrictTF = new TextField();
				updateDistrictTF.setPromptText("Enter New Name");
				ObservableList<String> options = FXCollections.observableArrayList();
				Stack<District> queue1 = this.records.toStack();
				while (!queue1.isEmpty()) {
					String nameString = queue1.pop().getDistrictName();
					options.add(nameString);
				}
				ComboBox<String> comboBox = new ComboBox<>(options);
				comboBox.setPromptText("Choose District");
				comboBox.setOnAction(e1 -> {
					String selectedOption = comboBox.getValue();
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setTitle("Confirmation Dialog");
					alert1.setHeaderText("Look, you are about to update a Distirct");
					alert1.setContentText("Are you sure ?");
					String name = updateDistrictTF.getText().trim();
					if (!name.equals("")) {
						if (this.records.find(new District(name)) == null) {
						Optional<ButtonType> result = alert1.showAndWait();
						if (result.get() == ButtonType.OK) {
							if (selectedOption != null) {
								TNode<District> dDistrict = this.records.find(new District(selectedOption));
								if (dDistrict != null ) {
									this.records.delete(dDistrict.getData());
									dDistrict.getData().setDistrictName(name);
									this.records.insert(dDistrict.getData());
									this.queue.clear();
									this.queue = this.records.toQueue();
									this.stack = new Stack<>();
									if (!this.queue.isEmpty()) {
										this.currDistrict = this.queue.dequeue();
										initialize(currDistrict);
									}
									updateDistrictLabel.setText("Updated Successfuly");
								} else {
									updateDistrictLabel.setText("District Not Found");
								}
							}
						} else {
							updateDistrictLabel.setText("Updated Not Completed");
						}
						} else {
							label.setText("Already Exist");
						}
					} else {
						label.setText("Enter Name");
					}
				});
				VBox vbox = new VBox(label, updateDistrictTF, comboBox);
				vbox.setPrefWidth(200);
				vbox.setPrefHeight(200);
				vbox.setAlignment(Pos.CENTER);
				alert.getDialogPane().setContent(vbox);
				alert.showAndWait();
				}else {
					updateDistrictLabel.setText("No District Found");
				}
			});

			deleteDistrictBtn.setOnAction(e -> {
				if (!this.records.isEmpty()) {
					deleteDistrictLabel.setText("");
				Label label = new Label("Select District: ");
				ObservableList<String> options = FXCollections.observableArrayList();
				Stack<District> stack1 = this.records.toStack();
				while (!stack1.isEmpty()) {
					String nameString = stack1.pop().getDistrictName();
					options.add(nameString);
				}
				ComboBox<String> comboBox = new ComboBox<>(options);
				comboBox.setPromptText("Choose District");
				comboBox.setOnAction(e1 -> {
					String selectedOption = comboBox.getValue();
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setTitle("Confirmation Dialog");
					alert1.setHeaderText("Look, you are about to delete a Distirct");
					alert1.setContentText("Are you sure ?");
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						if (selectedOption != null) {
							District dDistrict = this.records.delete(new District(selectedOption));
							if (dDistrict != null) {
								this.queue.clear();
								this.queue = this.records.toQueue();
								this.stack = new Stack<>();
								if (!this.queue.isEmpty()) {
									this.currDistrict = this.queue.dequeue();
								}else {
									this.currDistrict = null;
								}
								initialize(currDistrict);
								deleteDistrictLabel.setText("Deleted Successfuly");
							} else {
								deleteDistrictLabel.setText("District Not Found");
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
				deleteDistrictLabel.setText("No District Found");
			}
			});

			nextDistrict.setOnAction(e -> {
				if (!this.queue.isEmpty()) {
					District district = queue.dequeue();
					if (currDistrict != null && stack.peek() != currDistrict && district != null) {
						this.stack.push(currDistrict);
					}
					this.currDistrict = district;
					initialize(currDistrict);
				}

			});

			previousDistrict.setOnAction(e -> {
				if (!stack.isEmpty()) {
					Queue<District> tempQueue = new Queue<>();
					tempQueue.enqueue(currDistrict);
					while (!this.queue.isEmpty()) {
						tempQueue.enqueue(this.queue.dequeue());
					}
					while (!tempQueue.isEmpty()) {
						this.queue.enqueue(tempQueue.dequeue());
					}
					this.currDistrict = stack.pop();
					initialize(currDistrict);
				}
			});

			loadFirst.setOnAction(e -> {
				if (currDistrict != null) {
					locationScreen locationScreen = new locationScreen(records, currDistrict);
					primaryStage.setScene(locationScreen.getScene());
					primaryStage.show();
					}
			});
			 scene1 = new Scene(finalVBox,500, 500);
			 primaryStage.setScene(scene1);
			 primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
