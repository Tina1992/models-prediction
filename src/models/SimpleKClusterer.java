package models;

import java.util.Random;

import parameters.WekaSimpleParameter;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;

public class SimpleKClusterer extends AbsWekaClusterer {
	
	private static final int DEFAULT_N = 50;

	public SimpleKClusterer(){
		super(new SimpleKMeans());
		addParameter(new WekaSimpleParameter('N', DEFAULT_N, "Clusters Number"));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Simple K Clusterer";
	}

	@Override
	protected void getClusterer(Instances dataset) {
		// TODO Auto-generated method stub
		try {

			WekaSimpleParameter cn=(WekaSimpleParameter) getParameter("Clusters Number");
			((SimpleKMeans) clusterer_).setPreserveInstancesOrder(true);
			int center = (int) Math.sqrt(dataset.numInstances());
			int finalk = 0;

			int seed = 10; // the seed for randomizing the data
			Random rand = new Random(seed); // create seeded number generator
			Instances randData = new Instances(dataset); // create copy of
															// original data
			randData.randomize(rand); // randomize data with number generator

			double maxAv = Double.MAX_VALUE;

			for (int k = center - (center / 2); k < 50; k++) {
				double error = 0;
				double s = 0.0;
				for (int n = 0; n < k; n++) {
					Instances train = randData.trainCV(k, n);
					Instances test = randData.testCV(k, n);
					cn.setValue(k);
					cn.modifyModel(this);
					clusterer_.buildClusterer(train);
					for (int i = 0; i < test.numInstances(); i++) {
						s += s((SimpleKMeans)clusterer_, test.instance(i), train);
					}
					s = s / test.numInstances();
					error = ((SimpleKMeans) clusterer_).getSquaredError() / test.numInstances();
				}
				if (((error - s) > 0) && (error - s) < maxAv) {
					maxAv = Math.abs(s - error);
					finalk = k;
				}
			}
			cn.setValue(finalk);
			cn.modifyModel(this);
			clusterer_.buildClusterer(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private double s(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		double a = a(knn, i, is);
		double b = b(knn, i, is);
		if (a == b)
			return 0.0;
		if (a < b)
			return 1 - (a / b);
		return (b / a) - 1;
	}

	private double b(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		// TODO Auto-generated method stub
		int[] ass = knn.getAssignments();
		int cluster = knn.clusterInstance(i);
		double minDist = Double.MAX_VALUE;
		for (int j = 0; j < ass.length; j++) {
			if (ass[j] != cluster) {
				double dist = knn.getDistanceFunction().distance(i, is.instance(j));
				if (dist < minDist) {
					minDist = dist;
				}
			}

		}
		return minDist;
	}

	private double a(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		// TODO Auto-generated method stub
		int[] ass = knn.getAssignments();
		int cluster = knn.clusterInstance(i);
		double maxDist = 0;
		for (int j = 0; j < ass.length; j++) {
			if (ass[j] == cluster) {
				double dist = knn.getDistanceFunction().distance(i, is.instance(j));
				if (dist > maxDist) {
					maxDist = dist;
				}
			}

		}
		return maxDist;
	}

}
