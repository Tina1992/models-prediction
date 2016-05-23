package parameters;

import models.AbsModeler;
import models.AbsWekaModeler;
import weka.core.OptionHandler;

public class WekaSimpleParameter extends AbsWekaParameter {

	private char charOp_;
	private double value_;
	private float minValue = 0;
	private float maxValue = -1;		//No tiene limite

	public WekaSimpleParameter(char charOp, Object value, String name) {
		super(name);
		charOp_ = charOp;
		value_ = (double) value;
	}

	// getters and setters

	public void setValue(double value) {
		value_ = value;
	}

	public double getValue() {
		return value_;
	}

	public float getMinValor() {
		return minValue;
	}

	public void setMinValor(float min) {
		minValue = min;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	// Public methods
	/**/
	@Override
	public void modifyModel(AbsModeler modeler) {
		try {
			OptionHandler oh = ((AbsWekaModeler) modeler).getOptionHandler();
			modifyObject(oh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**/
	@Override
	public String getParameterString() throws Exception {
		// TODO Auto-generated method stub
		double r=value_%1;
		if (r==0){
			Double val=new Double(value_);
			return "-" + String.valueOf(charOp_).toUpperCase() + " " + String.valueOf(val.intValue());
		}
		return "-" + String.valueOf(charOp_).toUpperCase() + " " + String.valueOf(value_);
	}

}
