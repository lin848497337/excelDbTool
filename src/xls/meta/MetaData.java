package xls.meta;

import org.dom4j.Element;

public interface MetaData {
	void setPackage(String pkg);
	String getName();
	void parse(Element el);
	void compile();
}
