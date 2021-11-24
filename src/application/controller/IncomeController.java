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

public class IncomeController {

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Button returnBtn;
	@FXML
	private Button saveBtn;
	@FXML
	private Button calculateBtn;
	@FXML
	private Button calculate2Btn;
	@FXML
	private Button clearBtn;
	
	@FXML
	private TextField Text1;
	@FXML
	private TextField Text2;
	@FXML
	private TextField Text3;
	
	@FXML
	private Label labelOne;
	@FXML
	private Label labelTwo;
	
	//Loads and sets data with user info if available
	public void initialize() throws IOException {
		Income userInfo = new Income();
		userInfo.loadData("data/dataIncome.csv");
		ArrayList<String> userInfoL = userInfo.getInfo();
		
		String hourlyPay = String.format("%.2f", Double.valueOf(userInfoL.get(1)));
		String hoursPerWeek = String.format("%.0f", Double.valueOf(userInfoL.get(2)));
		String taxes = String.format("%.2f", Double.valueOf(userInfoL.get(4)));
		
		//checking to see if user has calculated any information
		if( userInfoL.get(0).equals("0") && userInfoL.get(1).equals("0")) {
			//Do nothing
		} else {
			Text1.setText(hourlyPay);
			Text2.setText(hoursPerWeek);
			labelOne.setText(userInfoL.get(3));
			Text3.setText(taxes);
			labelTwo.setText(userInfoL.get(5));
		}
	}
	
	@FXML
	public void handleReturn(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/Main.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	//Saves user input to user file
	@FXML
	public void handleSave(ActionEvent event) throws IOException{
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
	@FXML
	public void handleClear(ActionEvent event) throws IOException{
		FileWriter myWriter = new FileWriter("data/dataIncome.csv");
		myWriter.write("0,0,0,0,0,0");
		myWriter.close();
		
		User userInfo = new User();
		userInfo.loadData("data/data.csv");
		ArrayList<String> userInfoL = userInfo.getData();
		
		FileWriter myWriter2 = new FileWriter("data/data.csv");
		myWriter2.write("0" + "," + userInfoL.get(1) + "," + userInfoL.get(2));
		myWriter2.close();
		
		Text1.setText(null);
		Text2.setText(null);
		labelOne.setText("Awaiting Calculation...");
		Text3.setText(null);
		labelTwo.setText("Awaiting Calculation...");
	}
	//Calculates the yearly salary based off payment per hour and hours worked a week
	// uses 52 week year to calculate
	@FXML
	public void handleCalculation(ActionEvent event) throws IOException{
		double perHour, perWeek, sum;
		try {
			if(Text1.getText().equals(null) || Text2.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			perHour = Double.valueOf(Text1.getText());
			perWeek = Double.valueOf(Text2.getText());
		
			sum = (perHour * perWeek) * 52.0;
		
			String result = String.format("%.2f", sum);
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
		double perHour, perWeek, taxes, sum;
		try {
			if(Text1.getText().equals(null) || Text2.getText().equals(null) || Text3.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			perHour = Double.valueOf(Text1.getText());
			perWeek = Double.valueOf(Text2.getText());
			taxes = Double.valueOf(Text3.getText());
			
			
		
			sum = ((perHour * perWeek) * 52) - taxes;
			sum = sum/12;
		
			String result = String.format("%.2f", sum);
			labelTwo.setText(result);
		
			//Save data
		
			FileWriter myWriter = new FileWriter("data/dataIncome.csv");
			myWriter.write("1," + Text1.getText() + "," + Text2.getText() + "," + labelOne.getText() + "," + Text3.getText() + "," + result);
			myWriter.close();
		}
		catch (Exception excpt){
			AlertWindow.display("Error", "Please enter values.");
			System.out.println(excpt.getMessage());
		}
		
		
	}
	
}
