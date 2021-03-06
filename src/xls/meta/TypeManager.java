package xls.meta;

import java.util.HashMap;
import java.util.Map;

public class TypeManager {
	
	private static TypeManager instance = new TypeManager();
	private Map<String,Type> typeMap = new HashMap<String,Type>();
	static{
		instance.registerType(new IntegerType());
		instance.registerType(new DoubleType());
		instance.registerType(new StringType());
	}
	
	private TypeManager(){}
	
	public static TypeManager getInstance(){
		return instance;
	}
	
	public Type getType(String typeName){
		Type type = typeMap.get(typeName);
		if (type == null){
			throw new RuntimeException("can not find type : for typeName = "+typeName);
		}
		type.compile();
		return type;
	}
	
	public void registerType(Type type){
		if(typeMap.put(type.name(), type) != null){
			throw new RuntimeException("duplicate type for type : "+type.name());
		}
	}
	
}
