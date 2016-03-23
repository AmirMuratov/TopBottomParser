
import Tokens.TokenType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;


/**
 * Created by amir on 22.03.16.
 */
public class Main {

    public static void printTree(Tree parse) {
        System.out.println("Begining Parsing: " + parse.node);
        if (parse.children != null) {
            for (Tree t : parse.children) {
                printTree(t);
            }
        }
        System.out.println("Ending Parsing: " + parse.node);
    }

    public static void main(String[] args) {
        String exampleString = "var i, j : Integer;X:Char;";
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        try {
            Parser p = new Parser();
            printTree(p.parse(stream));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
