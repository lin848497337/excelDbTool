package xls.gen.java;

import java.io.PrintWriter;

import xls.meta.ColMetaData;

public class ColGen {
	private ColMetaData col;
	public ColGen(ColMetaData col) {
		this.col = col;
	}

	public void print(PrintWriter writer){
		xls.gen.java.TypeGen gen = TypeGenFactory.getTypeGen(col.typeObject);
		writer.println(String.format("\tpublic %s %s;",gen.getJavaType(),col.name));
	}
}
