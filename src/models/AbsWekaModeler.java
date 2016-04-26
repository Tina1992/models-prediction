package models;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.converters.CSVLoader;

public abstract class AbsWekaModeler extends AbsModeler {

	protected OptionHandler optionHandler_;
	
	public AbsWekaModeler(OptionHandler optionHandler){
		optionHandler_=optionHandler;
	}

	public OptionHandler getOptionHandler(){
		return optionHandler_;
	}
	
	@Override
	public AbsModeler getModel(File database){
		try {
			CSVLoader cvsloader = new CSVLoader();
			cvsloader.setSource(database);
			Instances isTrainingSet = cvsloader.getDataSet();
			return getModeler(isTrainingSet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected abstract AbsModeler getModeler(Instances isTrainingSet);

}
