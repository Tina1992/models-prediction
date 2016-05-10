package optimization;

import java.util.Vector;

import models.AbsModeler;
import models.AbsWekaClassifier;
import models.AbsWekaModeler;
import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.OptionHandler;

public class WekaClassifierOptimizer extends AbsWekaSimpleOptimizer {

	private static final int FOLDS = 5;

	@Override
	protected void optimiceDoubleParams(AbsModeler modeler, Instances isTrainingSet) throws Exception {
		// TODO Auto-generated method stub
		
		Vector<AbsParameter> parameters=modeler.getParameters();
		CVParameterSelection cvps = new CVParameterSelection();
		
		for (AbsParameter p: parameters){
			range r=getRange(p.getName());
			Double step=DEFAULT_DOUBLE_STEP;
			if (r.min%1==0){
				step = DEFAULT_INTEGER_STEP;
			}
			String val=((AbsWekaParameter) p).getParameterString()[0].charAt(1)+" "+r.min+" "+r.max+" "+step;
			cvps.addCVParameter(val.toUpperCase());
		}
		cvps.setNumFolds(FOLDS);
		cvps.setClassifier((Classifier) ((AbsWekaModeler)modeler).getOptionHandler());
		cvps.buildClassifier(isTrainingSet);
		Classifier clas=(Classifier) ((AbsWekaModeler)modeler).getOptionHandler();
		((OptionHandler)clas).setOptions(cvps.getBestClassifierOptions());
		((AbsWekaClassifier)modeler).parseOptions(cvps.getBestClassifierOptions());
	}

}
