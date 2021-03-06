package models;

import java.util.Vector;

import individual.Individual;

public abstract class AbsClusterer extends AbsModeler{
	
	public abstract int getCluster(Individual individual);
	
	public abstract Vector<Individual> getClusterMember(int cluster);

}
