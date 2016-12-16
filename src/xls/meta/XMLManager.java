package xls.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public abstract class XMLManager <T extends XMLManager>{
	public abstract void load(org.dom4j.Element element);
	public abstract  void write(org.dom4j.Element element);
	public static org.dom4j.Element getRootElement(String strfile){
		try {
			File file = new File(strfile);
			SAXReader saxReader = new SAXReader();
			Document doc;
				doc = saxReader.read(new FileInputStream(file));
			Element el = doc.getRootElement();
			return el;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static org.dom4j.Document createDocument(){
		Document doc = DocumentHelper.createDocument();
		return doc;
	}
	
	public static void saveDoc(Document doc , String fileName){
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
	        format.setEncoding("UTF-8");
	        XMLWriter writer;
			writer = new XMLWriter(new FileWriter(fileName), format);
	        writer.write(doc);
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
