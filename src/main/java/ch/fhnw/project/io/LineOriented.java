package ch.fhnw.project.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import ch.fhnw.project.App;
import ch.fhnw.project.datenmodell.Variable;
import javafx.concurrent.Service;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class LineOriented implements FileParser{

	/*private List<Variable> lstData;*/
	/*private File file;*/
	private int countVar;
	private char delimiter;


	@Override
	public List<Variable> readData(File file) throws IOException {
		Group root = new Group();
		Stage stage = new Stage();
		ProgressBar pb = new ProgressBar();


		stage.setTitle("Loading data");
		root.getChildren().addAll(pb);

		stage.setScene(new Scene(root, 300, 150));
		stage.show();



		/*this.file = file;*/
		List<Variable> lstData = new ArrayList<>();
		String line;
	//	try{
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			//Count Variable
			countVar = Integer.parseInt(br.readLine());
			
			for(int i = 0; i < countVar; i++){

				lstData.add(new Variable(br.readLine()));
			}
			
			delimiter = br.readLine().charAt(0);

		double steps = 100/countVar;

		for(int i = 0; i < countVar; i++) {
			line = br.readLine();
			String[] splitted = line.split(delimiter + "");
			for (String s : splitted) {
				lstData.get(i).addValue(Double.parseDouble(s));
			}
			steps+=steps;
			pb.setProgress(steps);
		}
	
		//} catch(Exception e){
		//	e.printStackTrace();
	//	}
		return lstData;
	}

	/*@Override
	public List<Variable> getList() {
		return lstData;
	}
*/
}
