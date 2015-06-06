package xls.gen.cpp;

import java.io.PrintWriter;

import xls.meta.CaseColMetaData;
import xls.meta.EnumMetaData;

public class EnumTypeGen {
	private xls.meta.EnumMetaData enumData;

	public EnumTypeGen(EnumMetaData enumData) {
		this.enumData = enumData;
	}

	public void gen(PrintWriter writer){
		String onlyName = String.format("__%S__H__", enumData.getName().replaceAll("\\.", "_").toUpperCase());
		writer.println(String.format("#ifndef %s",onlyName));
		writer.println(String.format("#define %s", onlyName));
		writer.println(String.format("namespace %s{", enumData.pkg.replaceAll("\\.", "_").toLowerCase()));
		writer.println();
		writer.println();
		writer.println(String.format("enum %s{",enumData.name));
		for(CaseColMetaData cc : enumData.caseList){
			writer.println(String.format("\t%s=%s,",cc.name,cc.value));
		}
		writer.println("}");
		writer.println("}");
		
		
		writer.println("#endif");
	}
}
