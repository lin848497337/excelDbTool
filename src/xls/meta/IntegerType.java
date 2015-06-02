package xls.meta;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;


public class IntegerType implements Type {

	@Override
	public String name() {
		return "int";
	}

	@Override
	public void compile() {
		
	}

	@Override
	public DataValidation createDataValidation(ColMetaData col, Sheet sheet,
			int maxRowNum, int index) {
		String formula = String.format("IF(AND(INT(CELL(\"contents\"))=CELL(\"contents\"),INT(CELL(\"contents\"))>=%d, INT(CELL(\"contents\")) <= %d, COUNTIF($A$1:$A$%d, CELL(\"contents\"))<2),TRUE,FALSE)", col.range.min,col.range.max,maxRowNum);
		DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint(formula);
		CellRangeAddressList regions = new CellRangeAddressList(1, maxRowNum, index-1, index-1);
		DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, regions);
		String note = String.format("Integer(%d, %d)", col.range.min,col.range.max);
		dataValidation.setShowPromptBox(true);
		dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		dataValidation.setEmptyCellAllowed(true);
		dataValidation.setShowErrorBox(true);
		dataValidation.createPromptBox(col.name, note);
		return dataValidation;
	}

}
