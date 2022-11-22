import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //prompt user to enter the name of a jack file (.jack)
        //program will then produce an XML output file of all the tokens n the file with the
        //same name but .xml

        String fileName;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the name of the .jack file");
        fileName = keyboard.nextLine();

        Tokenizer tk = new Tokenizer(fileName);

        while(tk.hasMoreTokens())
        {
            tk.advance();
        }

        tk.lastTag();
    }
}
