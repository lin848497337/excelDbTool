package xls.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import xls.FileUtil;
import xls.gen.java.GenEnumCode;
import xls.gen.java.GenTableCode;
import xls.meta.EnumMetaData;
import xls.meta.MetaDataManager;
import xls.meta.TableMetaData;

public class GenCodeManager {
	
	private String genDir;
	
	private GenCodeManager(){}
	
	private static GenCodeManager instance = new GenCodeManager();
	
	public static GenCodeManager getInstance(){
		return instance;
	}
	
	public void init(String dir){
		genDir = dir;
	}
	
	public void genJava() throws IOException{
		Map<String,EnumMetaData> enums = MetaDataManager.getInstance().getEnumMap();
		for(EnumMetaData ed : enums.values()){
			File file = new File(genDir + File.separator +ed.pkg.replaceAll("\\.", "\\\\") + File.separator + ed.name+".java");
			FileUtil.makeFile(file);
			PrintWriter writer = new PrintWriter(file);
			GenEnumCode gen = new GenEnumCode(ed);
			gen.gen(writer);
			writer.close();
		}
		Map<String,TableMetaData> tables = MetaDataManager.getInstance().getTableMap();
		for(TableMetaData table : tables.values()){
			xls.gen.java.GenTableCode gen = new GenTableCode(table);
			String dir = table.pkg.replaceAll("\\.", "\\\\");
			File file = new File(genDir + File.separator + dir + table.typeName+".java");
			FileUtil.makeFile(file);
			PrintWriter writer = new PrintWriter(file);
			gen.gen(writer);
			System.out.println("create "+file.getName());
			writer.close();
		}
	}
}
