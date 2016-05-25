package optimization;

import models.AbsModeler;
import models.AbsWekaModeler;
import weka.core.Instances;

public abstract class AbsWekaOptimizer extends AbsOptimizer {

	protected double minValue = -1;
	protected double maxValue = -1;

	public void setMinValue(double min) {
		minValue = min;
	}

	public void setMaxValue(double max) {
		maxValue = max;
	}

	@Override
	public void optimiceParams(AbsModeler modeler) {
		optimiceParams(modeler, ((AbsWekaModeler) modeler).getInstances());
	}

	protected abstract void optimiceParams(AbsModeler modeler, Instances isTrainingSet);
}
