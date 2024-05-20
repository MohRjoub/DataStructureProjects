package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AlertShow implements EventHandler<ActionEvent> {

	CustomList<Martyr>records;
	public AlertShow(CustomList<Martyr>records) {
		this.records=records;
	}
	@Override
	public void handle(ActionEvent arg0) {
		ButtonType doneButton = new ButtonType("Done");
		Alert alert = new Alert(Alert.AlertType.NONE, "", doneButton);
		alert.setTitle("Number Of Martyrs");
		Label label2 = new Label();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(label2);
		TextField textField = new TextField();
		Button button = new Button("Show Numbers");
		HBox hBox = new HBox(textField, button);
		hBox.setSpacing(5);
		borderPane.setTop(hBox);
		alert.getDialogPane().setContent(borderPane);
		ArrayList<String> choices = new ArrayList<>();
		choices.addAll(0, Arrays.asList("Display by Age", "Display by Gender", "Display by Date",
				"Display by Age and Gender", "Display by Age and Date", "Display by Gender and Date"));
		ChoiceDialog<String> dialog = new ChoiceDialog<>("Display by Age", choices);
		dialog.setTitle("Display Martyrs");
		dialog.setHeaderText("Ways to Number of Martyrs");
		dialog.setContentText("Choose your Opption:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get().equals("Display by Age")) {
				textField.setPromptText("Enter Age");
				button.setOnAction(e1 -> {
					int num = 0;
					for (int i = 0; i < records.count; i++) {
						if (records.get(i).getAge() == Integer.parseInt(textField.getText().trim())) {
							num++;
						}
					}
					label2.setText(num + "");
				});
				alert.showAndWait();
			} else if (result.get().equals("Display by Gender")) {
				textField.setPromptText("Enter Gender");
				button.setOnAction(e1 -> {
					int num = 0;
					for (int i = 0; i < records.count; i++) {
						if (records.get(i).getGender() == textField.getText().trim().charAt(0)) {
							num++;
						}
					}
					label2.setText(num + "");
				});
				alert.showAndWait();
			} else if (result.get().equals("Display by Date")) {
				textField.setPrefColumnCount(15);
				textField.setPromptText("Enter Date ex: 10/31/2015");
				button.setOnAction(e1 -> {
					int num = 0;
					for (int i = 0; i < records.count; i++) {
						if (records.get(i).getDateOfDeath().trim() == textField.getText().trim()) {
							num++;
						}
					}
					label2.setText(num + "");
				});
				alert.showAndWait();
			}
		}
	}
	
}
