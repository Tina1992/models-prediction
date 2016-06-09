package libraries;

import java.util.Vector;

import models.AbsModeler;
import parameters.AbsParameter;
import parameters.AbsWekaParameter;
import parameters.WekaKernelParameter;
import parameters.WekaSimpleParameter;
import weka.core.OptionHandler;
import weka.core.Utils;

public class WekaLibrary{
	
	public static void addParameter(AbsParameter modelParameter, AbsModeler model) {
		model.addParameter(modelParameter);
		modelParameter.modifyModel(model);
	}

	public static int getSimpleParamsCount(AbsModeler model){
		Vector<AbsParameter> modelParameters_=model.getParameters();
		int i=0;
		for (AbsParameter p: modelParameters_){
			i+=((AbsWekaParameter)p).getSimpleParametersCount();
		}
		return i;
	}
	
	public static void parseOptions(String[] options, AbsModeler model) {
		int index = 0;
		for (index = 0; index < options.length; index = index + 2) {
			try {
				if (!options[index].isEmpty() && (options[index].length() == 2)) {
					AbsParameter p = model.getParameter(options[index].charAt(1));
					((WekaSimpleParameter) p).setValue(Double.valueOf(options[index + 1]));
				}
			} catch (Exception e) {
				System.out.println("Parametro no parseado");
			}
		}
		for (AbsParameter p : model.getParameters()) {
			p.modifyModel(model);
		}
	}
	
	public static void modifyObject(AbsWekaParameter parameter, OptionHandler oh) throws Exception {
		String[] currOptions = oh.getOptions();
		String[] newOptions = Utils.splitOptions(parameter.getParameterString());
		for (int i=0;i<currOptions.length;i++){
			if (newOptions[0].equals(currOptions[i])){
				currOptions[i+1]=newOptions[1];
			}
		}
		oh.setOptions(currOptions);
	}

	public static void parseKernelOptions(String[] options, WekaKernelParameter kernel) {
		// TODO Auto-generated method stub
		int index=0;
		for (index=0;index<options.length;index=index+2){
			try{
			if (!options[index].isEmpty()&&(options[index].length()==2)){
				WekaSimpleParameter p=new WekaSimpleParameter(options[index].charAt(1), Double.valueOf(options[index+1]), String.valueOf(options[index].charAt(1)) );
				kernel.addKernelOption(p);
			}}
			catch  (Exception e){
				System.err.println(options[index]);
				System.err.println("Error de parametro");
			}
		}
	}
	
}
