package xls.gen.java;

/**
 * Created by charles on 2016/12/16.
 */
public class DoubleTypeGen implements TypeGen {
    @Override
    public String getJavaType() {
        return "Double";
    }

    @Override
    public String save(String name, String element) {
        return String.format("%s.addAttribute(\"%s\",%s+\"\")", element,name,name);
    }

    @Override
    public String read(String name, String element) {
        return String.format("Double.parseDouble(%s.attributeValue(\"%s\"))",element,name);
    }
}
