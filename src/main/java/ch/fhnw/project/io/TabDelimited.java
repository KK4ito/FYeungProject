package ch.fhnw.project.io;

import java.util.*;

import ch.fhnw.project.datenmodell.Variable;

import java.io.*;

public class TabDelimited implements FileParser{

	@Override
	public List<Variable> readData(File file) throws IOException {
		List<Variable> lstData = new ArrayList<>();
		
		
		//try{
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fileReader);
			String line = br.readLine();
			String[] splittedString = line.split("\\t");
				
			for(int i = 0; i < splittedString.length; i++){
				lstData.add(new Variable(splittedString[i]));
			}
			
			while((line = br.readLine()) != null){
				String[] splittedValue = line.split("\\t");
				for(int x = 0; x < splittedString.length; x++){
					
					lstData.get(x).addValue(Double.parseDouble(splittedValue[x]));
				}
				
			}
			br.close();
			fileReader.close();
			
		//}
		//catch(IOException e){
		//	e.printStackTrace();
		//}
		
		return lstData;
		
	}

}
