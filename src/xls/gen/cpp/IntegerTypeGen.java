package xls.gen.cpp;

public class IntegerTypeGen implements TypeGen {

	@Override
	public String getCppType() {
		return "int";
	}

	@Override
	public String read(String name, String element) {
		return String.format("%s->IntValue(\"%s\")", element,name);
	}

}
