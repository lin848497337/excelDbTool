package xls.meta;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import xls.FileUtil;

public class ExcelCreater {
	
	private TableMetaData table;
	private Map<Integer,Map<String,Object>> dataMap = new HashMap<Integer,Map<String,Object>>();
	
	public ExcelCreater(TableMetaData table){
		this.table = table;
	}
	
	public void doCreate(String outputDir) throws Exception{
		File file = new File(outputDir + File.separator + table.excelFile);
		int oldRow = 0;
		if(file.exists()){
			oldRow = loadData(file);
			file.delete();
			file = new File(outputDir + File.separator + table.excelFile);
		}
		FileUtil.makeFile(file);
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
		for(int i=1 ; i<=oldRow ; i++){
			row = sheet.createRow(roleIndex++);
			index = 0;
			for(ColMetaData col : table.colList){
				Cell cell = row.createCell(index++);
				Map<String,Object> mp = dataMap.get(i);
				Object val = mp.get(col.colName);
				if(val == null){
					continue;
				}
				if(col.typeObject instanceof IntegerType){
					cell.setCellValue(Integer.parseInt(val.toString()));
				}else{
					cell.setCellValue(val.toString());
				}
			}
		}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
	
	private int loadData(File file ) throws Exception{
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheet("data");
		int rowNum = sheet.getLastRowNum();
		for(int i=1 ; i<=rowNum ; i++){
			Row row = sheet.getRow(i);
			int colNum = row.getLastCellNum();
			Map<String,Object> mp = new HashMap<String, Object>();
			dataMap.put(i, mp);
			for(int j=0 ; j<colNum ; j++){
				Cell cell = row.getCell(j);
				ColMetaData colMeta = table.colList.get(j); 
				if(colMeta.typeObject instanceof StringType){
					mp.put(colMeta.colName, cell.getStringCellValue());
				}else if(colMeta.typeObject instanceof EnumMetaData){
					mp.put(colMeta.colName, cell.getStringCellValue());
				}else if(colMeta.typeObject instanceof IntegerType){
					mp.put(colMeta.colName, (int)cell.getNumericCellValue());
				}
			}
		}
		return rowNum;
	}
	
}
