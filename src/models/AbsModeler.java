package models;

import java.io.File;
import java.util.Vector;

import parameters.AbsParameter;

public abstract class AbsModeler {
	
	protected Vector<AbsParameter> modelParameters_=new Vector<AbsParameter>();
	
	public void addParameter(AbsParameter modelParameter){
		for (AbsParameter ap:modelParameters_){
			if (ap.getName().equals(modelParameter.getName())){
				modelParameters_.remove(ap);
				modelParameters_.add(modelParameter);
				return;
			}
		}
		modelParameters_.add(modelParameter);
	}
	
	public AbsParameter getParameter(String name){
		for (AbsParameter ap:modelParameters_){
			if (ap.getName().equals(name)){
				return ap;
			}
		}
		return null;
	}
	
	public abstract AbsModeler getModel(File database);
	
	public abstract String getName();
	public abstract String toString();

}
