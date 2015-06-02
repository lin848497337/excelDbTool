package xls.meta;

import java.util.HashMap;
import java.util.Map;

public class TypeManager {
	
	private static TypeManager instance = new TypeManager();
	private Map<String,Type> typeMap = new HashMap<String,Type>();
	static{
		instance.registerType(new IntegerType());
		instance.registerType(new StringType());
	}
	
	private TypeManager(){}
	
	public static TypeManager getInstance(){
		return instance;
	}
	
	public Type getType(String typeName){
		Type type = typeMap.get(typeName);
		type.compile();
		return type;
	}
	
	public void registerType(Type type){
		typeMap.put(type.name(), type);
	}
	
}
