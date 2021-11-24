package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * PayTypeController provides functionality to the PayType.fxml scene.
 * Here, the user chooses whether they are paid at an hourly rate or yearly salary.
 * Their choice will take them to the right monthly income tool.
 * @author: Xavier Mejia - MKD107
 */
public class PayTypeController {
	
	private AnchorPane mainPane;
	
	//Buttons to be used by the user
	@FXML private Button returnBtn;
	@FXML private Button hourlyBtn;
	@FXML private Button salaryBtn;
	
	/*
	 * handleReturn() returns the user to the main scene (Main.fxml)
	 * @param event: passed when user clicks on returnBtn
	 */
	@FXML public void handleReturn(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/Main.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	/*
	 * handleHourly() takes the user to the default hourly pay tool (Income.fxml)
	 * @param event: passed when user clicks on hourlyBtn
	 */
	@FXML public void handleHourly(ActionEvent event) throws IOException {
		URL url = new File("src/application/view/Income.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();		
	}
	
	/*
	 * handleSalary() takes the user to the salary tool (IncomeSalary.fxml)
	 * @param event: passed when user clicks on salaryBtn
	 */
	@FXML public void handleSalary(ActionEvent event) throws IOException {
		URL url = new File("src/application/view/IncomeSalary.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
}
