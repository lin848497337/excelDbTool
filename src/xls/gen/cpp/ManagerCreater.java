package xls.gen.cpp;

import java.io.PrintWriter;
import java.util.Map;

import xls.meta.TableMetaData;

public class ManagerCreater {
	public void genHeader(PrintWriter writer){
		writer.println("#ifndef __XML_DATA_MANAGER__H__");
		writer.println("#define __XML_DATA_MANAGER__H__");
		writer.println();
		writer.println("namespace datamanager{");
		writer.println();
		writer.println("class XMLDataManager");
		writer.println("{");
		writer.println("public:");
		writer.println("\tXMLDataManager();");
		writer.println("\t~XMLDataManager();");
		writer.println("\tstatic void loadAll();");
		writer.println("\tstatic void release();");
		writer.println("};");
		writer.println("}");
		
		writer.println();
		writer.println("#endif");
	}
	public void gen(PrintWriter writer ,Map<String,TableMetaData> tableMap){
		writer.println("#include \"XMLDataManager.h\"");
		for(TableMetaData t : tableMap.values()){
			writer.println(String.format("#include \"%s.h\"", t.typeName));
		}
		writer.println();
		writer.println("namespace datamanager{");
		writer.println();
		writer.println("XMLDataManager::XMLDataManager()");
		writer.println("{");
		writer.println("\tXMLDataManager::loadAll();");
		writer.println("}");
		writer.println();
		writer.println("XMLDataManager::~XMLDataManager()");
		writer.println("{");
		writer.println("\tXMLDataManager::release();");
		writer.println("}");
		writer.println();
		writer.println("void XMLDataManager::loadAll()");
		writer.println("{");
		for(TableMetaData t : tableMap.values()){
			String pkg = t.pkg.replaceAll("\\.", "_");
			writer.println(String.format("\t%s::%s::loadAll();", pkg,t.typeName));
		}
		writer.println("}");
		writer.println("void XMLDataManager::release()");
		writer.println("{");
		for(TableMetaData t : tableMap.values()){
			String pkg = t.pkg.replaceAll("\\.", "_");
			writer.println(String.format("\t%s::%s::release();", pkg,t.typeName));
		}
		writer.println("}");
		writer.println("XMLDataManager _xmlDataManager;");
		writer.println("}");
	}
	
}
