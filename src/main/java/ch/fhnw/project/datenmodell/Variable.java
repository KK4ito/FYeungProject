package ch.fhnw.project.datenmodell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FelixYeung on 19.05.16.
 */
public class Variable {
	
    private List<Double> lstValue;
    private String name;

    public Variable(String name){
    	this.name = name;
        lstValue = new ArrayList<>();
    }
    
    public void addValue(double d){
    	this.lstValue.add(d);
    }
    
    public String getName(){
    	return this.name;
    }
    
    public List<Double>  getValues(){
    	return this.lstValue;
    }
    
    public Double getValue(int index){
    	return this.lstValue.get(index);
    }

}
