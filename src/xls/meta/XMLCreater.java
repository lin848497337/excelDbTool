package xls.meta;

import java.io.File;
import java.io.FileWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class XMLCreater {
	private TableMetaData table;

	public XMLCreater(TableMetaData table) {
		this.table = table;
	}
	

	public void doCreate(String excelDir ,String parentDir) throws Exception{
		File excelfile = new File(excelDir + File.separator + table.excelFile);
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("data");
		doc.add(root);
		Workbook workbook = null;
		workbook = WorkbookFactory.create(excelfile);
		Sheet sheet = workbook.getSheet("data");
		int rowNum = sheet.getLastRowNum();
		for(int i=1 ; i<=rowNum ; i++){
			Row row = sheet.getRow(i);
			Element el = DocumentHelper.createElement(table.getName());
			int colNum = row.getLastCellNum();
			for(int j=0 ; j<colNum ; j++){
				Cell cell = row.getCell(j);
				ColMetaData colMeta = table.colList.get(j); 
				if(colMeta.typeObject instanceof StringType){
					String name = cell.getStringCellValue();
					el.addAttribute(colMeta.name, name);
				}else if(colMeta.typeObject instanceof EnumMetaData){
					EnumMetaData typeData = (EnumMetaData) colMeta.typeObject;
					String val = cell.getStringCellValue();
					for(CaseColMetaData cc : typeData.caseList){
						if(cc.str.equals(val)){
							el.addAttribute(colMeta.name, cc.value);
						}
					}
				}else if(colMeta.typeObject instanceof IntegerType){
					el.addAttribute(colMeta.name, (int)cell.getNumericCellValue()+"");
				}
			}
			root.add(el);
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");// 设置XML文件的编码格式
        XMLWriter writer;
		writer = new XMLWriter(new FileWriter(parentDir + File.separator + table.getName()+".xml"), format);
        writer.write(doc);
        writer.close();
	}
}
