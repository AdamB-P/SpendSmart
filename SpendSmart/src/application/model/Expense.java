package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Expense {
	public ArrayList<String> info;
	
	//Constructor
	public Expense() {
		info = new ArrayList<String>();
	}
	
	public void loadData(String file) throws IOException{
		setInfo(this.info);
		
		BufferedReader reader;
		String currentRow;
		reader = new BufferedReader(new FileReader(file));
		
		while((currentRow = reader.readLine()) != null) {
			String data[] = currentRow.split(",");
			addInfo(data[0]); //Scene Init
			addInfo(data[1]); //Rent
			addInfo(data[2]); //Electric
			addInfo(data[3]); //Water
			addInfo(data[4]); //Internet
			addInfo(data[5]); //Commute
			addInfo(data[6]); //Misc
		}
		reader.close();
	}
	
	//Setter and getters
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
