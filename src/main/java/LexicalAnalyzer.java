import Tokens.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by amir on 22.03.16.
 */
public class LexicalAnalyzer {

    final Set<String> types = new HashSet<>();
    final Map<Integer, Class> oneSymbolTokens = new HashMap<>();
    private InputStream input;
    private int curPos;
    private int curChar;
    private Token curToken;

    {
        types.add("Integer");
        types.add("Char");
        types.add("String");
        types.add("Float");
        types.add("Real");
        types.add("Boolean");
        types.add("Word");
        types.add("Extended");
        types.add("Byte");
        oneSymbolTokens.put((int) ',', Comma.class);
        oneSymbolTokens.put((int) ':', Colon.class);
        oneSymbolTokens.put((int) ';', Semicolon.class);
        oneSymbolTokens.put(-1, End.class);
    }


    public LexicalAnalyzer(InputStream input) throws ParseException {
        this.input = input;
        nextChar();
        curToken = getNextToken();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = input.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private Token getNextToken() throws ParseException {
        while (Character.isWhitespace(curChar)) {
            nextChar();
        }
        if (oneSymbolTokens.containsKey(curChar)) {
            try {
                return (Token) oneSymbolTokens.get(curChar).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                nextChar();
            }
        }
        if (!Character.isLetter(curChar)) {
            throw new ParseException("Illegal character", curPos);
        }
        StringBuilder sb = new StringBuilder();
        while (Character.isLetter(curChar)) {
            sb.append((char) curChar);
            nextChar();
        }
        String word = sb.toString();
        if (types.contains(word)) {
            return new Type(word);
        }
        if (word.equals("var")) {
            return new Var();
        }
        return new Variable(word);
    }

    public void nextToken() throws ParseException {
        curToken = getNextToken();
    }

    public Token currentToken() {
        return curToken;
    }
}
