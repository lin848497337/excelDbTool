package xls.meta;

import org.dom4j.Element;

public class ColMetaData implements MetaData {

	public String name;
	public String colName;
	public String typeVal;
	public String rangeVal;
	public Type typeObject;
	public RangeAttri range;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void parse(Element el) {
		name = el.attributeValue("name");
		colName = el.attributeValue("colName");
		typeVal = el.attributeValue("type");
		rangeVal = el.attributeValue("range");
	}

	@Override
	public void compile() {
		typeObject = TypeManager.getInstance().getType(typeVal);
		if(rangeVal != null && !rangeVal.equals("")){
			range = new RangeAttri(rangeVal);
		}
	}

	@Override
	public void setPackage(String pkg) {
		// TODO Auto-generated method stub
		
	}

}
