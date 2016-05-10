package optimization;

import models.AbsModeler;
import models.AbsWekaModeler;
import weka.core.Instances;

public abstract class AbsWekaOptimizer extends AbsOptimizer {

	@Override
	public void optimiceParams(AbsModeler modeler) {
		optimiceParams(modeler, ((AbsWekaModeler)modeler).getInstances());
	}

	protected abstract void optimiceParams(AbsModeler modeler, Instances isTrainingSet);
}
