package optimization;

import java.util.Vector;

import models.AbsModeler;
import models.AbsWekaClassifier;
import models.SMOregClassifier;
import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import parameters.WekaKernelParameter;
import parameters.WekaSimpleParameter;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.meta.GridSearch;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Utils;
import weka.core.setupgenerator.AbstractParameter;
import weka.core.setupgenerator.MathParameter;

public class WekaKernelOptimizer extends AbsWekaOptimizer {

	private Vector<WekaKernelParameter> kernels = new Vector<WekaKernelParameter>();

	public void setKernelOptions() {
		NormalizedPolyKernel npk = new NormalizedPolyKernel(); // exponent (-E)
		PolyKernel pk = new PolyKernel(); // exponent (-E)
		Puk p = new Puk(); // omega (-O) - sigma (-S)
		RBFKernel rbfk = new RBFKernel(); // gamma (-G)

		WekaKernelParameter npkparam = new WekaKernelParameter(npk);
		WekaKernelParameter pkparam = new WekaKernelParameter(pk);
		WekaKernelParameter pukparam = new WekaKernelParameter(p);
		WekaKernelParameter rbfkparam = new WekaKernelParameter(rbfk);

		Vector<AbsWekaParameter> exponents = new Vector<AbsWekaParameter>();
		exponents.add(new WekaSimpleParameter('E', 1, "exponent"));
		Vector<AbsWekaParameter> pukparams = new Vector<AbsWekaParameter>(); // PUK
		pukparams.add(new WekaSimpleParameter('O', 1, "omega"));
		pukparams.add(new WekaSimpleParameter('S', 1, "sigma"));
		Vector<AbsWekaParameter> rbfkparams = new Vector<AbsWekaParameter>();
		rbfkparams.add(new WekaSimpleParameter('G', 1, "gamma"));

		npkparam.setKernelOptions(exponents);
		pkparam.setKernelOptions(exponents);
		pukparam.setKernelOptions(pukparams);
		rbfkparam.setKernelOptions(rbfkparams);

		kernels.add(npkparam);
		kernels.add(rbfkparam);
		kernels.add(pkparam);
		kernels.add(pukparam);

	}

	private AbstractClassifier[] bestClassifiers(AbsModeler modeler, Instances dataset) throws Exception {
		MultiSearch ms = new MultiSearch();
		SMOreg smoreg = new SMOreg();

		ms.setEvaluation(new SelectedTag(GridSearch.EVALUATION_CC, GridSearch.TAGS_EVALUATION));

		setKernelOptions();

		AbstractParameter[] lpparam = new AbstractParameter[modeler.getParameters().size() - 1];
		int index = 0;
		AbstractClassifier[] cls = new AbstractClassifier[kernels.size()];
		for (AbsParameter param : modeler.getParameters()) {
			if (!param.getClass().getName().contains("Kernel")) {
				lpparam[index] = new MathParameter();
				lpparam[index].setOptions(
						Utils.splitOptions("-property " + ((AbsWekaParameter) param).getParameterString().charAt(1)
								+ " -min 0.0 -max 5.0 -step 1.0 -base 10.0 -expression I"));
				index++;
			}
		}

		int kindex = 0;
		for (WekaKernelParameter k : kernels) {
			AbstractParameter[] kparam = new AbstractParameter[k.getKernelParams().size()];
			int i = 0;
			for (AbsParameter kernelparam : k.getKernelParams()) {
				AbstractParameter aux = new MathParameter();
				aux.setOptions(Utils.splitOptions("-property kernel." + ((AbsWekaParameter) kernelparam).getName()
						+ " -min 2.0 -max 5.0 -step 1.0 -base 10.0 -expression I"));
				kparam[i] = aux;
				i++;
			}
			AbstractParameter[] finalParameters = new AbstractParameter[lpparam.length + kparam.length];
			System.arraycopy(lpparam, 0, finalParameters, 0, lpparam.length);
			System.arraycopy(kparam, 0, finalParameters, lpparam.length, kparam.length);

			ms.setSearchParameters(finalParameters);

			smoreg.setKernel(k.getKernel());
			ms.setClassifier(smoreg);

			ms.buildClassifier(dataset);
			cls[kindex] = (AbstractClassifier) ms.getBestClassifier();
			kindex++;

		}
		return cls;
	}

	@Override
	protected void optimiceParams(AbsModeler modeler, Instances isTrainingSet) {
		// TODO Auto-generated method stub
		try {
			MultiScheme ms = new MultiScheme();
			AbstractClassifier[] optclassifiers = bestClassifiers(modeler, isTrainingSet);
			ms.setClassifiers(optclassifiers);
			ms.buildClassifier(isTrainingSet);
			((SMOregClassifier) modeler).setClassifier(optclassifiers[ms.getBestClassifierIndex()]);
			((AbsWekaClassifier) modeler)
					.parseOptions(((SMOreg) (optclassifiers[ms.getBestClassifierIndex()])).getOptions());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
