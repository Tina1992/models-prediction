package models;

import java.io.File;
import java.util.Vector;

import parameters.AbsParameter;

public abstract class AbsModeler {
	
	protected Vector<AbsParameter> modelParameters_=new Vector<AbsParameter>();
	
	//--Public methods
	/**/
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
	
	public AbsParameter getParameter(char c){
		for (AbsParameter ap:modelParameters_){
			if (ap.getName().equals(String.valueOf(c))){
				return ap;
			}
		}
		return null;
	}

	public Vector<AbsParameter> getParameters() {
		// TODO Auto-generated method stub
		return modelParameters_;
	}
	
	//--Abstract methods
	
	public abstract AbsModeler getModel(File database);
	public abstract String getName();
	public abstract String toString();

}
