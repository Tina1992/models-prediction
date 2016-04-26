package models;

import weka.clusterers.Clusterer;
import weka.core.Instances;
import weka.core.OptionHandler;

public abstract class AbsWekaClusterer extends AbsWekaModeler {
	protected Clusterer clusterer_;
	
	public AbsWekaClusterer(Clusterer clusterer){
		super((OptionHandler) clusterer);
		clusterer_=clusterer;
	}

	@Override
	protected AbsModeler getModeler(Instances instances) {
		// TODO Auto-generated method stub
		getClusterer(instances);
		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return clusterer_.toString();
	}
	
	public Clusterer getClusterer(){
		return clusterer_;
	}

	protected abstract void getClusterer(Instances instances);
	
}
