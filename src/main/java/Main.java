import Tokens.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static Tokens.TokenType.*;

/**
 * Created by amir on 22.03.16.
 */
public class Main {
    public static void main(String[] args) {
        String exampleString = "var i, j : Integer;X:Char;";
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        try {
            LexicalAnalyzer la = new LexicalAnalyzer(stream);
            Token cur = la.nextToken();
            while (cur.getType() != END) {
                System.out.println(cur.getName());
                cur = la.nextToken();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
