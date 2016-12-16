package xls.meta;

/**
 * Created by charles on 2016/12/16.
 */
public enum  ActionTypeEnum {
    CREATE_EXCEL("excel"),
    GEN_CODE("code"),
    GEN_XML("xml"),
    GEN_JSON("json")
    ;

    private String name;

    ActionTypeEnum(String name) {
        this.name = name;
    }

    public String getValue(){
        return name;
    }

    public static ActionTypeEnum getValue(String name){
        for (ActionTypeEnum e: ActionTypeEnum.values()){
            if (e.getValue().equals(name)){
                return e;
            }
        }
        return null;
    }


}
