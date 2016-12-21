package xls.meta;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by charles on 2016/12/16.
 */
public class JSONCreator {
    private TableMetaData table;

    public JSONCreator(TableMetaData table) {
        this.table = table;
    }

    public void doCreate(String excelDir ,String parentDir) throws Exception{
        File excelfile = new File(excelDir + File.separator + table.excelFile);
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(parentDir + File.separator + table.typeName+".js")));
        Workbook workbook = null;
        workbook = WorkbookFactory.create(excelfile);
        Sheet sheet = workbook.getSheet("data");
        int rowNum = sheet.getLastRowNum();
        printWriter.printf("var %s = [",table.typeName);
        for(int i=1 ; i<=rowNum ; i++){
            Row row = sheet.getRow(i);
            int colNum = row.getLastCellNum();
            printWriter.printf("{");
            for(int j=0 ; j<colNum ; j++){
                Cell cell = row.getCell(j);
                ColMetaData colMeta = table.colList.get(j);
                if(colMeta.typeObject instanceof StringType){
                    String name = cell.getStringCellValue();
                    printWriter.printf("'%s' : '%s'",colMeta.name, name);
                }else if(colMeta.typeObject instanceof EnumMetaData){
                    EnumMetaData typeData = (EnumMetaData) colMeta.typeObject;
                    String val = cell.getStringCellValue();
                    for(CaseColMetaData cc : typeData.caseList){
                        if(cc.str.equals(val)){
                            printWriter.printf("'%s' : %s",colMeta.name, cc.value);
                        }
                    }
                }else if(colMeta.typeObject instanceof IntegerType){
                    printWriter.printf("'%s': %d",colMeta.name, new Double(cell.getNumericCellValue()).intValue());
                }else if(colMeta.typeObject instanceof DoubleType){
                    printWriter.printf("'%s': %f",colMeta.name, cell.getNumericCellValue());
                }
                if (j < colNum - 1){
                    printWriter.printf(",");
                }
            }
            printWriter.printf("}");
            if (i <= rowNum - 1){
                printWriter.printf(",");
            }
        }
        printWriter.printf("];module.exports = %s;",table.typeName);
        printWriter.close();
    }
}
