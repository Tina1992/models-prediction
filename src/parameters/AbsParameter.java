package parameters;

import models.AbsModeler;

public abstract class AbsParameter {
	
	protected String name_;

	public String getName(){
		return name_;
	}
	
	public abstract void modifyModel(AbsModeler modeler);
	
};
