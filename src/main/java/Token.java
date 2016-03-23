/**
 * Created by amir on 22.03.16.
 */
public enum Token {
    VAR, VARIABLE, COMMA, COLON, TYPE, SEMICOLON, END;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
