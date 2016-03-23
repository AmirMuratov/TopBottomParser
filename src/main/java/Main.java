import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;


/**
 * Created by amir on 22.03.16.
 */
public class Main {

    public static void printTree(Tree parse) {
        System.out.println("Beginning Parsing: " + parse.node);
        parse.children.forEach(Main::printTree);
        System.out.println("Ending Parsing: " + parse.node);
    }

    public static void main(String[] args) {

        String exampleString = "var i, j : Integer;X:Char;";
        //String exampleString = "var i: integer;j:integer;were:char;aaa:extended;we:string;";
        //String exampleString = "VAR vv, d:Char;A:integer;";
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        try {
            Parser p = new Parser();
            new Visualizer().Visualize(p.parse(stream));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
