package ch.fhnw.project.io;

import java.util.*;

import ch.fhnw.project.datenmodell.DataModel;

import java.io.*;

public class TabDelimited implements FileParser{

	List<DataModel> lstData;
	File file;	
	
	@Override
	public void readData(File file) {
		this.file = file;
		lstData = new ArrayList<>();
		
		
		try{
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();
			String[] splittedString = line.split("\\t");
				
			for(int i = 0; i < splittedString.length; i++){
				lstData.add(new DataModel(splittedString[i]));
			}
			
			while((line = br.readLine()) != null){
				String[] splittedValue = line.split("\\t");
				for(int x = 0; x < splittedString.length; x++){
					
					lstData.get(x).addValue(Double.parseDouble(splittedValue[x]));
				}
				
			}
			br.close();
			fileReader.close();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public List<DataModel> getList() {
		return this.lstData;
	}

}
