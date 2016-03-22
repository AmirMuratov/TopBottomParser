package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public class Type implements Token {

    private String name;

    public Type(String name) {
        this.name = name;
    }

    @Override
    public TokenType getType() {
        return TokenType.TYPE;
    }

    @Override
    public String getName() {
        return name;
    }
}
