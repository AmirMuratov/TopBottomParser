import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by amir on 22.03.16.
 */
public class LexicalAnalyzer {

    final Set<String> types = new HashSet<>();
    private InputStream input;
    private int curPos;
    private int curChar;
    private Token curToken;
    private int tokenBeg;

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
        tokenBeg = curPos;
        Token currentToken = null;
        switch (curChar) {
            case ';':
                currentToken = Token.SEMICOLON;
                break;
            case ':':
                currentToken = Token.COLON;
                break;
            case ',':
                currentToken = Token.COMMA;
                break;
            case -1:
                currentToken = Token.END;
                break;
        }
        if (currentToken != null) {
            nextChar();
            return currentToken;
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
            Token.TYPE.setName(word);
            return Token.TYPE;
        }
        if (word.equals("var")) {
            return Token.VAR;
        }
        Token.VARIABLE.setName(word);
        return Token.VARIABLE;
    }

    public void nextToken() throws ParseException {
        curToken = getNextToken();
    }

    public Token currentToken() {
        return curToken;
    }

    public int getTokenPosition() {
        return tokenBeg;
    }
}
