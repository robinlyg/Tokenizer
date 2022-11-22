import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ignores all comments and white space in the input stream and serializes it into jack language tokens
 */

public class Tokenizer {
    public static final String endKeyword = " </keyword>";
    public static final String endSymbol = " </symbol>";
    public static final String beginIntConst = "<integerConstant> ";
    public static final String endIntConst = " </integerConstant>";
    public static final String beginStringConst = "<stringConstant> ";
    public static final String endStringConst = " </stringConstant>";
    public static final String endIdConst = " </identifier>";
    public static final String beginIdConst = "<identifier> ";
    public static final String beginKeyword = "<keyword> ";
    public static final String beginSymbol = "<symbol> ";
    public final String KEYWORD = "keyword";
    public final String IDENTIFIER = "identifier";
    public final String INT_CONST = "integerConstant";
    public final String STRING_CONST = "stringConsstant";
    public final String SYMBOL = "symbol";
    public static final String VALID_SYMBOLS = "{}|;,.+-*/&()[]><=~";
    SymbolTable sb = new SymbolTable();
    protected Scanner fileScanner;
    protected String currentLine;
    private String intVal;
    private String keyword;
    private String stringVal;
    private String symbol;
    private String tokenType;
    private String identifier;
    private ArrayList<String> tokens = new ArrayList<>();
    protected static PrintWriter fileWriter;
    private String xmlFileName;

    /**
     * opens the input .jack file and gets ready to tokenize it.
     */
    public Tokenizer(String inFileName){
        try {
            fileScanner = new Scanner(new File(inFileName));
            xmlFileName = inFileName.replace(".jack", ".xml");

            fileWriter = new PrintWriter(xmlFileName);
            fileWriter.println("<tokens>");
        }catch(FileNotFoundException e){
            System.out.println("Error Loading " +e.getMessage());
            System.exit(0);
        }
    }

    /**
     * are there more tokens in the input
     * @return
     */
    public boolean hasMoreTokens(){
        if(!(fileScanner.hasNext()))
        {
            fileScanner.close();
            return false;
        }

        return true;
    }

    /**
     * gets the next token from the input, and makes it the current token
     * this method should be called only if hasMoreTokens is true
     * initially there is no current token
     */

    public void advance()
    {
        currentLine = fileScanner.nextLine();

        cleanLine();
        //now my currentTokens line is trimmed and free of comments
        parse();
        tokenize();

    }

   private void parse()
   {
       String token = "";
       tokens.clear();
       //current line already cleaned and advanced

       for (int i = 0; i < currentLine.length(); i++) {

           char c = currentLine.charAt(i);

           if (c == ' ') {
               if (!token.isEmpty()) {
                   tokens.add(token);
                   token = "";
               }
           } else if (VALID_SYMBOLS.contains(String.valueOf(c))) {
               if (!token.isEmpty()) {
                   tokens.add(token);
               }

               tokens.add(String.valueOf(c));
               token = "";

           } else token += c;

       }
   }

   private void printToFile(){

       switch (tokenType) {
           case INT_CONST -> {
               fileWriter.println(beginIntConst + intVal + endIntConst);
               System.out.println(beginIntConst + intVal + endIntConst);
           }
           case STRING_CONST -> {
               fileWriter.println(beginStringConst + stringVal + endStringConst);
               System.out.println(beginStringConst + stringVal + endStringConst);
           }
           case KEYWORD -> {
               fileWriter.println((beginKeyword + keyword + endKeyword));
               System.out.println((beginKeyword + keyword + endKeyword));
           }
           case SYMBOL -> {
               fileWriter.println(beginSymbol + symbol + endSymbol);
               System.out.println(beginSymbol + symbol + endSymbol);
           }
           case IDENTIFIER -> {
               fileWriter.println(beginIdConst + identifier + endIdConst);
               System.out.println(beginIdConst + identifier + endIdConst);
           }
       }

        fileWriter.flush();

   }

   private void tokenize(){
       for (String t : tokens) {
           if (t.matches("\\d+")) {
               //Int const
               tokenType = INT_CONST;
               intVal = t;
           } else if (t.startsWith("\"")) {
               //string const
               tokenType = STRING_CONST;
               stringVal = t;
           } else if (sb.getValue(t) != null && sb.getValue(t).equals("keyword")) {
               tokenType = KEYWORD;
               keyword = t;
           } else if (sb.getValue(t) != null && sb.getValue(t).equals("symbol")) {
               //symbol
               tokenType = SYMBOL;
               symbol = t;
               switch (symbol) {
                   case "&" -> symbol = "&amp;";
                   case "<" -> symbol = "&lt;";
                   case ">" -> symbol = "&gt;";
                   case "\" " -> symbol = "&quot;";
               }
           } else if (!(t.startsWith("//")) || !(t.startsWith("/*"))) {//else if !start wtih // or /*
               if (!(t.startsWith("\\d"))) {
                   //cannot start with a digit and can only contain digits, letters and _ after the first char.
                   if (!(t.contains("\\w"))) {
                       //identifier
                       tokenType = IDENTIFIER;
                       identifier = t;
                   }

               }
           }
        //call printmethod to add this token to the xml doc
           printToFile();
       }
   }
   private void cleanLine(){
       String newLine=currentLine.trim();
       int i = newLine.indexOf("/*");
       if(i != -1)
       {
           currentLine= newLine.substring(0,i);
           return;
       }
       i = currentLine.indexOf("*");
       if(i != -1) {
           currentLine = "";
           return;
       }

       i = currentLine.indexOf("*/");
       if(i != -1){
           currentLine= newLine.substring(0,i);
           return;
       }
       i= newLine.indexOf("//");
       if(i != -1){
           currentLine= newLine.substring(0,i);

       }

   }

   public void lastTag(){
       fileWriter.println("</tokens>");
       fileWriter.flush();
       fileWriter.close();
       fileScanner.close();
   }


}
