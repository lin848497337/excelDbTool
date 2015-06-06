package xls.gen.cpp;

public class StringTypeGen implements TypeGen {

	@Override
	public String getCppType() {
		return "char *";
	}

	@Override
	public String read(String name, String element) {
		return String.format("%s->Attribute(\"%s\")", element,name);
	}

}
