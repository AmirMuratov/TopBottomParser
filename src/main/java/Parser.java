import Tokens.Token;
import Tokens.TokenType;

import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by amir on 22.03.16.
 */
public class Parser {
    LexicalAnalyzer analyzer;

    private void consume(Token t) throws ParseException{
        if (analyzer.currentToken().getType() != t.getType()) {
            throw new ParseException("Expected " + t.getName() + ", found " + analyzer.currentToken().getName(), 1);
        }
        analyzer.nextToken();
    }

    private Tree s() throws ParseException {
        //S -> var P
        //var | $ |

        if (analyzer.currentToken().getType() != TokenType.VAR) {
            throw new ParseException("Var token not found", 1);
        }
        analyzer.nextToken();
        return new Tree("S", new Tree("VAR"), p());
    }
    private Tree p() throws ParseException {
        //P -> DP | D
        Tree parsedLine = d();
        if (analyzer.currentToken().getType() != TokenType.END) {
            return new Tree("P", parsedLine, p());
        } else {
            return new Tree("P", parsedLine);
        }
    }
    private Tree d() throws ParseException {
        //D -> V: T;
        Tree variables = v();
        if (analyzer.currentToken().getType() != TokenType.COLON) {
            throw new ParseException("Can't find colon after variables", 1);
        }
        analyzer.nextToken();
        Tree type = t();
        if (analyzer.currentToken().getType() != TokenType.SEMICOLON) {
            throw new ParseException("Can't find colon after type", 1);
        }
        analyzer.nextToken();
        return new Tree("D", variables, new Tree("COLON"), type, new Tree("SEMICOLON"));
    }

    private Tree v() throws ParseException {
        //V -> W, V | W
        Tree variable = w();
        if (analyzer.currentToken().getType() == TokenType.COMMA) {
            analyzer.nextToken();
            return new Tree("V", variable, new Tree("COMMA"), v());
        } else {
            return new Tree("V", variable);
        }
    }

    private Tree t() throws ParseException {
        //T -> Integer | Real | Boolean | Char | String | Word | Byte | Float | Extended
        if (analyzer.currentToken().getType() != TokenType.TYPE) {
            throw new ParseException("Can't find type", 2);
        }
        String name = analyzer.currentToken().getName();
        analyzer.nextToken();
        return new Tree("T", new Tree(name));
    }


    private Tree w() throws ParseException {
        //W -> \w+
        if (analyzer.currentToken().getType() != TokenType.VARIABLE) {
            throw new ParseException("Can't find variable name", 2);
        }
        String name = analyzer.currentToken().getName();
        analyzer.nextToken();
        return new Tree("W", new Tree(name));
    }



    public Tree parse(InputStream input) throws ParseException {
        analyzer = new LexicalAnalyzer(input);
        return s();
    }
}
