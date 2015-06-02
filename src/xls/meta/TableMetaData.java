package xls.meta;

import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
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

	@Override
	public void print(PrintWriter writer) {
		writer.println(String.format("package %s;", pkg.substring(0, pkg.length()-1)));
		writer.println("import xls.meta.XMLManager;");
		writer.println();
		writer.println();
		writer.println(String.format("public class %s extends %s<%s>{", typeName,"XMLManager",typeName));
		writer.println(String.format("\tpublic static java.util.Map<Integer,%s> dataMap = new java.util.HashMap<Integer,%s>();",typeName,typeName));
		for(ColMetaData c : colList){
			c.print(writer);
		}
		writer.println("\t@Override\n\tpublic void load(org.dom4j.Element element){");
		for(ColMetaData c : colList){
			writer.println(String.format("\t\t%s = %s;",c.name, c.typeObject.read(c.name, "element")));
		}
		writer.println("\t}");
		writer.println("\t@Override\n\tpublic void write(org.dom4j.Element element){");
		for(ColMetaData c : colList){
			writer.println(String.format("\t\t%s;", c.typeObject.save(c.name, "element")));
		}
		writer.println("\t}");
		writer.println(String.format("\tpublic static java.util.Map<Integer,%s> getAll(){",typeName));
		writer.println("\t\treturn dataMap;");
		writer.println("\t}");
		writer.println("\tpublic static void load(){");
		writer.println(String.format("\t\torg.dom4j.Element element = XMLManager.getRootElement(%s);", "\"xml/"+getName()+".xml\""));
		writer.println("\t\tjava.util.Iterator<org.dom4j.Element> it = element.elementIterator();");
		writer.println("\t\twhile(it.hasNext()){");
		writer.println("\t\t\torg.dom4j.Element el = it.next();");
		writer.println(String.format("\t\t\t%s data = new %s();",typeName, typeName));
		writer.println("\t\t\tdata.load(el);");
		writer.println("\t\t\tdataMap.put(data.id,data);");
		writer.println("\t\t}");
		writer.println("\t}");
		writer.println("}");
	}

}
