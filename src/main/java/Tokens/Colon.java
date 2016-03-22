package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Colon implements Token {
    @Override
    public TokenType getType() {
        return TokenType.COLON;
    }
}
