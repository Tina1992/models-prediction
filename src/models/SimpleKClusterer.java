package models;

import optimization.WekaClustererOptimizer;
import parameters.WekaSimpleParameter;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class SimpleKClusterer extends AbsWekaClusterer {
	
	private static final double DEFAULT_N = 30;
	/**/
	public SimpleKClusterer(){
		super(new SimpleKMeans());
		WekaSimpleParameter n=new WekaSimpleParameter('N', DEFAULT_N, "Clusters Number");
		n.setMinValor(2);
		addParameter(n);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Simple K Clusterer";
	}

	@Override
	protected void getClusterer(Instances dataset) {
		// TODO Auto-generated method stub
		WekaClustererOptimizer wco=new WekaClustererOptimizer();
		wco.optimiceParams(this);
		try {
			clusterer_.buildClusterer(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
