package models;

import java.util.Vector;

import optimization.WekaKernelOptimizer;
import parameters.WekaKernelParameter;
import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.*;
import weka.core.Instances;

public class SMOregClassifier extends AbsWekaClassifier {
	
	private static final double DEFAULT_C = 1;
	private static final double DEFAULT_E = 1;
	private static final Kernel DEFAULT_KERNEL=new NormalizedPolyKernel();

	public SMOregClassifier(int index){
		super(new SMOreg(), index);
		addParameter(new WekaSimpleParameter('C', DEFAULT_C, "C"/*"Complexy"*/));
		WekaSimpleParameter exponent=new WekaSimpleParameter('E',DEFAULT_E,"E"/*"exponent"*/);
		Vector<WekaSimpleParameter> vec=new Vector<>();
		vec.add(exponent);
		WekaKernelParameter kernelParam = new WekaKernelParameter(DEFAULT_KERNEL);
		addParameter(kernelParam);
	}
	
	public String getName(){
		return "SMOreg";
	}
	
	@Override
	public Classifier[] getOptions(Instances dataset) throws Exception {
		WekaKernelOptimizer wko=new WekaKernelOptimizer();
		Classifier[] cls = new Classifier[1];
		wko.optimiceParams(this);
		cls[0]=this.classifier;
		return cls;
	}
	
}
