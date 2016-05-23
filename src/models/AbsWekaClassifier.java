package models;

import optimization.AbsWekaOptimizer;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;

public abstract class AbsWekaClassifier extends AbsWekaModeler{
	protected AbstractClassifier classifier;
	protected AbsWekaOptimizer optimizer;
	private int index;
	
	//--Private methods
	private Classifier performAttSelect(Classifier base, Instances dataset) throws Exception{
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
	    CfsSubsetEval evaluator = new CfsSubsetEval();
	    ExhaustiveSearch search = new ExhaustiveSearch();
	    asc.setClassifier(base);
	    asc.setEvaluator(evaluator);
	    asc.setSearch(search);
	    asc.buildClassifier(dataset);
	    return asc.getClassifier();
	}
	
	//--Public methods
	/**/
	public AbsWekaClassifier(AbstractClassifier clas, AbsWekaOptimizer optimizer, int index){
		super(clas);
		this.index=index;
		classifier=clas;
		this.optimizer=optimizer;
	}

	public void setClassifier(AbstractClassifier classifier){
		this.classifier=classifier;
		parseOptions(classifier.getOptions());
	}

	public String toString(){
		return classifier.toString();
	}
	
	public Classifier getClassifier(){
		return classifier;
	}

	public AbsModeler getModeler(Instances dataset){
		dataset.setClassIndex(index);
		optimizer.optimiceParams(this);
		try {
			classifier.buildClassifier(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	//--Abstract methods
	public abstract String getName();
}
