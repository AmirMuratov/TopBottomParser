package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Comma implements Token {
    @Override
    public TokenType getType() {
        return TokenType.COMMA;
    }
}
