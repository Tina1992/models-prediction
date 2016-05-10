package parameters;

import models.AbsModeler;
import models.AbsWekaModeler;
import weka.core.OptionHandler;

public class WekaSimpleParameter extends AbsWekaParameter{

	public WekaSimpleParameter(char charOp, Object value, String name) {
		super(charOp, value, name);
	}

	@Override
	public void modifyModel(AbsModeler modeler) {
		try {
			OptionHandler oh=((AbsWekaModeler)modeler).getOptionHandler();
			modifyObject(oh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
