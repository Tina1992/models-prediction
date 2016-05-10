package optimization;

import java.util.Vector;

import models.AbsModeler;
import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import weka.core.Instances;

public abstract class AbsWekaSimpleOptimizer extends AbsWekaOptimizer{

	private static final int DEFAULT_CANT = 50;
	protected static final double DEFAULT_DOUBLE_STEP = 0.1;
	protected static final double DEFAULT_INTEGER_STEP = 1;
	
	protected class range{
		public String name;
		public double min;
		public double max;
		
		public range(String name,double min2, double max2) {
			this.name=name;
			min=min2;
			max=max2;
		}
	}
	
	protected Vector<range> ranges=new Vector<range>();
	
	protected void addRange(String name, double min, double max){
		ranges.addElement(new range(name, min, max));
	}
	
	protected range getRange(String name){
		for (range r:ranges){
			if (name.equals(r.name))
				return r;
		}
		return null;
	}

	private void ensureRanges(Vector<AbsParameter> params) {
		// TODO Auto-generated method stub
		for (AbsParameter p:params){
			double value=((Double)((AbsWekaParameter)p).getValue());
			int min=2;
			if (value-(DEFAULT_CANT)/2>0)
				min=(int) (value-(DEFAULT_CANT)/2);
			ranges.add(new range(p.getName(), min, value+(DEFAULT_CANT)/2));
		}
	}

	@Override
	protected void optimiceParams(AbsModeler modeler, Instances isTrainingSet) {
		// TODO Auto-generated method stub
		try {
			Vector<AbsParameter> parameters=modeler.getParameters();
			ensureRanges(parameters);
			optimiceDoubleParams(modeler, isTrainingSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void optimiceDoubleParams(AbsModeler modeler, Instances isTrainingSet)throws Exception ;
}
