package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.util.converter.IntegerStringConverter;

public class MartyrScreen extends Application {
	private ButtonType doneButton = new ButtonType("Done");
	private Alert alert = new Alert(Alert.AlertType.NONE, "", doneButton);
	private TextField addMartyrTF = new TextField();
	private Button addMartyrToCurrBtn = new Button("Add New Martyr Current Date");
	private Button addMartyrToAnotherBtn = new Button("Add New Martyr To Another Date");
	private HBox addHBox = new HBox(addMartyrToCurrBtn, addMartyrToAnotherBtn);
	private Label updateMartyrLabel = new Label();
	private Button deleteMartyrBtn = new Button("Delete Martyr");
	private Label deleteMartyrLabel = new Label();
	private HBox deleteHBox = new HBox(deleteMartyrBtn, deleteMartyrLabel);
	private Label districtName = new Label();
	private Label locationName = new Label();
	private Label dateString = new Label();
	private Label avgAge = new Label();
	private Label youngest = new Label();
	private Label oldest = new Label();
	private Button search = new Button("Search For Martyr");
	private TextField searchTF = new TextField();
	private HBox searchHBox = new HBox(search, searchTF);
	private Button nextDate = new Button("Next Date");
	private Button previousDate = new Button("Previous Date");
	private Button save = new Button("Save changes");
	private HBox hBox3 = new HBox(previousDate, nextDate);
	private ComboBox<String> districsBox = new ComboBox<>();
	private ComboBox<String> locationsBox = new ComboBox<>();
	private HBox hBox4 = new HBox(districsBox, locationsBox);
	private TableView<Martyr> tableView;
	private VBox finalVBox = new VBox(districtName, locationName, dateString, avgAge, youngest, oldest, hBox3, addHBox,
			 deleteHBox, searchHBox, hBox4, save, updateMartyrLabel);
	private BorderPane borderPane;
	private BSTree<District> records;
	private Queue<District> queue;
	private Stack<District> stack;
	private District currDistrict;
	private Queue<Location> locationQueue;
	private Stack<Location> locationStack;
	private Location currLocation;
	private Queue<CustomDate> customDateQueue;
	private Stack<CustomDate> customDateStack;
	private CustomDate currDate;
	private Scene scene1;
	public MartyrScreen(BSTree<District> records,District loadDistrict, Location loadLocation) {
		try {
			borderPane = new BorderPane();
			addMartyrTF.setPromptText("Enter Martyr Name");
			addHBox.setAlignment(Pos.CENTER);
			addHBox.setSpacing(5);
			deleteHBox.setAlignment(Pos.CENTER);
			deleteHBox.setSpacing(5);
			hBox3.setAlignment(Pos.CENTER);
			hBox3.setSpacing(5);
			hBox4.setAlignment(Pos.CENTER);
			hBox4.setSpacing(5);
			searchHBox.setAlignment(Pos.CENTER);
			searchHBox.setSpacing(5);
			finalVBox.setAlignment(Pos.CENTER);
			finalVBox.setSpacing(10);
			borderPane.setCenter(finalVBox);
			tableView = new TableView<>();
			TableColumn<Martyr, String> name = new TableColumn<>("Name");
			name.setCellValueFactory(new PropertyValueFactory<>("Name"));
			TableColumn<Martyr, Integer> age = new TableColumn<>("Age");
			age.setCellValueFactory(new PropertyValueFactory<>("Age"));
			TableColumn<Martyr, String> gender = new TableColumn<>("Gender");
			gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
			tableView.getColumns().addAll(name, age, gender);
	        tableView.setEditable(true);
	        name.setCellFactory(TextFieldTableCell.forTableColumn());
	        age.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	        gender.setCellFactory(ComboBoxTableCell.forTableColumn("M", "F"));
	        borderPane.setBottom(tableView);
			this.records = records;
			this.queue = this.records.toQueue();
			this.locationQueue = new Queue<>();
			this.customDateQueue = new Queue<>();
			this.stack = new Stack<>();
			this.locationStack = new Stack<>();
			this.customDateStack = new Stack<>();
			if (!this.queue.isEmpty() && loadDistrict == null && loadLocation == null) {
				this.currDistrict = this.queue.dequeue();
				this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
				if (!this.locationQueue.isEmpty()) {
					this.currLocation = this.locationQueue.dequeue();
					this.customDateQueue = this.currLocation.getDates().toQueue();
					this.currDate = this.customDateQueue.dequeue();
					initialize(currDate);
				}
			}else if (!this.queue.isEmpty() && loadDistrict != null && loadLocation != null) {
				this.currDistrict = loadDistrict;
				this.locationQueue = loadDistrict.getLocations().queueLevelOrder();
				this.currLocation = loadLocation;
				if (!this.locationQueue.isEmpty()) {
					this.customDateQueue = loadLocation.getDates().toQueue();
					this.currDate = this.customDateQueue.dequeue();
					initialize(currDate);
				}
			}
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
			ObservableList<String> options1 = FXCollections.observableArrayList();
			if (this.currLocation != null) {
				Stack<Location> stack = this.currDistrict.getLocations().stackLevelOrder();
				while (!stack.isEmpty()) {
					String nameString = stack.pop().getLocationName();
					options1.add(nameString);
				}
				locationsBox.setItems(options1);
			}
			locationsBox.setPromptText("Choose Location");

			districsBox.setOnAction(e -> {
				String selectedOption = districsBox.getValue();
				if (!selectedOption.equals("")) {
					TNode<District> node = this.records.find(new District(selectedOption));
					if (node != null) {
						this.currDistrict = node.getData();
						this.locationQueue.clear();
						this.locationQueue = this.currDistrict.getLocations().queueLevelOrder();
						this.locationStack = new Stack<>();
						if (!this.locationQueue.isEmpty()) {
							this.currLocation = this.locationQueue.dequeue();
							ObservableList<String> options2 = FXCollections.observableArrayList();
							if (this.currLocation != null) {
								Stack<Location> stack = this.currDistrict.getLocations().stackLevelOrder();
								while (!stack.isEmpty()) {
									String nameString = stack.pop().getLocationName();
									options2.add(nameString);
								}
								locationsBox.setItems(options2);
							}
							locationsBox.setPromptText(currLocation.getLocationName());
							this.customDateQueue.clear();
							this.customDateQueue = this.currLocation.getDates().toQueue();
							this.customDateStack = new Stack<>();
							if (!this.customDateQueue.isEmpty()) {
								this.currDate = this.customDateQueue.dequeue();
								initialize(currDate);
							}
						}
					}
				}
			});
			locationsBox.setOnAction(e -> {
				String selectedOption = locationsBox.getValue();
				if (selectedOption != null && !selectedOption.equals("") && this.currDistrict != null) {
					TNode<Location> node = this.currDistrict.getLocations().find(new Location(selectedOption));
					if (node != null) {
						if (!this.locationQueue.isEmpty()) {
							this.currLocation = node.getData();
							this.customDateQueue.clear();
							this.customDateQueue = this.currLocation.getDates().toQueue();
							this.customDateStack = new Stack<>();
							if (!this.customDateQueue.isEmpty()) {
								this.currDate = this.customDateQueue.dequeue();
								initialize(currDate);
							}
						}
					}
				}
			});
			addMartyrToCurrBtn.setOnAction(e->{
				Label nameLabel = new Label("Full Name:");
				TextField nameField = new TextField();
				Label ageLabel = new Label("Age:");
				TextField ageField = new TextField();
				Label genderLabel = new Label("Gender:");
				TextField genderField = new TextField();
				Label AddState = new Label();
				Button submitButton = new Button("Submit");
				GridPane gridPane = new GridPane();
				gridPane.setPadding(new Insets(20));
				gridPane.setVgap(10);
				gridPane.setHgap(10);
				gridPane.setPrefWidth(400);
				gridPane.add(nameLabel, 0, 0);
				gridPane.add(nameField, 1, 0);
				gridPane.add(ageLabel, 0, 1);
				gridPane.add(ageField, 1, 1);
				gridPane.add(genderLabel, 0, 2);
				gridPane.add(genderField, 1, 2);
				gridPane.add(submitButton, 1, 3);
				gridPane.add(AddState, 1, 4);
				alert.setTitle("Add Martyr");
				alert.setHeaderText("Add Customer");
				alert.getDialogPane().setContent(gridPane);
				submitButton.setOnAction(e1 -> {
					try {
						String nameString = nameField.getText();
						String ageString = ageField.getText();
						String genderString = genderField.getText().trim();
						if (nameString.isEmpty() || ageString.isEmpty() || genderString.isEmpty() || 
								(genderString.charAt(0) != 'F' && genderString.charAt(0) != 'M')) {
							AddState.setText("Please fill in all fields with valid data");
						} else {
							Martyr martyr = new Martyr(nameString, ageString, genderString.charAt(0));
							Martyr martyr2 = this.currDate.getMartyrs().search(martyr);
							if (martyr2 == null) {
								this.currDate.getMartyrs().insert(martyr);
								AddState.setText("Added Successfuly");
								initialize(currDate);
							} else {
								AddState.setText("Already Exist");
							}
						}
					} catch (Exception e2) {
							AddState.setText("Please enter a valid data");
							}
				});
				alert.showAndWait();
			});
			
			addMartyrToAnotherBtn.setOnAction(e->{
				Label nameLabel = new Label("Full Name:");
				TextField nameField = new TextField();
				Label ageLabel = new Label("Age:");
				TextField ageField = new TextField();
				Label genderLabel = new Label("Gender:");
				DatePicker date = new DatePicker();
				TextField genderField = new TextField();
				Label AddState = new Label();
				Button submitButton = new Button("Submit");
				GridPane gridPane = new GridPane();
				gridPane.setPadding(new Insets(20));
				gridPane.setVgap(10);
				gridPane.setHgap(10);
				gridPane.setPrefWidth(400);
				gridPane.add(nameLabel, 0, 0);
				gridPane.add(nameField, 1, 0);
				gridPane.add(ageLabel, 0, 1);
				gridPane.add(ageField, 1, 1);
				gridPane.add(genderLabel, 0, 2);
				gridPane.add(genderField, 1, 2);
				gridPane.add(date, 1, 3);
				gridPane.add(submitButton, 1, 4);
				gridPane.add(AddState, 1, 5);
				alert.setTitle("Add Customer");
				alert.setHeaderText("Add Customer");
				alert.getDialogPane().setContent(gridPane);
				submitButton.setOnAction(e1 -> {
					try {
						String nameString = nameField.getText();
						String ageString = ageField.getText();
						String genderString = genderField.getText().trim();
						LocalDate dateOfDeath = date.getValue();
						if (nameString.isEmpty() || ageString .isEmpty() || genderString.isEmpty() ||
								(genderString.charAt(0) != 'F' && genderString.charAt(0) != 'M') || dateOfDeath == null) {
							AddState.setText("Please fill in all fields");
						} else {
							if (dateValidation(dateOfDeath.toString())) {
								CustomDate customDate = new CustomDate(dateOfDeath.toString());
								TNode<CustomDate> customDateNode = this.currLocation.getDates().find(customDate);
								if (customDateNode == null) {
									Martyr martyr = new Martyr(nameString, ageString, genderString.charAt(0));
									customDate.getMartyrs().insert(martyr);
									this.currLocation.getDates().insert(customDate);
									this.customDateQueue.clear();
									this.customDateQueue = this.currLocation.getDates().toQueue();
									this.customDateStack = new Stack<>();
									if (!this.customDateQueue.isEmpty()) {
										this.currDate = this.customDateQueue.dequeue();
										initialize(currDate);
									}
									AddState.setText("Added Successfuly");
								} else {
									AddState.setText("Date Already Exist");
								}
							} else {
								AddState.setText("Please enter a valid Date");
							}
						}
					} catch (Exception e2) {
						AddState.setText("Please enter a valid data");
					}
				});
				alert.showAndWait();
			});
			nextDate.setOnAction(e -> {
				if (!this.customDateQueue.isEmpty()) {
					CustomDate customDate = this.customDateQueue.dequeue();
					if (currDistrict != null && currLocation != null && currDate != null
							&& this.customDateStack.peek() != currDate && customDate != null) {
						this.customDateStack.push(currDate);
					}
					this.currDate = customDate;
					initialize(currDate);
				}
			});

			previousDate.setOnAction(e -> {
				if (!customDateStack.isEmpty()) {
					Queue<CustomDate> tempQueue = new Queue<>();
					tempQueue.enqueue(currDate);
					while (!this.customDateQueue.isEmpty()) {
						tempQueue.enqueue(this.customDateQueue.dequeue());
					}
					while (!tempQueue.isEmpty()) {
						this.customDateQueue.enqueue(tempQueue.dequeue());
					}
					this.currDate = this.customDateStack.pop();
					initialize(currDate);
				}
			});
			
			 tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		            if (newSelection != null) {
		                deleteMartyrBtn.setOnAction(e->{
		                	if (newSelection != null) {
								this.currDate.getMartyrs().delete(newSelection);
					            tableView.getItems().remove(newSelection);
					            initialize(currDate);
							}
		                });
		            }
				});
				name.setOnEditCommit(e -> {
					Martyr martyr = e.getRowValue();
					String newName = e.getNewValue();
					if (this.currDate.getMartyrs().search(new Martyr(newName, martyr.getAge()+"", martyr.getGender())) == null) {
						martyr.setName(newName);
						updateMartyrLabel.setText("Updated Successfly");
						initialize(currDate);
					}else {
						updateMartyrLabel.setText("Already Exist");
					}
				});
				age.setOnEditCommit(e -> {
					Martyr martyr = e.getRowValue();
					martyr.setAge(e.getNewValue()+"");
					if (this.currDate != null) {
						this.currDate.getMartyrs().update(martyr);
						updateMartyrLabel.setText("Updated Successfly");
					}
				});
				gender.setOnEditCommit(e -> {
					Martyr martyr = e.getRowValue();
					char newGender = e.getNewValue().charAt(0);
					if (newGender != martyr.getGender() && this.currDate.getMartyrs()
							.search(new Martyr(martyr.getName(), martyr.getAge() + "", newGender)) == null) {
						martyr.setGender(newGender);
						updateMartyrLabel.setText("Updated Successfly");
					} else {
						updateMartyrLabel.setText("Already Exist");
					}
				});
				
