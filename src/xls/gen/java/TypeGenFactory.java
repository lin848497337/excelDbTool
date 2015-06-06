package xls.gen.java;

import xls.meta.EnumMetaData;
import xls.meta.IntegerType;
import xls.meta.StringType;

public class TypeGenFactory {
	public static TypeGen getTypeGen(xls.meta.Type type){
		if(type instanceof IntegerType){
			return new IntegerTypeGen();
		}
		if(type instanceof StringType){
			return new StringTypeGen();
		}
		if(type instanceof EnumMetaData){
			EnumMetaData ed = (EnumMetaData)type;
			return getTypeGen(ed.typeObject);
		}
		return null;
	}
}
