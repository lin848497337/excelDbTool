package xls.meta;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;

public interface Type {
	public String name();
	public void compile();
	public DataValidation createDataValidation(ColMetaData col, Sheet sheet, int maxRowNum, int index);
}
