package application.model;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/*
 * AlertWindow is an object that, as it name suggests, gives an alert within a box
 * It contains a single class method display()
 * @author: Xavier Mejia / mkd107
 */
public class AlertWindow {
	/*
	 * display() takes in 2 parameters which tell AlertBox what to display
	 * @param title: title of alert window
	 * @param message: message of alert window
	 */
	public static void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(300);
		window.setHeight(150);
		
		Label label = new Label();
		label.setText(message);
		
		Button closeButton = new Button("Ok");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene alertScene = new Scene(layout);
		window.setScene(alertScene);
		window.showAndWait();
	}
}
