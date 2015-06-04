package xls.meta;

public class Main {

	public static void main(String[] args) throws Exception {
		MetaDataManager.getInstance().initArg(args);
		MetaDataManager.getInstance().load();
		MetaDataManager.getInstance().compile();
		MetaDataManager.getInstance().doAction();
	}

}
