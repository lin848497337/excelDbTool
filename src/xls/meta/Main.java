package xls.meta;

public class Main {

	public static void main(String[] args) throws Exception {
		String type = "gen";
		String exceldir = "xls";
		String metaDir = "meta/main.xml";
		String xmlDir = "xml";
		MetaDataManager.getInstance().loadMeta(metaDir);
		MetaDataManager.getInstance().compile();
		MetaDataManager.getInstance().createExcel(exceldir);
	}

}
