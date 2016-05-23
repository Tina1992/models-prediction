package models;

import optimization.WekaClassifierOptimizer;
import parameters.WekaSimpleParameter;
import weka.classifiers.functions.LinearRegression;

public class LinearRegClassifier extends AbsWekaClassifier {
	/*
	 * Generates a linear regression function predictor. Predicted attribute: A
	 * coefficients[att0, att1,...,N] A = coefficients[att0] +
	 * coefficients[att1] + ... for all i: coefficients[atti] != 0
	 */

	private static final double DEFAULT_RIDGE = 0.5;
	/**/
	public LinearRegClassifier(int index) {
		super(new LinearRegression(), new WekaClassifierOptimizer(), index);
		addParameter(new WekaSimpleParameter('R', DEFAULT_RIDGE, "R"));	/*No tiene limites - Double*/
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LinearRegression";
	}
}
