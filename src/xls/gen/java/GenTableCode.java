package xls.gen.java;

import java.io.PrintWriter;

import xls.meta.ColMetaData;
import xls.meta.TableMetaData;

public class GenTableCode{
	private TableMetaData table;

	public GenTableCode(TableMetaData table) {
		this.table = table;
	}
	
	public void gen(PrintWriter writer){
		writer.println(String.format("package %s;", table.pkg.substring(0, table.pkg.length()-1)));
		writer.println("import xls.meta.XMLManager;");
		writer.println();
		writer.println();
		writer.println(String.format("public class %s extends %s<%s>{", table.typeName,"XMLManager",table.typeName));
		writer.println(String.format("\tpublic static java.util.Map<Integer,%s> dataMap = new java.util.HashMap<Integer,%s>();",table.typeName,table.typeName));
		for(ColMetaData c : table.colList){
			ColGen gen = new ColGen(c);
			gen.print(writer);
		}
		writer.println("\t@Override\n\tpublic void load(org.dom4j.Element element){");
		for(ColMetaData c : table.colList){
			xls.gen.java.TypeGen gen = TypeGenFactory.getTypeGen(c.typeObject);
			writer.println(String.format("\t\t%s = %s;",c.name, gen.read(c.name, "element")));
		}
		writer.println("\t}");
		writer.println("\t@Override\n\tpublic void write(org.dom4j.Element element){");
		for(ColMetaData c : table.colList){
			xls.gen.java.TypeGen gen = TypeGenFactory.getTypeGen(c.typeObject);
			writer.println(String.format("\t\t%s;", gen.save(c.name, "element")));
		}
		writer.println("\t}");
		writer.println(String.format("\tpublic static java.util.Map<Integer,%s> getAll(){",table.typeName));
		writer.println("\t\treturn dataMap;");
		writer.println("\t}");
		writer.println("\tpublic static void load(){");
		writer.println(String.format("\t\torg.dom4j.Element element = XMLManager.getRootElement(%s);", "\"xml/"+table.getName()+".xml\""));
		writer.println("\t\tjava.util.Iterator<org.dom4j.Element> it = element.elementIterator();");
		writer.println("\t\twhile(it.hasNext()){");
		writer.println("\t\t\torg.dom4j.Element el = it.next();");
		writer.println(String.format("\t\t\t%s data = new %s();",table.typeName, table.typeName));
		writer.println("\t\t\tdata.load(el);");
		writer.println("\t\t\tdataMap.put(data.id,data);");
		writer.println("\t\t}");
		writer.println("\t}");
		writer.println("}");
	}
}
