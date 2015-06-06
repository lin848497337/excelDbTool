package xls.gen.java;

public interface TypeGen {
	public String getJavaType();
	public String save(String name, String element);
	public String read(String name, String element);
}
