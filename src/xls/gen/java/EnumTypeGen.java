package xls.gen.java;

public class EnumTypeGen implements TypeGen {

	private TypeGen gen;
	
	public EnumTypeGen(TypeGen gen) {
		this.gen = gen;
	}
	
	@Override
	public String save(String name, String element) {
		return gen.save(name, element);
	}

	@Override
	public String read(String name, String element) {
		return gen.read(name, element);
	}

	@Override
	public String getJavaType() {
		return gen.getJavaType();
	}

}
