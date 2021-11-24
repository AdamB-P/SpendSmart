package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.AlertWindow;
import application.model.Expense;
import application.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExpenseController {

	@FXML
	private AnchorPane mainPane;
	
	//All TextFields
	@FXML
	private TextField monthText;
	@FXML
	private TextField electText;
	@FXML
	private TextField waterText;
	@FXML
	private TextField interText;
	@FXML
	private TextField commuteText;
	@FXML
	private TextField miscText;
	
	//All Buttons
	@FXML
	private Button calculateBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private Button returnBtn;
	
	//Label
	@FXML
	private Label labelOne;
	
	//PieChart
	@FXML
	public PieChart pieChart;
	
	public void initialize() throws IOException {
		//Load in userData
		Expense userInfo = new Expense();
		userInfo.loadData("data/dataExpense.csv");
		ArrayList<String> userInfoL = userInfo.getInfo();
		
		if( userInfoL.get(0).equals("0") && userInfoL.get(1).equals("0")) {
			//Do nothing if user hasn't entered any information on the
			//expense scene.
			
		} else {
			//Sets text to user data
			monthText.setText(userInfoL.get(1));
			electText.setText(userInfoL.get(2));
			waterText.setText(userInfoL.get(3));
			interText.setText(userInfoL.get(4));
			commuteText.setText(userInfoL.get(5));
			miscText.setText(userInfoL.get(6));
			
			//Creating double variables for pie chart
			double month,elect,water,inter,commute,misc;
			
			month = Double.valueOf(userInfoL.get(1));
			elect = Double.valueOf(userInfoL.get(2));
			water = Double.valueOf(userInfoL.get(3));
			inter = Double.valueOf(userInfoL.get(4));
			commute = Double.valueOf(userInfoL.get(5));
			misc = Double.valueOf(userInfoL.get(6));
			
			ObservableList<PieChart.Data> pieChartData
			= FXCollections.observableArrayList(
					new PieChart.Data("Rent", month),
					new PieChart.Data("Electricity", elect),
					new PieChart.Data("Water", water),
					new PieChart.Data("Internet", inter),
					new PieChart.Data("Commute", commute),
					new PieChart.Data("Misc", misc));
		
			pieChart.setData(pieChartData);
		}
		
		
		
	}
	//Returns user to main scene
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
	
	//Calculates the sum of all text fields, displays it on a pie chart, and then saves the data so it
	//can be stored if user leaves and returns to expense scene.
	public void handleCalculate(ActionEvent event) throws IOException{
		double month, elect, water, inter, commute, misc, sum;
		//Using exceptions to catch user errors
		//TODO add visual display of error for user to see
		try {
			//If any field is empty, throws exception
			if(monthText.getText().equals(null) || electText.getText().equals(null) || waterText.getText().equals(null) || interText.getText().equals(null) || commuteText.getText().equals(null) || miscText.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			month = Double.valueOf(monthText.getText());
			elect = Double.valueOf(electText.getText());
			water = Double.valueOf(waterText.getText());
			inter = Double.valueOf(interText.getText());
			commute = Double.valueOf(commuteText.getText());
			misc = Double.valueOf(miscText.getText());
		
			sum = month + elect + water + inter + commute + misc;
		
			String result = String.format("%.2f", sum);
			labelOne.setText(result);
			//Saving data to file for later use
			FileWriter myWriter = new FileWriter("data/dataExpense.csv");
			myWriter.write("1," + monthText.getText() + "," + electText.getText() + "," +
					waterText.getText() + "," + interText.getText() + "," + commuteText.getText() + "," +
					miscText.getText());
			myWriter.close();
			
			//load user data
			User userInfo = new User();
			userInfo.loadData("data/data.csv");
			ArrayList<String> userInfoList = userInfo.getData();
			
			//update user data
			FileWriter myWriter2 = new FileWriter("data/data.csv");
			myWriter2.write(userInfoList.get(0) + "," + result + "," + userInfoList.get(2));
			myWriter2.close();
			
			//update pie chart
			ObservableList<PieChart.Data> pieChartData
			= FXCollections.observableArrayList(
					new PieChart.Data("Rent", month),
					new PieChart.Data("Electricity", elect),
					new PieChart.Data("Water", water),
					new PieChart.Data("Internet", inter),
					new PieChart.Data("Commute", commute),
					new PieChart.Data("Misc", misc));
		
			pieChart.setData(pieChartData);
		}
		catch (Exception excpt){
			System.out.println(excpt.getMessage());
			AlertWindow.display("Error", "Please enter expenses.");
		}
		
	}
	//Clears data on file and resets text fields
	@FXML
	public void handleClear(ActionEvent event) throws IOException{
		FileWriter myWriter = new FileWriter("data/dataExpense.csv");
		myWriter.write("0,0,0,0,0,0,0");
		myWriter.close();
		
		monthText.setText(null);
		electText.setText(null);
		waterText.setText(null);
		interText.setText(null);
		commuteText.setText(null);
		miscText.setText(null);
		
		labelOne.setText("Awaiting Calculation...");
		
	}
}
