package xls.gen.cpp;

import java.io.PrintWriter;

import xls.meta.ColMetaData;
import xls.meta.TableMetaData;

public class GenTableCode {
	private TableMetaData table;

	public GenTableCode(TableMetaData table) {
		this.table = table;
	}
	
	public void genHeader(PrintWriter writer){
		String onlyName = String.format("__%S__H__", table.getName().replaceAll("\\.", "_").toUpperCase());
		writer.println(String.format("#ifndef %s",onlyName));
		writer.println(String.format("#define %s", onlyName));
		
		writer.println();
		writer.println("#include \"cocos2d.h\"");
		writer.println("#include \"tinyxml2\tinyxml2.h\"");
		writer.println();
		writer.println();
		writer.println(String.format("class %s",table.typeName));
		writer.println("{");
		writer.println("public:");
		for(ColMetaData col : table.colList){
			xls.gen.cpp.TypeGen type = TypeGenFactory.getType(col.typeObject);
			writer.println(String.format("\t%s %s;",type.getCppType(),col.name ));
		}
		writer.println("\tvoid read(tinyxml2::XMLElement *surface);");
		writer.println("\tstatic void loadAll();");
		writer.println(String.format("\tstatic std::map<int,%s*> dataMap",table.typeName));
		writer.println("};");
		
		writer.println("#endif");
	}
	
	public void gen(PrintWriter writer){
		//just write cpp file
		writer.println(String.format("#include \"%s.h\"",table.typeName));
		writer.println();
		writer.println();
		writer.println("using namespace tinyxml2;");
		writer.println();
		writer.println();
		writer.println(String.format("void %s::read(XMLElement *surface)", table.typeName));
		writer.println("{");
		writer.println("\tconst XMLAttribute *attributeOfSurface = surface->FirstAttribute();");
		for(ColMetaData col : table.colList){
			xls.gen.cpp.TypeGen type = TypeGenFactory.getType(col.typeObject);
			writer.println(String.format("\t\tthis->%s = %s;", col.name,type.read(col.name, "attributeOfSurface")));
		}
		writer.println("}");
		writer.println();
		writer.println(String.format("void %s::loadAll()", table.typeName));
		writer.println("{");
		writer.println("\ttinyxml2::XMLDocument doc;");
		writer.println(String.format("\tdoc.LoadFile(%s.xml);", table.getName()));
		writer.println("\tXMLElement *root=doc.RootElement();");
		writer.println(String.format("\tXMLElement *surface=scene->FirstChildElement(\"%s\");", table.getName()));
		writer.println("\twhile(surface)");
		writer.println("\t{");
		writer.println(String.format("\t\t%s *data = new %s()",table.typeName,table.typeName));
		writer.println("\t\tdata->read(surface);");
		writer.println("\t\tthis->dataMap->insert(data->id,data);");
		writer.println("\t\t");
		writer.println("\t}");
		writer.println("}");
		
	}
}