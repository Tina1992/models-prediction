package parameters;

import models.AbsModeler;

public abstract class AbsParameter {
	
	protected String name_;
	
	public AbsParameter(String name){
		name_=name;
	}

	public String getName(){
		return name_;
	}
	
	public abstract void modifyModel(AbsModeler modeler);
	
};
