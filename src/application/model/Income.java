package application.model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Income {

	public ArrayList<String> info;
	
	//Constructor
	public Income() {
		info = new ArrayList<String>();
	}
	
	//Load data to list
	public void loadData(String file) throws IOException{
		setInfo(this.info);
		
		BufferedReader reader;
		String currentRow;
		reader = new BufferedReader(new FileReader(file));
		
		while((currentRow = reader.readLine()) != null) {
			String data[] = currentRow.split(",");
			addInfo(data[0]); //Scene init
			addInfo(data[1]); //Pay per hour
			addInfo(data[2]); //Hours pre week
			addInfo(data[3]); //Yearly Salary
			addInfo(data[4]); //Taxes
			addInfo(data[5]); //Estimated monthly income
		}
		reader.close();
	}
	
	//Setter and getter
	public ArrayList<String> getInfo() {
		return this.info;
	}
	public void setInfo(ArrayList<String> info) {
		this.info = new ArrayList<>();
	}
	public void addInfo(String thing) {
		info.add(thing);
	}
}
