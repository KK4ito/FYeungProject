package ch.fhnw.project;

import org.junit.Test;

import ch.fhnw.project.datenmodell.DataModel;
import ch.fhnw.project.io.FileParser;
import ch.fhnw.project.io.LineOriented;
import ch.fhnw.project.io.TabDelimited;

import static org.junit.Assert.assertEquals;

import java.io.File;

public class ExampleTest {

    @Test
    public void testTabDelimited() {

    	System.out.println("TEST TAB");
    	File file = new File("src/main/resources/reference-data.txt");
        
        FileParser fp = new TabDelimited();
        
        fp.readData(file);
        for(DataModel m : fp.getList()){
        	System.out.print(m.getName() + " Values: ");
        	
        	for(double d : m.getValues()){
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
    	for(DataModel m : fp.getList()){
        	System.out.print(m.getName() + " Values: ");
        	
        	for(double d : m.getValues()){
        		System.out.print(d + "/" );
        	}
        	System.out.println();
        }
    	System.out.println("______________________________");
    }
    
}