package main;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import models.AbsModeler;
import models.LinearRegClassifier;
import models.MultilayerPerceptronClassifier;
import models.SMOregClassifier;
import models.SimpleKClusterer;
import models.SimpleLinearRegClassifier;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Main {

	private static double s(SimpleKMeans knn, Instance i, Instances is) throws Exception {
		double a = a(knn, i, is);
		double b = b(knn, i, is);
		if (a == b)
			return 0.0;
		if (a < b)
			return 1 - (a / b);
		return (b / a) - 1;
	}

	private static double b(SimpleKMeans knn, Instance i, Instances is) throws Exception {
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

	private static double a(SimpleKMeans knn, Instance i, Instances is) throws Exception {
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

	public void main() throws Exception {
		// TODO Auto-generated method stub
		CSVLoader cvsloader = new CSVLoader();
		cvsloader.setSource(new File("C:\\Users\\Tina\\Desktop\\test.csv"));
		Instances isTrainingSet = cvsloader.getDataSet();
		SimpleKMeans knn = new SimpleKMeans();
		knn.setPreserveInstancesOrder(true);
		int center = (int) Math.sqrt(isTrainingSet.numInstances());
		double minError = Double.MAX_VALUE;
		int finalk = 0;

		int seed = 10; // the seed for randomizing the data
		Random rand = new Random(seed); // create seeded number generator
		Instances randData = new Instances(isTrainingSet); // create copy of
															// original data
		randData.randomize(rand); // randomize data with number generator

		double maxAv = Double.MAX_VALUE;

		for (int k = center - (center / 2); k < 50; k++) {
			System.out.println(k);
			double error = 0;
			double s = 0.0;
			for (int n = 0; n < k; n++) {
				Instances train = randData.trainCV(k, n);
				Instances test = randData.testCV(k, n);
				knn.setNumClusters(k);
				knn.buildClusterer(train);
				for (int i = 0; i < test.numInstances(); i++) {
					s += s(knn, test.instance(i), train);
				}
				s = s / test.numInstances();
				error = knn.getSquaredError() / test.numInstances();
			}
			if (((error - s) > 0) && (error - s) < maxAv) {
				maxAv = Math.abs(s - error);
				finalk = k;
			}
		}
		
		knn.setNumClusters(finalk);
		knn.buildClusterer(isTrainingSet);
		System.out.println(knn);

	}
	
	public static void main(String[] args) throws IOException{
		Vector<AbsModeler> models=new Vector<AbsModeler>();
		
		File database=new File("C:\\Users\\Tina\\Desktop\\test.csv");
		
		int index=13;
		
		LinearRegClassifier lrc=new LinearRegClassifier(index);
		MultilayerPerceptronClassifier mpc=new MultilayerPerceptronClassifier(index);	//Neural
		SimpleKClusterer skc=new SimpleKClusterer();
		SimpleLinearRegClassifier slrgc=new SimpleLinearRegClassifier(index);
		SMOregClassifier smoreg=new SMOregClassifier(index);								//Vector
		

		//models.add(skc);
		//models.add(lrc);
		//models.add(mpc);
		//models.add(slrgc);
		models.add(smoreg);
		
		for (AbsModeler am: models){
			AbsModeler result=am.getModel(database);
			System.out.println(result.toString());
		}
		
		
	}

}
