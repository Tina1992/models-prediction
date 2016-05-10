package parameters;

import java.util.Vector;

import models.AbsModeler;
import models.SMOregClassifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.Kernel;

public class WekaKernelParameter extends AbsWekaParameter {
	
	protected Kernel kernel_;
	protected Vector<AbsWekaParameter> kernelOptions_;
	
	public WekaKernelParameter(Kernel kernel){
		super('K', kernel,"Kernel Parameter");
		kernel_=kernel;
	}
	
	public void setKernelParams(Vector<AbsWekaParameter> options){
		kernelOptions_=options;
	}
	
	public Vector<AbsWekaParameter> getKernelParams(){
		return kernelOptions_;
	}

	@Override
	public void modifyModel(AbsModeler modeler) {
		// TODO Auto-generated method stub
		for (AbsWekaParameter wkp: kernelOptions_){
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

	public Kernel getKernel() {
		// TODO Auto-generated method stub
		return kernel_;
	}
	
	
	

}
