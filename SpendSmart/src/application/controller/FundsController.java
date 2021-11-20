package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import application.model.Funds;
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
 * FundsController provides functionality to the Funds.fxml scene. 
 * The user can allocate remaining funds to their savings and/or emergency fund.
 * @author: Austin Thrash - FFK221
 */
public class FundsController {

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Label labelOne;
	
	@FXML
	private TextField savingText;
	@FXML
	private TextField emerText;
	
	@FXML
	private Button calcBtn;
	@FXML
	private Button saveBtn;
	@FXML
	private Button returnBtn;
	
	//Loads user data if available. 
	public void initialize() throws IOException {
		double income,expense, sub;
		Funds userInfo = new Funds();
		userInfo.loadData("data/dataFunds.csv");
		ArrayList<String> userInfoL = userInfo.getInfo();
		
		User userData = new User();
		userData.loadData("data/data.csv");
		ArrayList<String> userDataL = userData.getData();
		
		income = Double.valueOf(userDataL.get(0));
		expense = Double.valueOf(userDataL.get(1));
		
		sub = income - expense;
		
		String result = String.format("%.2f", sub);
		
		if( userInfoL.get(0).equals("0") && userInfoL.get(1).equals("0")) {
			labelOne.setText(result);
		} else {
			labelOne.setText(userDataL.get(2));
			savingText.setText(userInfoL.get(1));
			emerText.setText(userInfoL.get(2));
		}
		
	}
	
	/*
	 * handleReturn() handles user input by returning the user to the home screen (Main.fxml)
	 * @param event: this is passed when the user clicks on returnBtn
	 */
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
	
	/*
	 * handleCalculate() subtracts savings & emergency fund from remaining funds.
	 * @param event: this is passed when the user clicks on calcBtn
	 */
	@FXML
	public void handleCalculate(ActionEvent event) throws IOException{
		double saving, emer, income, expense, funds;
		try {
			if(savingText.getText().equals(null) || emerText.getText().equals(null)) {
				throw new Exception("Null Text Fields");
			}
			saving = Double.valueOf(savingText.getText());
			emer = Double.valueOf(emerText.getText());
			
			User userData = new User();
			userData.loadData("data/data.csv");
			ArrayList<String> userDataL = userData.getData();
			
			income = Double.valueOf(userDataL.get(0));
			expense = Double.valueOf(userDataL.get(1));
			
			if(userDataL.get(0).equals("0") || userDataL.get(1).equals("0")) {
				throw new Exception("User Missing Info");
			}
			
			funds = ((income - expense) - (saving + emer));
			
			String result = String.format("%.2f", funds);
			labelOne.setText(result);
			
			FileWriter myWriter = new FileWriter("data/data.csv");
			myWriter.write(userDataL.get(0) + "," + userDataL.get(1) + "," + result);
			myWriter.close();
			
			FileWriter myWriter2 = new FileWriter("data/dataFunds.csv");
			myWriter2.write("1," + savingText.getText() + "," + emerText.getText());
			myWriter2.close();
			
		
		} catch(Exception excpt) {
			System.out.println(excpt.getMessage());
		}
	}

	/*
	 * handleSave() saves input to CSV file; this is also performed by handleCalculate()
	 * @param event: this is passed when the user clicks on calcBtn
	 */
	@FXML
	public void handleSave(ActionEvent event) throws IOException{
		double saving, emer, income, expense, funds;
		
		saving = Double.valueOf(savingText.getText());
		emer = Double.valueOf(emerText.getText());
		
		User userInfo = new User();
		userInfo.loadData("data/data.csv");
		ArrayList<String> userInfoL = userInfo.getData();
		
		income = Double.valueOf(userInfoL.get(0));
		expense = Double.valueOf(userInfoL.get(1));
		try {
			if(userInfoL.get(0).equals("0") || userInfoL.get(1).equals("0")) {
				throw new Exception("User Missing Info");
			}
		
			funds = ((income - expense) - (saving + emer));
		
			String result = String.format("%.2f", funds);
		
		
			FileWriter myWriter = new FileWriter("data/data.csv");
			myWriter.write(userInfoL.get(0) + "," + userInfoL.get(1) + "," + result);
			myWriter.close();
		}catch (Exception excpt) {
			System.out.println(excpt.getMessage());
		}
	}
	
}
