package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Semicolon implements Token {
    @Override
    public TokenType getType() {
        return TokenType.SEMICOLON;
    }
}
