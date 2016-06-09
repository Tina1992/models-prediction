package database;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import individual.Individual;

public abstract class Database implements Cloneable{
	
	protected Vector<Individual> database;
	
	public Database(){
		database=new Vector<Individual>();
	}
	
	public Vector<Individual> getIndividuals(){
		return database;
	}
	
	public Object getDatabaseImplementation(){
		return database; ////Puede ser override si se tiene una representación interna (Por ejemplo weka - Instances)
	}
	
	public void addAttribute(String name){		//Numeric attribute
		for (Individual i:database){
			i.addAttribute(name, null);
		}
	}
	
	public void addData(File file){
		Database second=clone();
		second.parseFile(file);
		for (Individual i:second.getIndividuals()){
			database.add(i);
		}
	}
	
	public Database clone(){
		 Database b=null;
		try {
			b = getClass().getDeclaredConstructor().newInstance();
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}
	
	public abstract void parseFile(File file);	//Parsing del archivo

}
