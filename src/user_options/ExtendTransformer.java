package user_options;

import java.io.File;

import database.Database;

public class ExtendTransformer implements DatabaseTransformer {
	
	private File file;
	
	public ExtendTransformer(File file){
		this.file=file;
	}
	
	public void transformDatabase(Database database){
		database.addData(file);
	}

}
