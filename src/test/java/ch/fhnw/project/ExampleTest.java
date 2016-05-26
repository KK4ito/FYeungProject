package ch.fhnw.project;

import org.junit.Test;

import ch.fhnw.project.datenmodell.Variable;
import ch.fhnw.project.io.FileParser;
import ch.fhnw.project.io.LineOriented;
import ch.fhnw.project.io.TabDelimited;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

public class ExampleTest {

    @Test
    public void testTabDelimited() {

    	System.out.println("TEST TAB");
    	File file = new File("src/main/resources/helvetia.txt");
        
        FileParser fp = new TabDelimited();

		List<Variable> variables = fp.readData(file);
		for(Variable v : variables){
        	System.out.print(v.getName() + " Values: ");
        	
        	for(double d : v.getValues()){
        		System.out.print(d + "/" );
        	}
        	System.out.println();
        }
        System.out.println("______________________________");
        
    }
    
    @Test
    public void testLineOriented(){
    	
    	System.out.println("TEST LINE");
    	
    	File file = new File("src/main/resources/reference-data.lin");
    	FileParser fp = new LineOriented();
    	
    	fp.readData(file);
    	for(Variable m : fp.readData(file)){
        	System.out.print(m.getName() + " Values: ");
        	
        	for(double d : m.getValues()){
        		System.out.print(d + "/" );
        	}
        	System.out.println();
        }
    	System.out.println("______________________________");
    }

	@Test
	public  void testName(){
		System.out.println("Test name");

		File file = new File("helvetia.txt");
		FileParser fp = new TabDelimited();
		fp.readData(file);

		for (Variable variable : fp.readData(file)) {
			System.out.println(variable.getName());

		}
	}
    
}