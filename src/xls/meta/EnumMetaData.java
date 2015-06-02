package xls.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.dom4j.Element;

public class EnumMetaData implements Type ,MetaData{

	private String name;
	private String type;
	private Type typeObject;
	private String pkg;
	private List<CaseColMetaData> caseList = new ArrayList<CaseColMetaData>();
	
	@Override
	public String name() {
		return getName();
	}

	@Override
	public void compile() {
		typeObject = TypeManager.getInstance().getType(type);
		for(CaseColMetaData c : caseList){
			c.compile();
		}
	}

	@Override
	public String getName() {
		return pkg + name;
	}

	@Override
	public void parse(Element el) {
		name = el.attributeValue("name");
		type = el.attributeValue("type");
		Iterator<Element> it = el.elementIterator();
		while(it.hasNext()){
			Element e = it.next();
			CaseColMetaData caseColMetaData = new CaseColMetaData();
			caseColMetaData.parse(e);
			caseList.add(caseColMetaData);
		}
	}

	@Override
	public void setPackage(String pkg) {
		this.pkg = pkg;
	}

	@Override
	public DataValidation createDataValidation(ColMetaData col, Sheet sheet,
			int maxRowNum, int index) {
		String []strs = new String[caseList.size()];
		for(int i=0 ; i<strs.length ; i++){
			strs[i] = caseList.get(i).str;
		}
		DataValidationConstraint constraint = sheet.getDataValidationHelper().createExplicitListConstraint(strs);
		CellRangeAddressList regions = new CellRangeAddressList(1, maxRowNum, index-1, index-1);
		DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, regions);
		StringBuilder note = new StringBuilder(); 
		for(CaseColMetaData cc : caseList){
			note.append("[").append(cc.str).append("]");
		}
		dataValidation.setShowPromptBox(true);
		dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		dataValidation.setEmptyCellAllowed(true);
		dataValidation.setShowErrorBox(true);
		dataValidation.createPromptBox(col.name, note.toString());
		return dataValidation;
	}

}
