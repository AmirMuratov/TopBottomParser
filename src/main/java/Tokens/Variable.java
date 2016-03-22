package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Variable implements Token {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public TokenType getType() {
        return TokenType.VARIABLE;
    }

    @Override
    public String getName() {
        return name;
    }
}
