package xls.meta;

import org.dom4j.Element;

public class RangeAttri {

	public final int min;
	public final int max;
	
	public RangeAttri(Element el){
		String val = el.attributeValue("range");
		String[] rg = val.split("-");
		min = Integer.parseInt(rg[0]);
		max = Integer.parseInt(rg[1]);
	}
	
	public RangeAttri(String val){
		String[] rg = val.split("-");
		min = Integer.parseInt(rg[0]);
		max = Integer.parseInt(rg[1]);
	}
}
