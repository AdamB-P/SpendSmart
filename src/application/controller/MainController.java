package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.Expense;
import application.model.Funds;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * MainController provides functionality to the Main.fxml scene.
 * The user is free to access financial tools by clicking their respective buttons.
 * @author: Austin Thrash - FFK221
 */
public class MainController {

	@FXML private AnchorPane mainPane;
	
	@FXML private Button incomeBtn;
	@FXML private Button expenseBtn;
	@FXML private Button fundsBtn;
	@FXML private Button clearBtn;
	
	@FXML private Label labelOne;
	@FXML private Label labelTwo;
	@FXML private Label labelThree;
	
	@FXML public PieChart pieChart;
	
	/*
	 * Every time we load into the scene we want to update the information based on user input.
	 * We save & load mentioned information to CSV files and use data where needed.
	 * See Main.java for more information
	 */
	@FXML
	public void initialize() throws IOException {
		User userData = new User();
		userData.loadData("data/data.csv");
		ArrayList<String> userDataL = userData.getData();
		
		//Setting Income label upon initialize
		if (userDataL.get(0).equals("0")) {
			labelOne.setText("Awaiting Calculation...");
		} else {
			labelOne.setText(userDataL.get(0));
		}
		
		//Setting Expense label upon initialize
		if (userDataL.get(1).equals("0")) {
			labelTwo.setText("Awaiting Calculation...");
		} else {
			labelTwo.setText(userDataL.get(1));
		}
		//Setting remaining funds upon initialize
		if (userDataL.get(2).equals("0")) {
			labelThree.setText("Awaiting Calculation...");
		} else {
			labelThree.setText(userDataL.get(2));
		}
		
		//If all information has been entered, the pie chart of current expenses will update and become visible to user
		if (!"0".equals(userDataL.get(0)) && !"0".equals(userDataL.get(1)) && !"0".equals(userDataL.get(2))) {
			double month,elect,water,inter,commute,misc,savings, emer;
			
			Expense userInfo = new Expense();
			userInfo.loadData("data/dataExpense.csv");
			ArrayList<String> userInfoL = userInfo.getInfo();
			
			Funds userInfo2 = new Funds();
			userInfo2.loadData("data/dataExpense.csv");
			ArrayList<String> userInfo2L = userInfo2.getInfo();
			
			month = Double.valueOf(userInfoL.get(1));
			elect = Double.valueOf(userInfoL.get(2));
			water = Double.valueOf(userInfoL.get(3));
			inter = Double.valueOf(userInfoL.get(4));
			commute = Double.valueOf(userInfoL.get(5));
			misc = Double.valueOf(userInfoL.get(6));
			
			savings = Double.valueOf(userInfo2L.get(1));
			emer = Double.valueOf(userInfo2L.get(2));
			
			ObservableList<PieChart.Data> pieChartData
			= FXCollections.observableArrayList(
					new PieChart.Data("Rent", month),
					new PieChart.Data("Electricity", elect),
					new PieChart.Data("Water", water),
					new PieChart.Data("Internet", inter),
					new PieChart.Data("Commute", commute),
					new PieChart.Data("Misc", misc),
					new PieChart.Data("Savings", savings),
					new PieChart.Data("emer", emer));
		
			pieChart.setData(pieChartData);
		}
	}
	
	/*
	 * hanleIncome() handles user input by changing the current scene to income tool (PayType.fxml)
	 * @param event: this is passed when the user clicks on incomeBtn
	 */
	@FXML public void handleIncome(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/PayType.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * handleExpense() handles user input by changing the current scene to expense calculator (Expense.fxml)
	 * @param event: this is passed when the user clicks on expenseBtn
	 */
	@FXML public void handleExpense(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/Expense.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * handleFunds() handles user input by changing the current scene to funds calculator (Funds.fxml)
	 * @param event: this is passed when the user clicks on fundsBtn
	 */
	@FXML public void handleFunds(ActionEvent event) throws IOException{
		URL url = new File("src/application/view/Funds.fxml").toURI().toURL();
		mainPane = (AnchorPane)FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * handleClear() clears all data provided by the user; this gives them a fresh start
	 * @param event: this is passed when the user clicks on clearBtn
	 */
	public void handleClear(ActionEvent event) throws IOException{
		FileWriter myWriter = new FileWriter("data/data.csv");
		myWriter.write("0,0,0");
		myWriter.close();
		
		FileWriter myWriter2 = new FileWriter("data/dataExpense.csv");
		myWriter2.write("0,0,0,0,0,0,0");
		myWriter2.close();
		
		FileWriter myWriter3 = new FileWriter("data/dataIncome.csv");
		myWriter3.write("0,0,0,0,0,0");
		myWriter3.close();
		
		FileWriter myWriter4 = new FileWriter("data/dataFunds.csv");
		myWriter4.write("0,0,0");
		myWriter4.close();
		
		labelOne.setText("Awaiting Calculation...");
		labelTwo.setText("Awaiting Calculation...");
		labelThree.setText("Awaiting Calculation...");
		
		pieChart.getData().removeAll();
	}
}
