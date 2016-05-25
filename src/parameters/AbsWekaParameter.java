package parameters;

import java.util.Vector;

import models.AbsModeler;
import weka.core.OptionHandler;
import weka.core.Utils;

public abstract class AbsWekaParameter extends AbsParameter {

	public AbsWekaParameter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**/
	protected void modifyObject(OptionHandler oh) throws Exception {
		String[] currOptions = oh.getOptions();
		String[] newOptions = Utils.splitOptions(this.getParameterString());
		for (int i=0;i<currOptions.length;i++){
			if (newOptions[0].equals(currOptions[i])){
				currOptions[i+1]=newOptions[1];
			}
		}
		oh.setOptions(currOptions);
	}

	public abstract void modifyModel(AbsModeler modeler);

	public abstract String getParameterString() throws Exception;
	
	public abstract Vector<String> getPropertyString(double min, double max);
	
	public abstract int getSimpleParametersCount();
	
	public abstract double getFirstValue(double min);
	
	public abstract double getLastValue(double max);

}
