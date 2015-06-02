package xls.meta;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

public class StringType implements Type {

	@Override
	public String name() {
		return "string";
	}

	@Override
	public void compile() {

	}

	@Override
	public DataValidation createDataValidation(ColMetaData col, Sheet sheet,
			int maxRowNum, int index) {
		DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint("ISTEXT(CELL(\"contents\"))");
		CellRangeAddressList regions = new CellRangeAddressList(1, maxRowNum, index-1, index-1);
		DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, regions);
		String note = String.format("String");
		dataValidation.setShowPromptBox(true);
		dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		dataValidation.setEmptyCellAllowed(true);
		dataValidation.setShowErrorBox(true);
		dataValidation.createPromptBox(col.name, note);
		return dataValidation;
	}

	@Override
	public String getPrintStr() {
		return "String";
	}

	@Override
	public String save(String name, String element) {
		return String.format("%s.addAttribute(\"%s\",%s)", element,name,name);
	}

	@Override
	public String read(String name, String element) {
		return String.format("%s.attributeValue(\"%s\")", element,name);
	}

}
