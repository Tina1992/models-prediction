package models;

import parameters.WekaSimpleParameter;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.*;
import weka.classifiers.meta.GridSearch;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.filters.AllFilter;

public class SMOregClassifier extends AbsWekaClassifier {
	
	private static final double DEFAULT_C = 1;

	public SMOregClassifier(int index){
		super(new SMOreg(), index);
		addParameter(new WekaSimpleParameter('C', DEFAULT_C, "Complexy"));
	}
	
	public String getName(){
		return "SMOreg";
	}
	
	@Override
	public Classifier[] bestClassifiers(Instances dataset) throws Exception {
		
		GridSearch gs = new GridSearch();
		gs.setClassifier(this.classifier);
	
		//KERNEL:
		NormalizedPolyKernel npk = new NormalizedPolyKernel();		//exponent (-E)
		PolyKernel pk = new PolyKernel();							//exponent (-E)
		Puk p = new Puk();											//omega (-O) - sigma (-S)
		RBFKernel rbfk = new RBFKernel();							//gamma (-G)
	
		Kernel[] kernels = {npk, pk, p, rbfk};
		String[] optionK = {"exponent", "exponent", "omega", "gamma"};

		int numcls = kernels.length * SMOreg.TAGS_FILTER.length;
		Classifier[] cls = new Classifier[numcls];
		
		//set the evaluation to mean absolut error: Gets the criterion used for evaluating the classifier performance.
		gs.setEvaluation(new SelectedTag(GridSearch.EVALUATION_MAE, GridSearch.TAGS_EVALUATION));
		
		//Set the filter to weka.filters.AllFilter since we don't need any special data processing and we don't optimize the filter in this case (data gets always passed through filter!).
		gs.setFilter(new AllFilter());
		
		//X PROPERTY: C of SMO
		gs.setXProperty("classifier.c");
		gs.setXMax(5);
		gs.setXMin(0);
		gs.setXStep(1);
		gs.setXExpression("I");
		
		int index=0;
		for(int j=0;j<SMOreg.TAGS_FILTER.length;j++){
			
			/* filterType -- Determines how/if the data will be transformed. 0=normalize/1=standardize/2=neither */
			((SMOreg) gs.getClassifier()).setFilterType(new SelectedTag(j, SMOreg.TAGS_FILTER));
			for(int i=0; i<kernels.length;i++){
				Kernel kernel = kernels[i];
				((SMOreg) gs.getClassifier()).setKernel(kernel);
					
				gs.setYProperty("classifier.kernel."+optionK[i]);
				gs.setYMax(3);
				gs.setYMin(1);
				gs.setYStep(1);
				gs.setYBase(10);
				gs.setYExpression("pow(BASE,I)");
				
				gs.buildClassifier(dataset);
				cls[index] = gs.getBestClassifier();
				index++;
			}
			
			
		}
		return cls;
	}

	@Override
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
