package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Var implements Token {
    @Override
    public TokenType getType() {
        return TokenType.VAR;
    }
}
