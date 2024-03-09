package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {
	
	private CustomList<Martyr> records = new CustomList<>(16);
	@Override
	public void start(Stage primaryStage) {
		try {
			FileChooser fil_chooser = new FileChooser();
            Label label = new Label("No files selected");
            Button open = new Button("Open File");
            VBox openFileBox = new VBox(30, label, open);
            openFileBox.setAlignment(Pos.CENTER);
			Button addBtn = new Button("Add");
			TextField dataTF = new TextField();
			dataTF.setPromptText("Name,Age,EventLocation,DateOfDeath,Gender");
			dataTF.setPrefColumnCount(25);
			Label addRes = new Label();
			HBox addBox = new HBox(addBtn,dataTF,addRes);
			addBox.setAlignment(Pos.CENTER);
			addBox.setSpacing(6);
			Button deleteBtn = new Button("Delete");
			TextField nameTF = new TextField();
			nameTF.setPromptText("Name");
			Label res = new Label();
			HBox deleteBox = new HBox(deleteBtn,nameTF,res);
			deleteBox.setAlignment(Pos.CENTER);
			deleteBox.setSpacing(2);
			Button findBtn = new Button("Search");
			TextField findNameTF = new TextField();
			findNameTF.setPromptText("Name");
			Label findRes = new Label();
			HBox findBox = new HBox(findBtn,findNameTF,findRes);
			findBox.setAlignment(Pos.CENTER);
			findBox.setSpacing(7);
			Button displayBtn=new Button("Display NUM of Martyrs");
			VBox finalBox = new VBox(addBox,deleteBox,findBox,displayBtn);
			finalBox.setAlignment(Pos.CENTER);
			finalBox.setSpacing(5);
			Scene scene = new Scene(finalBox,900,500);
            open.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                	fil_chooser.setInitialDirectory(new File("C:\\Users\\ACTC\\Downloads"));
                    File file = fil_chooser.showOpenDialog(primaryStage);

                    if (file != null) {
                        label.setText(file.getName() + "  selected");
                        Scanner in;
        				try {
        					in = new Scanner(file);
        					in.nextLine();
        					while (in.hasNext()) {
        						String line = in.nextLine().trim();
        						records.Add( new Martyr(line.split(",")[0],line.split(",")[1].trim(),
        								line.split(",")[2],line.split(",")[3],line.split(",")[4].trim().charAt(0)));
        					}
        				} catch (FileNotFoundException e1) {
        					System.out.println(e1.getMessage());
        				}
        				primaryStage.setScene(scene);
        				primaryStage.show();
                    }
                 //   records.travers();
                }
            });
            
            addBtn.setOnAction(e->{
				addRes.setText("");
				//(Name, Age, Event location, Date of death, and Gender)
				String[] data = dataTF.getText().trim().split(",");
				if (data.length==5) {
					try {
						boolean found = false;
						Martyr martyr = new Martyr(data[0].trim(),data[1].trim(),data[2].trim(),data[3].trim(),data[4].trim().charAt(0));
						for(int i = 0; i <records.count; i++) {
						if (martyr.compareTo(records.get(i))==0 && martyr.getGender() == records.get(i).getGender()) {							
							found=true;
							}
						}
						if (!found) {
							records.Add(martyr);			
							System.out.println(records.get(records.count-1));
							addRes.setText("Added Successfuly");
						}
						else {
							addRes.setText("Martyr Already Exist");
							System.out.print("Martyr Already Exist");
						}
					
					}catch (Exception e1) {
						addRes.setText("Added UnSuccessfuly");
						System.out.println("An Error Occurd");
					}
				}else {
					addRes.setText("Added UnSuccessfuly");
				}
			});

			deleteBtn.setOnAction(e -> {
				res.setText("");
				String name = nameTF.getText();
				if (name != "") {
					Martyr pers = new Martyr(name, "20", name, name, 'M');
					int index = records.find(pers);
					System.out.println(index);
					if (index != -1) {
							records.delete(pers);
							res.setText("Deleted Successfuly");
					} else
						res.setText("Not Found");
						
					
				} else {
					res.setText("Deleted UnSuccessfuly");
				}
			});
			findBtn.setOnAction(e -> {
				findRes.setText("");
				String name = findNameTF.getText().trim();
				if (!"".equals(name)) {
					int index = records.find(new Martyr(name, "20", name, name, 'M'));
					if (index != -1) {
						findRes.setText(records.get(index).toString());
					} else
						findRes.setText("Not Found");
				}
			});
			
			displayBtn.setOnAction(new AlertShow(records));
			Scene intialScene = new Scene(openFileBox,400,400);
			primaryStage.setTitle("Martyrs Info");
			primaryStage.setScene(intialScene);
			primaryStage.show();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
