package application.model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User {

	public ArrayList<String> data;
	
	//Constructor
	public User() {
		data = new ArrayList<String>();
	}
	
	//Load data to list
	public void loadData(String file) throws IOException{
		setData(this.data);
		//
		BufferedReader reader;
		String currentRow;
		reader = new BufferedReader(new FileReader(file));
		
		while((currentRow = reader.readLine()) != null) {
			String data[] = currentRow.split(",");
			addData(data[0]); //Estimated Income
			addData(data[1]); //Estimated Expenses
			addData(data[2]); //Available funds
		}
		reader.close();
	}
	
	//Setter and getter
	public ArrayList<String> getData() {
		return this.data;
	}
	public void setData(ArrayList<String> data) {
		this.data = new ArrayList<>();
	}
	public void addData(String thing) {
		data.add(thing);
	}
}