				search.setOnAction(e->{
					String nameString = searchTF.getText();
					if (!nameString.isEmpty() && this.currDate != null) {
						LinkedList<Martyr> linkedList = this.currDate.getMartyrs();
						Iterator<Martyr> iterator = linkedList.iterator();
						Stack<Martyr>tempQueue = new Stack<>();
						while (iterator.hasNext()) {
							Martyr martyr = iterator.next();
							if (martyr.getName().contains(nameString)) {
								tempQueue.push(martyr);
							}
						}
						sortStack(tempQueue);
						tableView.getItems().clear();
						while (!tempQueue.isEmpty()) {
							tableView.getItems().add(tempQueue.pop());
						}
					}
				});
				
				save.setOnAction(e->{
					try (BufferedWriter dataOutputStream = new BufferedWriter(new FileWriter(new File("newFile")));){
						Stack<District> stack = this.records.toStack();
						while (!stack.isEmpty()) {
							District district = stack.pop();
							Stack<Location> stack1 = district.getLocations().toStack();
							while (!stack1.isEmpty()) {
								Location location = stack1.pop();
								Stack<CustomDate> stack3 = location.getDates().toStack();
								while (!stack3.isEmpty()) {
									CustomDate customDate = stack3.pop();
									LinkedList<Martyr> linkedList = customDate.getMartyrs();
									Iterator<Martyr> iterator = linkedList.iterator();
									while (iterator.hasNext()) {
										Martyr martyr = iterator.next();
										Scanner scanner = new Scanner(customDate.getDate().toString());
										scanner.useDelimiter("-");
										String year = scanner.next();
										String month = scanner.next();
										String day = scanner.next();
										dataOutputStream.write(martyr.getName() + "," + month + "/" + day + "/" + year
												+ "," + location.getLocationName() + "," + district.getDistrictName()
												+ "," + martyr.getGender()+"\n");
									}
								}
							}
						}
						updateMartyrLabel.setText("Saved");
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
				});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void initialize(CustomDate customDate) {
		if (customDate != null) {
			tableView.getItems().clear();
			districtName.setText(currDistrict.getDistrictName());
			locationName.setText(currLocation.getLocationName());
			dateString.setText(customDate.getDate() + "");
			double sumAge = 0;
			double total = customDate.getMartyrs().length();
			int oldestAge = 0;
			int youngestAge = 200;
			LinkedList<Martyr> linkedList = customDate.getMartyrs();
			Iterator<Martyr> iterator = linkedList.iterator();
			Stack<Martyr>tempQueue = new Stack<>();
			while (iterator.hasNext()) {
				Martyr martyr = iterator.next();
				if (martyr.getAge() != -1) {
					sumAge += martyr.getAge();
					if (oldestAge < martyr.getAge()) {
						oldestAge = martyr.getAge();
						oldest.setText("Oldest Martyr: " + martyr.getName());
					}
					if (youngestAge > martyr.getAge()) {
						youngestAge = martyr.getAge();
						youngest.setText("Youngest Martyr: " + martyr.getName());
					}
				}
				tempQueue.push(martyr);
			}
			sortStack(tempQueue);
			while (!tempQueue.isEmpty()) {
				tableView.getItems().add(tempQueue.pop());
			}
			if(total !=0)
			avgAge.setText("Average Age For Martyrs: " + String.format("%.1f", sumAge / total));
			else {
				avgAge.setText("Average Age For Martyrs: " + 0);
				oldest.setText("");
				youngest.setText("");
			}

		} else {
			districtName.setText("");
			locationName.setText("");
			dateString.setText("");
			oldest.setText("");
			youngest.setText("");
			tableView.getItems().clear();
		}
	}

	public void sortStack(Stack<Martyr> stack) {
        Stack<Martyr> tempStack = new Stack<>();
        while (!stack.isEmpty()) {
        	Martyr temp = stack.pop();
            while (!tempStack.isEmpty() && tempStack.peek().getName().compareToIgnoreCase(temp.getName()) > 0) {
                stack.push(tempStack.pop());
            }
            tempStack.push(temp);
        }
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }


	public BorderPane getBorderPane() {
		return borderPane;
	}

	public boolean dateValidation(String data) {// method to check if the date is valid
		Scanner in = new Scanner(data.trim());
		in.useDelimiter("-");
		String yearString = in.next();
		String mounthString = in.next();
		String dayString = in.next();
		if (yearString.length() == 4 && dayString.length() <= 2 && dayString.length() > 0 && mounthString.length() <= 2
				&& mounthString.length() > 0) {
			int year = Integer.valueOf(yearString) - 1900;
			int mounth = Integer.valueOf(mounthString)-1;
			int day = Integer.valueOf(dayString);
			Date date1 = new Date();
			Date date2 = new Date(year, mounth, day);
			if (date2.compareTo(date1) <= 0 && yearString.length() == 4 && dayString.length() <= 2
					&& dayString.length() > 0 && mounthString.length() <= 2 && mounthString.length() > 0 && mounth < 12 && mounth >= 0
					&& day <= 31 && day > 0) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void start(Stage primaryStage){
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
}
