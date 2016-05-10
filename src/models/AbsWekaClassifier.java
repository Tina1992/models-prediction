package models;

import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import parameters.WekaSimpleParameter;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.MultiScheme;
import weka.core.Instances;
import weka.core.OptionHandler;

public abstract class AbsWekaClassifier extends AbsWekaModeler{
	protected Classifier classifier;
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
	
	public AbsWekaClassifier(Classifier clas, int index){
		super((OptionHandler) clas);
		this.index=index;
		this.classifier=clas;
	}
	
	public void setClassifier(Classifier classifier){
		this.classifier=classifier;
	}

	public String toString(){
		return classifier.toString();
	}
	
	public Classifier getClassifier(){
		return classifier;
	}
	
	public Classifier optimizingParams(Instances dataset) throws Exception{
		Classifier[] bestclasifiers = getOptions(dataset);
		int index=0;
		Classifier[] optclassifiers = new Classifier[bestclasifiers.length];
		for(Classifier bc: bestclasifiers){
			optclassifiers[index] = performAttSelect(bc,dataset);
			index++;
		}
		//determinate the best classifier  
		MultiScheme ms = new MultiScheme();
		ms.setClassifiers(optclassifiers);
		ms.buildClassifier(dataset);
			    
		return optclassifiers[ms.getBestClassifierIndex()];
	}

	public AbsModeler getModeler(Instances dataset){
		dataset.setClassIndex(index);
		try {
			classifier=optimizingParams(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public void parseOptions(String[] options){
		int index=0;
		for (index=0;index<options.length;index=index+2){
			try{
			if (!options[index].isEmpty()&&(options[index].length()==2)){
				AbsParameter p=getParameter(options[index].charAt(1));
				((AbsWekaParameter)p).setValue(Double.valueOf(options[index+1]));
			}}
			catch  (Exception e){
				System.err.println("Error de parametro");
			}
		}
		
	}
	
	//--Abstract methods
	public abstract String getName();
	public abstract Classifier[] getOptions(Instances dataset) throws Exception;
}
