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
import optimization.WekaClustererOptimizer;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Main {
	
	public static void main(String[] args) throws IOException{
		Vector<AbsModeler> models=new Vector<AbsModeler>();
		
		File database=new File("C:\\Users\\Tina\\Desktop\\test.csv");
		

		SimpleKClusterer skc=new SimpleKClusterer();
		//clustereropt.optimiceParams(skc, database);
		
		int index=10;
		
		LinearRegClassifier lrc=new LinearRegClassifier(index);
		MultilayerPerceptronClassifier mpc=new MultilayerPerceptronClassifier(index);	//Neural
		SimpleLinearRegClassifier slrgc=new SimpleLinearRegClassifier(index);
		SMOregClassifier smoreg=new SMOregClassifier(index);								//Vector
		

		//models.add(skc);
		models.add(lrc);
		models.add(mpc);
		models.add(slrgc);
		models.add(smoreg);
		
		for (AbsModeler am: models){
			AbsModeler result=am.getModel(database);
			System.out.println(result.toString());
		}
		
		
	}

}
