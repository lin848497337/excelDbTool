package xls.gen.java;

public class StringTypeGen implements TypeGen {

	
	
	@Override
	public String save(String name, String element) {
		return String.format("%s.addAttribute(\"%s\",%s)", element,name,name);
	}

	@Override
	public String read(String name, String element) {
		return String.format("%s.attributeValue(\"%s\")", element,name);
	}

	@Override
	public String getJavaType() {
		return "String";
	}

}
