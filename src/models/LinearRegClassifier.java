package models;

import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;

public class LinearRegClassifier extends AbsWekaClassifier{
	/*
	 * Generates a linear regression function predictor.
	 * Predicted attribute: A
	 * coefficients[att0, att1,...,N] 
	 * A = coefficients[att0] + coefficients[att1] + ... for all i: coefficients[atti] != 0
	 */

	private static final double DEFAULT_RIDGE = 0.5;


	public LinearRegClassifier(int index){
		super(new LinearRegression(), index);
		
		addParameter(new WekaSimpleParameter('R',DEFAULT_RIDGE,"Ridge"));
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LinearRegression";
	}

	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * attributeSelectionMethod 
		 * -- Set the method used to select attributes for use in the linear regression. 
		 * Available methods are: no attribute selection, attribute selection using M5's method (step through the 
		 * attributes removing the one with the smallest standardised coefficient until no improvement is observed 
		 * in the estimate of the error given by the Akaike information criterion), and a greedy selection using the 
		 * Akaike information metric.
		 */
		Tag[] methods = LinearRegression.TAGS_SELECTION;
		Classifier[] cls = new Classifier[methods.length];
		((LinearRegression) this.classifier).setEliminateColinearAttributes(true);
		CVParameterSelection cvps = new CVParameterSelection();
		cvps.setNumFolds(5);
		cvps.addCVParameter("R 1.0E-10 1.0E-6 5");
		Classifier aux = new LinearRegression();
		for(int i=0; i<methods.length;i++){
			((LinearRegression) this.classifier).setAttributeSelectionMethod(new SelectedTag(i, methods));
			cvps.setClassifier(this.classifier);
			cvps.buildClassifier(dataset);
			aux.setOptions(cvps.getBestClassifierOptions());
			cls[i] = aux;
		}
		return cls;
	}

	
	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
