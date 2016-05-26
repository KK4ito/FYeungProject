package ch.fhnw.project.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import ch.fhnw.project.datenmodell.Variable;

public class LineOriented implements FileParser{

	/*private List<Variable> lstData;*/
	/*private File file;*/
	private int countVar;
	private char delimiter;
	
	@Override
	public List<Variable> readData(File file) {

		/*this.file = file;*/
		List<Variable> lstData = new ArrayList<>();
		String line;
		try{
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			//Count Variable
			countVar = Integer.parseInt(br.readLine());
			
			for(int i = 0; i < countVar; i++){
				lstData.add(new Variable(br.readLine()));
			}
			
			delimiter = br.readLine().charAt(0);
			
			for(int i = 0; i < countVar; i++){
				line = br.readLine();
				String[] splitted = line.split(delimiter +"");
				for(String s : splitted){
					lstData.get(i).addValue(Double.parseDouble(s));
				}
				
			}
	
		} catch(Exception e){
			e.printStackTrace();
		}
		return lstData;
	}

	/*@Override
	public List<Variable> getList() {
		return lstData;
	}
*/
}
