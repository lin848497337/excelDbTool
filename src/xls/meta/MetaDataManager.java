package xls.meta;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MetaDataManager {
	private String metaDir;
	private static MetaDataManager instance = new MetaDataManager();
	private Map<String,TableMetaData> tableMap = new HashMap<String,TableMetaData>();
	private MetaDataManager(){}
	private Stack<String> namespaceStack = new Stack<String>();
	public static MetaDataManager getInstance(){
		return instance;
	}
	
	public void compile(){
		for(TableMetaData data : tableMap.values()){
			data.compile();
		}
	}
	
	public void loadMeta(String metaFile) throws Exception{
		if(metaDir != null){
			metaFile = metaDir + File.separator + metaFile;
		}
		File file = new File(metaFile);
		if(!file.exists() ){
			throw new RuntimeException("meta not exist");
		}
		if(metaDir == null){
			metaDir = file.getParent();
		}
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new FileInputStream(file));
		Element el = doc.getRootElement();
		parseElement(el);
	}
	
	private void parseElement(Element el) throws Exception{
		Iterator<Element> e = el.elementIterator();
		while(e.hasNext()){
			el = e.next();
			if(el.getName().equals("import")){
				loadMeta(el.attributeValue("src"));
			}else
			if(el.getName().equals("namespace")){
				namespaceStack.push(el.attributeValue("name"));
				parseElement(el);
				namespaceStack.pop();
			}else
			if(el.getName().equals("table")){
				TableMetaData tableData = new TableMetaData();
				tableData.parse(el);
				tableData.setPackage(getCurrentPackage());
				tableMap.put(tableData.getName(), tableData);
			}else
				if(el.getName().equals("enum")){
					EnumMetaData enumMetaData = new EnumMetaData();
					enumMetaData.parse(el);
					enumMetaData.setPackage(getCurrentPackage());
					TypeManager.getInstance().registerType(enumMetaData);
				}
		}
	}
	
	private String getCurrentPackage(){
		StringBuilder sb = new StringBuilder();
		for(int i=0 ; i<namespaceStack.size() ; i++){
			sb.append(namespaceStack.get(i));
			sb.append(".");
		}
		return sb.toString();
	}
	
	
	public void createExcel(String outputDir) throws Exception{
		for(TableMetaData table : tableMap.values()){
			ExcelCreater creater = new ExcelCreater(table);
			creater.doCreate(outputDir);
		}
	}
}
