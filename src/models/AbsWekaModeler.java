package models;

import java.io.File;
import java.io.IOException;

import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import parameters.WekaSimpleParameter;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.converters.CSVLoader;

public abstract class AbsWekaModeler extends AbsModeler {

	protected OptionHandler optionHandler_;
	protected Instances database_;

	/**/
	public void addParameter(AbsParameter modelParameter) {
		super.addParameter(modelParameter);
		modelParameter.modifyModel(this);
		;
	}

	// Public methods
	/**/
	public AbsWekaModeler(OptionHandler optionHandler) {
		optionHandler_ = optionHandler;
	}

	/**/
	public OptionHandler getOptionHandler() {
		return optionHandler_;
	}

	public Instances getInstances() {
		return database_;
	}

	@Override
	public AbsModeler getModel(File database) {
		try {
			CSVLoader cvsloader = new CSVLoader();
			cvsloader.setSource(database);
			database_ = cvsloader.getDataSet();
			return getModeler(database_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**/
	public void parseOptions(String[] options) {
		int index = 0;
		for (index = 0; index < options.length; index = index + 2) {
			try {
				if (!options[index].isEmpty() && (options[index].length() == 2)) {
					AbsParameter p = getParameter(options[index].charAt(1));
					((WekaSimpleParameter) p).setValue(Double.valueOf(options[index + 1]));
				}
			} catch (Exception e) {
				System.out.println("Parametro no parseado");
			}
		}
		for (AbsParameter p : modelParameters_) {
			p.modifyModel(this);
		}
	}

	// Protected methods

	protected abstract AbsModeler getModeler(Instances isTrainingSet);

	public int getSimpleParamsCount(){
		int i=0;
		for (AbsParameter p: modelParameters_){
			i+=((AbsWekaParameter)p).getSimpleParametersCount();
		}
		return i;
	}
	
}
