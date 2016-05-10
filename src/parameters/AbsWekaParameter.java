package parameters;

import models.AbsModeler;
import weka.core.OptionHandler;
import weka.core.Utils;

public abstract class AbsWekaParameter extends AbsParameter{
	
	protected char charOp_;
	protected Object value_;
	
	public AbsWekaParameter(char charOp, Object value,String name){
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
		return Utils.splitOptions("-"+charOp_+" "+value_.toString());
	}
	
	protected void modifyObject(OptionHandler oh) throws Exception{
		oh.setOptions(this.getParameterString());
	}
	
	public void parseOptions(String options){
		charOp_=options.charAt(0);
		value_=options.split(" ")[1];
	}
	
	public abstract void modifyModel(AbsModeler modeler);

}
