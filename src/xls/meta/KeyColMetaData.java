package xls.meta;

import org.dom4j.Element;

public class KeyColMetaData implements MetaData {

	private String name;
	private String colName;
	private String typeName;
	private RangeAttri range;
	private Type typeObject;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void parse(Element el) {
		name = el.attributeValue("name");
		colName = el.attributeValue("colName");
		typeName = el.attributeValue("type");
		range = new RangeAttri(el);
	}

	@Override
	public void compile() {
		typeObject = TypeManager.getInstance().getType(typeName);
	}

	@Override
	public void setPackage(String pkg) {
		// TODO Auto-generated method stub
		
	}

}
