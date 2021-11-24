package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Funds {
	public ArrayList<String> info;
	
	//Constructor
	public Funds() {
		info = new ArrayList<String>();
	}
	
	//Loads data to list
	public void loadData(String file) throws IOException{
		setInfo(this.info);
		//
		BufferedReader reader;
		String currentRow;
		reader = new BufferedReader(new FileReader(file));
		
		while((currentRow = reader.readLine()) != null) {
			String data[] = currentRow.split(",");
			addInfo(data[0]); //Scene init
			addInfo(data[1]); //Savings
			addInfo(data[2]); //Emergency Funds
		}
		reader.close();
	}
	
	//Setters and getters
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
