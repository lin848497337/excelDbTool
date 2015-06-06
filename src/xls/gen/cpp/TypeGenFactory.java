package xls.gen.cpp;

import xls.meta.EnumMetaData;
import xls.meta.IntegerType;
import xls.meta.StringType;

public class TypeGenFactory {
	
	public static TypeGen getType(xls.meta.Type type){
		if(type instanceof IntegerType){
			return new xls.gen.cpp.IntegerTypeGen();
		}else if(type instanceof StringType){
			return new xls.gen.cpp.StringTypeGen();
		}else if(type instanceof EnumMetaData){
			EnumMetaData ed = (EnumMetaData) type;
			return getType(ed.typeObject);
		}
		return null;
	}
}
