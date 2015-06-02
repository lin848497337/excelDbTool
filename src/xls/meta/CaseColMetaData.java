package xls.meta;

import java.io.PrintWriter;

import org.dom4j.Element;

public class CaseColMetaData implements MetaData {

	public String name;
	public String str;
	public String value;
	public Type type;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void parse(Element el) {
		name = el.attributeValue("name");
		str = el.attributeValue("str");
		value = el.attributeValue("value");
	}

	@Override
	public void compile() {

	}

	@Override
	public void setPackage(String pkg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(PrintWriter writer) {
		writer.println(String.format("\tpublic static final %s %s=%s;",type.getPrintStr(),name,value));
	}

}
