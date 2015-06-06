package xls.gen.cpp;

public class StringTypeGen implements TypeGen {

	@Override
	public String getCppType() {
		return "std::string";
	}

	@Override
	public String read(String name, String element) {
		return String.format("std::string(%s->Attribute(\"%s\"))", element,name);
	}

}
