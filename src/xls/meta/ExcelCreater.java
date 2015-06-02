package xls.meta;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreater {
	
	private TableMetaData table;
	
	public ExcelCreater(TableMetaData table){
		this.table = table;
	}
	
	public void doCreate(String outputDir) throws Exception{
		File dirfile = new File(outputDir);
		if(!dirfile.exists()){
			dirfile.mkdir();
		}
		File file = new File(outputDir + File.separator + table.excelFile);
		if(file.exists()){
			loadData(file);
			file.delete();
			file = new File(outputDir + File.separator + table.excelFile);
		}
		Workbook workbook = null;
		if(file.getName().endsWith("xls")){
			workbook = new HSSFWorkbook();
		}else if (file.getName().endsWith("xlsx")){
			workbook = new XSSFWorkbook();
		}else{
			throw new RuntimeException("ºó×º´íÎó!");
		}
	    Sheet sheet = workbook.createSheet("data");
		workbook.setSheetOrder("data", 0);
		int maxRowNum = 65535;
		if(sheet instanceof XSSFSheet){
			maxRowNum = 1048575;
		}
		int roleIndex = 0;
		Row row = sheet.createRow(roleIndex++);
		int index = 0;
		for(ColMetaData col : table.colList){
			Cell cell = row.createCell(index++);
			cell.setCellValue(col.colName);
			
			DataValidation dataValidation = col.typeObject.createDataValidation(col, sheet, maxRowNum, index);
			sheet.addValidationData(dataValidation);
		}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
	
	private void loadData(File file ){
		
	}
	
}
