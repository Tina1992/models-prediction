package parameters;

import models.AbsModeler;

public abstract class AbsParameter {
	
	protected String name_;
	
	public abstract void modifyModel(AbsModeler modeler);

	public String getName(){
		return name_;
	}
	
};
