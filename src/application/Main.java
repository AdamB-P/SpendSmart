/**
 * Main is the class responsible for loading the scene and fxml file of the main scene.
 * 
 * CSV files are used to save and load user information. Organized as such:
 * 
 * "data.csv" - 0, 0, 0; 
 *              Calculated Income, Calculated Expenses, Calculated Funds
 *              
 * *Note* - the first 0 on all extended data files i.e. dataIncome,dataExpense,dataFunds
 * 		   represents if the user has entered information on that scene. 0 if the scene
 * 		   hasn't been used or 1 if it has. Labeled as Scene Init.
 * 
 * "dataIncome.csv" - 0, 0, 0, 0, 0, 0;
 * 					  Scene Init, Hourly pay, Hours per week, Yearly Salary, Taxes, Monthly income
 * 
 * "dataExpense.csv" - 0, 0, 0, 0, 0, 0, 0;
 * 					   Scene Init, Rent, Electric, Water, Internet, Commute, Misc
 * 
 * "dataFunds.csv" - 0, 0, 0;
 * 					 Scene Init, Savings, Emergency fund
 * 
 * When a needs updating(user calculated or saved), it is loaded in using an 
 * object of class User, Income, Expenses, or Funds. The data is stored in an ArrayList
 * and the necessary data index is updated and the rest are written back onto the file with
 * commas separating data fields.(ie .write("userData.get(0) + "," + updatedInfo));
 * 
 * If this process doesn't make sense, look at the csv files before entering data and observe 
 * how they change as you progress through the program. Or ask me and I can try to explain it better.
 * 
 * !!!!NOTICE!!!!: I had to add to my VM run arguements to get JavaFX to work. I'm not sure why.
 * Told me JavaFX runtime components are missing. But I followed all the set up instructions.
 * So if you have any trouble running the program enter this into the VM arguments
 * 
 * --module-path "C:\Program Files\Java\javafx-sdk-17.0.0.1\lib" --add-modules=javafx.controls,javafx.fxml
 * 
 * Just change the path to match your Javafx SDK directory. I don't know why this issue exists.
 * 
 * @Outline_By Austin Thrash - FFK221
 * UTSA CS 3443 - Group Project
 * Fall 2021
 */

package application;
	
import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = new File("src/application/view/Main.fxml").toURI().toURL();
			AnchorPane root = (AnchorPane)FXMLLoader.load(url);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("$mart$pend");
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
