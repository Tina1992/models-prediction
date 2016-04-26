package parameters;

import java.util.Vector;

import models.AbsModeler;
import models.SMOregClassifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.Kernel;

public class WekaKernelParameter extends AbsParameter {
	
	protected Kernel kernel_;
	protected Vector<WekaSimpleParameter> kernelOptions_;
	
	public WekaKernelParameter(Kernel kernel){
		kernel_=kernel;
	}

	@Override
	public void modifyModel(AbsModeler modeler) {
		// TODO Auto-generated method stub
		for (WekaSimpleParameter wkp: kernelOptions_){
			try {
				wkp.modifyObject(kernel_);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SMOreg smoreg=(SMOreg) ((SMOregClassifier)modeler).getClassifier();
		smoreg.setKernel(kernel_);
	}
	
	
	

}
