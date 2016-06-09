package database;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import individual.WekaIndividual;
import individual.Individual;

public class WekaDatabase extends Database {

	private Instances isTrainingSet;

	public WekaDatabase() {
		// TODO Auto-generated constructor stub
		database = new Vector<Individual>();
	}

	@Override
	public void parseFile(File file) {
		// TODO Auto-generated method stub
		CSVLoader cvsloader = new CSVLoader();
		try {
			cvsloader.setSource(file);
			isTrainingSet = cvsloader.getDataSet();
			Enumeration<Instance> enDatabase = isTrainingSet.enumerateInstances();
			while (enDatabase.hasMoreElements()) {
				Instance i = enDatabase.nextElement();
				WekaIndividual wi = new WekaIndividual(i);
				database.addElement(wi);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object getDatabaseImplementation() {
		// TODO Auto-generated method stub
		return isTrainingSet;
	}

	@Override
	public void addData(File file) {
		// TODO Auto-generated method stub
		CSVLoader cvsloader = new CSVLoader();
		try {
			cvsloader.setSource(file);
			isTrainingSet.addAll(cvsloader.getDataSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.addData(file);

	}

	@Override
	public void addAttribute(String name) {
		// TODO Auto-generated method stub
		Attribute at = new Attribute(name);
		isTrainingSet.insertAttributeAt(at, isTrainingSet.numAttributes());
		super.addAttribute(name);
	}

}
