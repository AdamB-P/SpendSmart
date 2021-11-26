package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.AlertWindow;
import application.model.Income;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/*
 * IncomeSalaryController provides functionality to the IncomeSalary.fxml scene.
 * The user can input their salary and taxes to calculate their monthly income.
 * @author: Xavier Mejia - MKD107 & Austin Thrash - FFK221
 */
public class IncomeSalaryController {
	
	@FXML private AnchorPane mainPane;
	
	@FXML private Button returnBtn;
	@FXML private Button saveBtn;
	@FXML private Button clearBtn;
	@FXML private Button calculateBtn;
	@FXML private Button calculate2Btn;
	
	@FXML private TextField salaryText;
	@FXML private TextField taxText;
	
	@FXML private Label labelOne;
	@FXML private Label labelTwo;
	
	
	//Loads and sets data with user info if available
	public void initialize() throws IOException {
		Income userInfo = new Income();
		userInfo.loadData("data/dataIncome.csv");
		ArrayList<String> userInfoL = userInfo.getInfo();
		
		double payPerHour = Double.valueOf(userInfoL.get(1));
		double hoursPerWeek = Double.valueOf(userInfoL.get(2));
		double salary = payPerHour * hoursPerWeek * 52.0;

		String salaryString = String.format("%.2f", salary);	
		String taxesString = String.format("%.2f", Double.valueOf(userInfoL.get(4)));
		
		//checking to see if user has calculated any information
		if( userInfoL.get(0).equals("0") && userInfoL.get(1).equals("0")) {
			//Do nothing
		} else {
			salaryText.setText(String.valueOf(salaryString));
			labelOne.setText(userInfoL.get(3));
			taxText.setText(taxesString);
			labelTwo.setText(userInfoL.get(5));
		}
	}
	
	
	@FXML public void handleReturn(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/Main.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	//Saves user input to user file
	@FXML public void handleSave(ActionEvent event) throws IOException{
		try {
			//To stop user from saving bad info
			if(labelTwo.getText().equals("Awaiting Calculation...")) {
				throw new Exception("Nothing to save");
			}
			User userInfo = new User();
			userInfo.loadData("data/data.csv");
			ArrayList<String> userInfoList = userInfo.getData();
		
			FileWriter myWriter = new FileWriter("data/data.csv");
			myWriter.write(labelTwo.getText() + "," + userInfoList.get(1) + "," + userInfoList.get(2));
			myWriter.close();
		} catch (Exception excpt){
			System.out.println(excpt.getMessage());
		}
	}
	
	//Clearing file and text Fields
	@FXML public void handleClear(ActionEvent event) throws IOException{
		FileWriter myWriter = new FileWriter("data/dataIncome.csv");
		myWriter.write("0,0,0,0,0,0");
		myWriter.close();
		
		User userInfo = new User();
		userInfo.loadData("data/data.csv");
		ArrayList<String> userInfoL = userInfo.getData();
		
		FileWriter myWriter2 = new FileWriter("data/data.csv");
		myWriter2.write("0" + "," + userInfoL.get(1) + "," + userInfoL.get(2));
		myWriter2.close();
		
		salaryText.setText(null);
		labelOne.setText("Awaiting Calculation...");
		taxText.setText(null);
		labelTwo.setText("Awaiting Calculation...");
	}
	
	/*
	 * Gets salary from user and converts that to an hourly pay to satisfy current CSV structure.
	 * This calculation is performed assuming hours per week = 40 (doesn't seem to really matter in this case).
	 */
	@FXML
	public void handleCalculation(ActionEvent event) throws IOException{
		double salary;
		try {
			if(salaryText.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			salary = Double.valueOf(salaryText.getText());
			
			String result = String.format("%.2f", salary);
			labelOne.setText(result);
		}
		catch (Exception excpt){
			AlertWindow.display("Error", "Please enter values.");
			System.out.println(excpt.getMessage());
		}
		
	}
	//Calculates monthly salary after taxes
	//Could possibly implement tax calculator but has a few variables
	@FXML
	public void handleCalculation2(ActionEvent event) throws IOException{
		double payPerHour, hoursPerWeek, weeksPerYear, taxes, salary, sum;
		
		try {
			if(salaryText.getText().equals(null) || taxText.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			
			salary = Double.valueOf(salaryText.getText());
			taxes = Double.valueOf(taxText.getText());	
		
			sum = salary - taxes;
			sum = sum/12;
		
			hoursPerWeek = 40;
			weeksPerYear = 52;
			payPerHour = salary / (weeksPerYear * hoursPerWeek);
			
			String result = String.format("%.2f", sum);
			labelTwo.setText(result);
		
			//Save data
			FileWriter myWriter = new FileWriter("data/dataIncome.csv");
			myWriter.write("1," + String.valueOf(payPerHour) + "," + String.valueOf(hoursPerWeek) + "," + labelOne.getText() + "," + taxText.getText() + "," + result);
			myWriter.close();
		}
		catch (Exception excpt){
			AlertWindow.display("Error", "Please enter values.");
			System.out.println(excpt.getMessage());
		}
		
		
	} 
}
