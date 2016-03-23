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
                currentTree.addChild(new Tree("var", true));
                consume(Token.VAR);
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
        //P          | **Term**            | $
        Tree currentTree = new Tree("P");
        switch (la.currentToken()) {
            case TERM:
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
        //    P'         | **Term**, eps       | $
        Tree currentTree = new Tree("P'");
        switch (la.currentToken()) {
            case TERM:
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
        //D          | **Term**            | **Term**, $
        Tree currentTree = new Tree("D");
        switch (la.currentToken()) {
            case TERM:
                currentTree.addChild(v());
                currentTree.addChild(new Tree(":", true));
                consume(Token.COLON);
                currentTree.addChild(new Tree(la.currentToken().getName(), true));
                consume(Token.TERM);
                currentTree.addChild(new Tree(";", true));
                consume(Token.SEMICOLON);
                break;
            default:
                throw new ParseException("Expected variable, found "
                        + la.currentToken().toString(), la.getTokenPosition());
        }
        return currentTree;
    }

    private Tree v() throws ParseException {
        //V -> WV'
            //V          | **Term**            | :
        Tree currentTree = new Tree("V");
        switch (la.currentToken()) {
            case TERM:
                currentTree.addChild(new Tree(la.currentToken().getName(), true));
                consume(Token.TERM);
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

    public Tree parse(InputStream input) throws ParseException {
        la = new LexicalAnalyzer(input);
        return s();
    }
}
