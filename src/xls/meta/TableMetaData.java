package xls.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

public class TableMetaData implements MetaData {

	public String typeName;
	public String excelFile;
	public String pkg;
	
	public KeyColMetaData keyCol ;
	public List<ColMetaData> colList = new ArrayList<ColMetaData>();
	
	@Override
	public String getName() {
		return pkg + typeName;
	}

	@Override
	public void parse(Element el) {
		typeName = el.attributeValue("name");
		excelFile = el.attributeValue("excelFile");
		Iterator<Element> it = el.elementIterator();
		while(it.hasNext()){
			Element cme = it.next();
			if(cme.getName().equals("key")){
				keyCol = new KeyColMetaData();
				keyCol.parse(cme);
			}else if(cme.getName().equals("variable")){
				ColMetaData cmd = new ColMetaData();
				cmd.parse(cme);
				colList.add(cmd);
			}
			
		}
	}

	@Override
	public void compile() {
	//	keyCol.compile();
		for(ColMetaData c : colList){
			c.compile();
		}
	}

	@Override
	public void setPackage(String pkg) {
		this.pkg = pkg;
	}

}
