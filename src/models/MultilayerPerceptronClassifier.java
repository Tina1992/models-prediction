package models;

import optimization.WekaClassifierOptimizer;
import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

public class MultilayerPerceptronClassifier extends AbsWekaClassifier {

	/*
	 * neural network regression
	 */

	private static final double DEFAULT_LR = 0.1;
	private static final double DEFAULT_M = 0.3;
	private static final double DEFAULT_TT = 500;
	private static final double DEFAULT_VS = 0;

	public MultilayerPerceptronClassifier(int index) {
		super(new MultilayerPerceptron(), index);
		addParameter(new WekaSimpleParameter('L', DEFAULT_LR, "L"/*"Learning Rate"*/));
		addParameter(new WekaSimpleParameter('M', DEFAULT_M, "M"/*"Momentum"*/));
		addParameter(new WekaSimpleParameter('N', DEFAULT_TT, "N"/*"Training Time"*/));
		addParameter(new WekaSimpleParameter('V', DEFAULT_VS, "V"/*"Learning Rate"*/));
	}

	@Override
	public String getName() {
		return "MultilayerPerceptron";
	}

	@Override
	public Classifier[] getOptions(Instances dataset) throws Exception {
		WekaClassifierOptimizer wco = new WekaClassifierOptimizer();
		wco.optimiceParams(this);
		return new Classifier[] { classifier };
	}
}
