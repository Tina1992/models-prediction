package models;

import optimization.WekaClassifierOptimizer;
import weka.classifiers.functions.SimpleLinearRegression;

public class SimpleLinearRegClassifier extends AbsWekaClassifier {
	/*
	 * Learns a simple linear regression model. Picks the attribute that results in the lowest squared error. 
	 * Can only deal with numeric attributes.
	 * 
	 * Predicted attribute: A
	 * Function on B (attributeIndex): A = Slope * B + Intercept 	A y B high correlation 
	 */
	/**/
	public SimpleLinearRegClassifier(int index){
		super(new SimpleLinearRegression(), new WekaClassifierOptimizer(0,5), index);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SimpleLinearRegression";
	}

}
