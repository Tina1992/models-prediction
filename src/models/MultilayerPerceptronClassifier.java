package models;

import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;

public class MultilayerPerceptronClassifier extends AbsWekaClassifier {

	/*
	 * neural network regression
	 */
	
	private static final Object DEFAULT_LR = 0.1;
	private static final Object DEFAULT_M = 0.3;
	private static final Object DEFAULT_TT = 500;
	private static final Object DEFAULT_VS = 0;

	public MultilayerPerceptronClassifier(int index){
		super(new MultilayerPerceptron(), index);
		addParameter(new WekaSimpleParameter('L',DEFAULT_LR,"Learning Rate"));
		addParameter(new WekaSimpleParameter('M',DEFAULT_M,"Momentum"));
		addParameter(new WekaSimpleParameter('N',DEFAULT_TT,"Training Time"));
		addParameter(new WekaSimpleParameter('V',DEFAULT_VS,"Learning Rate"));
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MultilayerPerceptron";
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		// TODO Auto-generated method stub
		CVParameterSelection cvps = new CVParameterSelection();
		cvps.setNumFolds(5);
		//Learning rate: -L 0.3
		cvps.addCVParameter("L 0.1 0.5 5");
		//momentum: -M 0.2
		cvps.addCVParameter("M 0.1 0.5 5");
		//trainingTime: -N 500
		cvps.addCVParameter("N 500 1000 6");
		//validationsetSize: -V 0
		cvps.addCVParameter("V 0 100 5");
		cvps.setClassifier(this.classifier);
		cvps.buildClassifier(dataset);
		classifier.setOptions(cvps.getBestClassifierOptions());
		return new Classifier[]{classifier};
	}

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
