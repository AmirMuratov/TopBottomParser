import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by amir on 22.03.16.
 */
public class Parser {
    LexicalAnalyzer la;

    private void consume(Token t) throws ParseException {
        if (la.currentToken() != t) {
            throw new ParseException("Expected " + t.toString() + ", found "
                    + la.currentToken().toString(), la.getTokenPosition());
        }
        la.nextToken();
    }


    private Tree s() throws ParseException {
        //S -> var P
        //S          | var           | $
        Tree currentTree = new Tree("S");
        switch (la.currentToken()) {
            case VAR:
                consume(Token.VAR);
                currentTree.addChild(new Tree("var", true));
                currentTree.addChild(p());
                break;
            default:
                throw new ParseException("Expected var, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree p() throws ParseException {
        //P -> DP'
        //P          | \w            | $
        Tree currentTree = new Tree("P");
        switch (la.currentToken()) {
            case VARIABLE:
                currentTree.addChild(d());
                currentTree.addChild(p2());
                break;
            default:
                throw new ParseException("Expected variable, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree p2() throws ParseException {
        //P' -> P | eps
        //    P'         | \w, eps       | $
        Tree currentTree = new Tree("P'");
        switch (la.currentToken()) {
            case VARIABLE:
                currentTree.addChild(p());
                break;
            case END:
                break;
            default:
                throw new ParseException("Expected variable or end of string, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree d() throws ParseException {
        //D -> V: T;
        //D          | \w            | \w, $
        Tree currentTree = new Tree("D");
        switch (la.currentToken()) {
            case VARIABLE:
                currentTree.addChild(v());
                consume(Token.COLON);
                currentTree.addChild(new Tree(":", true));
                currentTree.addChild(t());
                consume(Token.SEMICOLON);
                currentTree.addChild(new Tree(";", true));
                break;
            default:
                throw new ParseException("Expected variable, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree v() throws ParseException {
        //V -> WV'
        //V          | \w            | :
        Tree currentTree = new Tree("V");
        switch (la.currentToken()) {
            case VARIABLE:
                currentTree.addChild(w());
                currentTree.addChild(v2());
                break;
            default:
                throw new ParseException("Expected variable, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree v2() throws ParseException {
        //V' -> , V  | eps
        //V'         | ',', eps       | :
        Tree currentTree = new Tree("V'");
        switch (la.currentToken()) {
            case COMMA:
                consume(Token.COMMA);
                currentTree.addChild(new Tree(",", true));
                currentTree.addChild(v());
                break;
            case COLON:
                break;
            default:
                throw new ParseException("Expected coma or colon, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree t() throws ParseException {
        //T -> Integer | Real | Boolean | Char | String | Word | Byte | Float | Extended
        //T          | integer,char..| ;
        Tree currentTree = new Tree("T");
        switch (la.currentToken()) {
            case TYPE:
                currentTree.addChild(new Tree(la.currentToken().getName(), true));
                consume(Token.TYPE);
                break;
            default:
                throw new ParseException("Expected type, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree w() throws ParseException {
        //W -> \w+
        //W          | \w            | ',', :
        Tree currentTree = new Tree("W");
        switch (la.currentToken()) {
            case VARIABLE:
                currentTree.addChild(new Tree(la.currentToken().getName(), true));
                consume(Token.VARIABLE);
                break;
            default:
                throw new ParseException("Expected variable, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }


    public Tree parse(InputStream input) throws ParseException {
        la = new LexicalAnalyzer(input);
        return s();
    }
}
