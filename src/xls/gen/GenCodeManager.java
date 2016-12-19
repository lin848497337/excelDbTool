package xls.gen;

import xls.FileUtil;
import xls.gen.cpp.ManagerCreater;
import xls.gen.java.GenEnumCode;
import xls.gen.java.GenTableCode;
import xls.meta.EnumMetaData;
import xls.meta.MetaDataManager;
import xls.meta.TableMetaData;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
	
	public void genCpp() throws IOException{
		Map<String,EnumMetaData> enums = MetaDataManager.getInstance().getEnumMap();
		for(EnumMetaData ed : enums.values()){
			File file = new File(genDir + File.separator + ed.name+".h");
			FileUtil.makeFile(file);
			PrintWriter writer = new PrintWriter(file);
			xls.gen.cpp.EnumTypeGen gen = new xls.gen.cpp.EnumTypeGen(ed);
			gen.gen(writer);
			writer.close();
		}
		Map<String,TableMetaData> tables = MetaDataManager.getInstance().getTableMap();
		for(TableMetaData table : tables.values()){
			xls.gen.cpp.GenTableCode gen = new xls.gen.cpp.GenTableCode(table);
			File file = new File(genDir + File.separator + table.typeName+".h");
			FileUtil.makeFile(file);
			PrintWriter writer = new PrintWriter(file);
			gen.genHeader(writer);
			System.out.println("create "+file.getName());
			writer.close();
			file = new File(genDir + File.separator + table.typeName+".cpp");
			FileUtil.makeFile(file);
			writer = new PrintWriter(file);
			gen.gen(writer);
			System.out.println("create "+file.getName());
			writer.close();
		}
		File file = new File(genDir + File.separator + "XMLDataManager"+".h");
		FileUtil.makeFile(file);
		PrintWriter writer = new PrintWriter(file);
		ManagerCreater creater = new ManagerCreater();
		creater.genHeader(writer);
		writer.close();
		file = new File(genDir + File.separator + "XMLDataManager"+".cpp");
		FileUtil.makeFile(file);
		writer = new PrintWriter(file);
		creater.gen(writer, tables);
		writer.close();
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

	public void genJavascript() throws IOException{
		Map<String,EnumMetaData> enums = MetaDataManager.getInstance().getEnumMap();
		for(EnumMetaData ed : enums.values()){
			File file = new File(genDir + File.separator +ed.pkg.replaceAll("\\.", "\\\\") + File.separator + ed.name+".js");
			FileUtil.makeFile(file);
			PrintWriter writer = new PrintWriter(file);
			xls.gen.javascript.GenEnumCode gen = new xls.gen.javascript.GenEnumCode(ed);
			gen.gen(writer);
			writer.close();
		}
	}
}
