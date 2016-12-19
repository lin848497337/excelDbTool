package xls.gen.javascript;

import xls.meta.CaseColMetaData;
import xls.meta.EnumMetaData;
import xls.meta.IntegerType;
import xls.meta.StringType;

import java.io.PrintWriter;

public class GenEnumCode {
	private EnumMetaData emd;

	public GenEnumCode(EnumMetaData emd) {
		this.emd = emd;
	}
	
	public void gen(PrintWriter writer){
		writer.print(String.format("var %s = {", emd.name));
		if (emd.typeObject instanceof IntegerType){
			for(int i=0 ; i<emd.caseList.size() ; i++){
				CaseColMetaData c = emd.caseList.get(i);
				writer.print(String.format("%s:%s",c.name,c.value));
				if (i < emd.caseList.size() - 1){
					writer.print(",");
				}
			}
		}else if(emd.typeObject instanceof StringType){
			for(int i=0 ; i<emd.caseList.size() ; i++){
				CaseColMetaData c = emd.caseList.get(i);
				writer.print(String.format("%s:'%s'",c.name,c.value));
				if (i < emd.caseList.size() - 1){
					writer.print(",");
				}
			}
		}
		writer.println("};");
	}
}
