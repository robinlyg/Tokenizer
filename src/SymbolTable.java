
import java.util.HashMap;
import java.util.List;

public class SymbolTable {
    HashMap<String, String> symbolTable = new HashMap<>();

    public SymbolTable(){
        symbolTable.put("class","keyword" );
        symbolTable.put("constructor","keyword" );
        symbolTable.put("function","keyword" );
        symbolTable.put("method","keyword" );
        symbolTable.put("field","keyword" );
        symbolTable.put("static","keyword" );
        symbolTable.put("var","keyword" );
        symbolTable.put("int","keyword" );
        symbolTable.put("char","keyword" );
        symbolTable.put("boolean","keyword" );
        symbolTable.put("void","keyword" );
        symbolTable.put("true","keyword" );
        symbolTable.put("false","keyword" );
        symbolTable.put("null","keyword" );
        symbolTable.put("this","keyword" );
        symbolTable.put("let","keyword" );
        symbolTable.put("do","keyword" );
        symbolTable.put("if","keyword" );
        symbolTable.put("else","keyword" );
        symbolTable.put("while","keyword" );
        symbolTable.put("return","keyword" );

        symbolTable.put("[","symbol" );
        symbolTable.put("]","symbol" );
        symbolTable.put("(","symbol" );
        symbolTable.put(")","symbol" );
        symbolTable.put("{","symbol" );
        symbolTable.put("}","symbol" );
        symbolTable.put(".","symbol" );
        symbolTable.put(",","symbol" );
        symbolTable.put(";","symbol" );
        symbolTable.put("+","symbol" );
        symbolTable.put("-","symbol" );
        symbolTable.put("*","symbol" );
        symbolTable.put("/","symbol" );
        symbolTable.put("&","symbol" );
        symbolTable.put("|","symbol" );
        symbolTable.put("<","symbol" );
        symbolTable.put(">","symbol" );
        symbolTable.put("=","symbol" );
        symbolTable.put("~","symbol" );
    }

    public String getValue(String s) {
        return symbolTable.get(s);
    }

}

