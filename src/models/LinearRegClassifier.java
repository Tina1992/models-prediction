package models;

import optimization.WekaClassifierOptimizer;
import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;

public class LinearRegClassifier extends AbsWekaClassifier {
	/*
	 * Generates a linear regression function predictor. Predicted attribute: A
	 * coefficients[att0, att1,...,N] A = coefficients[att0] +
	 * coefficients[att1] + ... for all i: coefficients[atti] != 0
	 */

	private static final double DEFAULT_RIDGE = 0.5;

	public LinearRegClassifier(int index) {
		super(new LinearRegression(), index);
		addParameter(new WekaSimpleParameter('R', DEFAULT_RIDGE, "R"));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LinearRegression";
	}

	@Override
	public Classifier[] getOptions(Instances dataset) throws Exception {
		WekaClassifierOptimizer wco = new WekaClassifierOptimizer();
		Tag[] methods = LinearRegression.TAGS_SELECTION;
		Classifier[] cls = new Classifier[methods.length];
		((LinearRegression) this.classifier).setEliminateColinearAttributes(true);
		for (int i = 0; i < methods.length; i++) {
			((LinearRegression) this.classifier).setAttributeSelectionMethod(new SelectedTag(i, methods));
			wco.optimiceParams(this);
			cls[i] = this.classifier;
		}
		return cls;
	}
}
