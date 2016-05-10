package models;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.converters.CSVLoader;

public abstract class AbsWekaModeler extends AbsModeler {

	protected OptionHandler optionHandler_;
	protected Instances database_;
	
	//Public methods
	
	public AbsWekaModeler(OptionHandler optionHandler){
		optionHandler_=optionHandler;
	}

	public OptionHandler getOptionHandler(){
		return optionHandler_;
	}
	
	public Instances getInstances(){
		return database_;
	}
	
	@Override
	public AbsModeler getModel(File database){
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
	
	//Protected methods

	protected abstract AbsModeler getModeler(Instances isTrainingSet);

}
