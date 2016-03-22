package Tokens;

/**
 * Created by amir on 22.03.16.
 */
public interface Token {
    TokenType getType();
    default String getName() {
        return getType().name();
    }
}
