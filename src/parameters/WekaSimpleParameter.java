package parameters;

import models.AbsModeler;
import models.AbsWekaModeler;
import weka.core.OptionHandler;
import weka.core.Utils;

public class WekaSimpleParameter extends AbsParameter{
	
	protected char charOp_;
	protected Object value_;
	
	public WekaSimpleParameter(char charOp, Object value,String name){
		charOp_=charOp;
		value_=value;
		name_=name;
	}
	
	public char getCharOp(){
		return charOp_;
	}
	
	public Object getValue(){
		return value_;
	}
	
	public void setValue(Object value){
		value_=value;
	}
	
	public String[] getParameterString() throws Exception{
		return Utils.splitOptions("- "+charOp_+" "+value_.toString());
	}
	
	public void modifyObject(OptionHandler oh) throws Exception{
		oh.setOptions(this.getParameterString());
	}
	
	public void modifyModel(AbsModeler modeler){
		try {
			OptionHandler oh=((AbsWekaModeler)modeler).getOptionHandler();
			modifyObject(oh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
