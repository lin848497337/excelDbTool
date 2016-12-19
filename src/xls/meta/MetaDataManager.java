package xls.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import xls.gen.GenCodeManager;

public class MetaDataManager {
	private String metaDir;
	private static MetaDataManager instance = new MetaDataManager();
	private Map<String,TableMetaData> tableMap = new HashMap<String,TableMetaData>();
	private Map<String,EnumMetaData> enumMap = new HashMap<String,EnumMetaData>();
	private MetaDataManager(){}
	private Stack<String> namespaceStack = new Stack<String>();
	private String excelDir;
	private String codeDir;
	private String codeType;
	private String dataDir;
	private Map<String,String> templateMap = new HashMap<String, String>();
	private ActionTypeEnum action;

	public static MetaDataManager getInstance(){
		return instance;
	}
	
	public void doAction() throws Exception{
		switch(action)
		{
		case CREATE_EXCEL:
			System.out.println("create excel");
			createExcel();
			break;
		case GEN_CODE:
			System.out.println("create code");
			GenCodeManager.getInstance().init(codeDir);
			if(codeType.equals("java")){
				GenCodeManager.getInstance().genJava();
			}else if(codeType.equals("cpp")){
				GenCodeManager.getInstance().genCpp();
			}else if(codeType.equals("js")){
				GenCodeManager.getInstance().genJavascript();
			}
			break;
		case GEN_XML:
			System.out.println("create xml");
			genXML();
			break;
		case GEN_JSON:
			System.out.println("create json");
			genJSON();
			break;

		}
		System.out.println("complete action");
	}
	
	public void initArg(String []args){
		if(args.length != 12){
			printUsage();
			throw new RuntimeException("wrong arguments");
		}
		for(int i=0 ; i<args.length ; i++){
			if(args[i].equals("-meta")){
				metaDir = args[++i];
			}else if(args[i].equals("-excel")){
				excelDir = args[++i];
			}else if(args[i].equals("-code")){
				codeDir = args[++i];
			}else if(args[i].equals("-data")){
				dataDir = args[++i];
			}else if(args[i].equals("-action")){
				action = ActionTypeEnum.getValue(args[++i]);
				if (action == null) {
					printUsage();
					throw new RuntimeException("wrong arguments of action");
				}
			}else if(args[i].equals("-lang")){
				codeType = args[++i];
			}
		}
	}
	
	public String getXMLDir(){
		return dataDir;
	}
	
	public void loadTemplate() throws IOException{
		File fileDir = new File("template");
		File temps[] = fileDir.listFiles();
		StringBuilder sb = new StringBuilder();
		for(File f : temps){
			sb.delete(0, sb.length());
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			templateMap.put(f.getName(), sb.toString());
		}
	}
	
	public String getTemplate(String type){
		return templateMap.get(type);
	}
	
	public Map<String,TableMetaData> getTableMap(){
		return tableMap;
	}
	
	public Map<String,EnumMetaData> getEnumMap(){
		return enumMap;
	}
	
	private void printUsage(){
		StringBuilder sb = new StringBuilder();
		sb.append("usage : \n");
		sb.append("\t-meta metaDir -bean beanDir -excel excelDir -lang [java|cpp] -code codeDir -data xml -action [excel|code |xml|json]");
		System.out.println(sb.toString());
	}
	
	public void compile(){
		for(TableMetaData data : tableMap.values()){
			data.compile();
		}
	}
	
	public void load() throws Exception{
		loadMeta("main.xml");
	}
	
	private void loadMeta(String metaFile) throws Exception{
		if(metaDir != null){
			metaFile = metaDir + File.separator + metaFile;
		}
		File file = new File(metaFile);
		if(!file.exists() ){
			throw new RuntimeException(metaFile+" not exist");
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
					enumMap.put(enumMetaData.getName(), enumMetaData);
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
	
	
	public void createExcel() throws Exception{
		for(TableMetaData table : tableMap.values()){
			ExcelCreater creater = new ExcelCreater(table);
			creater.doCreate(excelDir);
		}
	}
	
	public void genXML() throws Exception{
		File dir = new File(dataDir);
		if(!dir.exists()){
			dir.mkdir();
		}
		for(TableMetaData table : tableMap.values()){
			XMLCreater creater = new XMLCreater(table);
			creater.doCreate(excelDir , dataDir);
		}
	}

	public void genJSON() throws Exception{
		File dir = new File(dataDir);
		if(!dir.exists()){
			dir.mkdir();
		}
		for(TableMetaData table : tableMap.values()){
			JSONCreator creater = new JSONCreator(table);
			creater.doCreate(excelDir , dataDir);
		}
	}
}
