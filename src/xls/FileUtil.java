package xls;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	
	public static void makeDir(File dir){
		if(dir != null && !dir.exists()){
			File parent = dir.getParentFile();
			makeDir(parent);
			dir.mkdir();
		}
	}
	
	public static void makeFile(File file) throws IOException{
		if(!file.exists()){
			File parent = file.getParentFile();
			makeDir(parent);
			file.createNewFile();
		}
		
	}
}
