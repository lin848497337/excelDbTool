package xls.gen.java;

import java.io.PrintWriter;

import xls.meta.CaseColMetaData;
import xls.meta.EnumMetaData;

public class GenEnumCode {
	private EnumMetaData emd;

	public GenEnumCode(EnumMetaData emd) {
		this.emd = emd;
	}
	
	public void gen(PrintWriter writer){
		writer.println(String.format("package %s;", emd.pkg.substring(0, emd.pkg.length()-1)));
		writer.println();
		writer.println();
		writer.println(String.format("public class %s {", emd.name));
		for(CaseColMetaData c : emd.caseList){
			xls.gen.java.TypeGen type = TypeGenFactory.getTypeGen(c.type);
			writer.println(String.format("\tpublic static final %s %s=%s;",type.getJavaType(),c.name,c.value));
		}
		writer.println("}");
	}
}
