import org.junit.Test;

import java.text.ParseException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by amir on 23.03.16.
 */
public class TestJunit {
    Parser parser = new Parser();
    Random r = new Random(23);

    /*
    Тест на правило P -> D
    */
    @Test
    public void test1() {
        String test = "var i: integer;";
        assertOK(test);
    }

    /*
    Тест на правило P -> DP
    */
    @Test
    public void test2() {
        String test = "var i: integer;j:integer;were:char;aaa:extended;we:string;";
        assertOK(test);
    }

    /*
    Тест на правило V -> Term
    */
    @Test
    public void test3() {
        String test = "var T : myarray;";
        assertOK(test);
    }

    /*
    Тест на правило V -> Term, V
    */
    @Test
    public void test4() {
        String test = "var T, A, B, C, D, E, K, L  : myarray;";
        assertOK(test);
    }

    /*
    Тест из условия
     */
    @Test
    public void test5() {
        String test = "var a, b: integer; c: integer;";
        assertOK(test);
    }

    /*
    Тест на большое кол-во пробелов/табов/переводов строк
     */
    @Test
    public void test6() {
        String test = "\t var a \n , b\t \n \t  : \n     integer  \t \n;\n\t\n\t\n \t  c   : integer    ; \t\n   ";
        assertOK(test);
    }

    /*
    Проверка на регистр
     */
    @Test
    public void test7() {
        String test = "VAR I, ASDASD:INTEGER;ASDASDASDASD,ASDASDASD,aSDA,ASDd,dADdDd:CHAAAAR;";
        assertOK(test);
    }

    /*
    Тесты на неожиданный символ(Проверка Lexical Parser)
     */
    @Test
    public void test8() {
        String[] test = {
                "*var i, j: integer; x, y:string;",
                "var i,*j: integer; x, y:string;",
                "var i, j:/integer; x, y:string;",
                "var i, j: integer;$x, y:string;",
                "var i, j: integer; x$, y$:string;",
                "var i, j: integer; x, y:$string;%",
        };
        for (String s : test) assertParseException(s);
    }

    /*
    Тесты на неожиданный терминал(Проверка Parser)
     */
    @Test
    public void test9() {
        String[] test = {
                ",var i, j: integer; x, y:string;",
                "var ,i, j: integer; x, y:string;",
                "var i j: integer x, y:string;",
                "var i, j,: integer; x, y:string;",
                "var i, j: integer;; x, y:string;",
                "var i, j: integer,; x, y:string;",
                "var i, j: integer: x, y:string;",
                "var i, j: integer; x, y string;",
                "var i, j: integer; x, y:string",
                "var :i, j integer; x, y:string;",
                "var i,, j integer; x, y:string;",
        };
        for (String s : test) assertParseException(s);
    }


    /*
    Набор случайных небольших тестов
     */
    @Test
    public void test10() {
        for (int i = 0; i < 10; i++) {
            StringBuilder test = new StringBuilder("var ");
            int numberOfLines = r.nextInt(5) + 1;
            for (int j = 0; j < numberOfLines; j++) {
                int numberOfVariables = r.nextInt(5);
                test.append(randomWord(r.nextInt(5) + 1));
                for (int k = 0; k < numberOfVariables; k++) {
                    test.append(",");
                    test.append(randomWord(r.nextInt(5) + 1));
                }
                test.append(":");
                test.append(randomWord(r.nextInt(5) + 1));
                test.append(";");
            }
            assertOK(test.toString());
        }
    }

    /*
    Набор случайных больших тестов
     */
    @Test
    public void test11() {
        for (int i = 0; i < 10; i++) {
            StringBuilder test = new StringBuilder("var ");
            int numberOfLines = r.nextInt(500) + 1;
            for (int j = 0; j < numberOfLines; j++) {
                int numberOfVariables = r.nextInt(500);
                test.append(randomWord(r.nextInt(20) + 5));
                for (int k = 0; k < numberOfVariables; k++) {
                    test.append(",");
                    test.append(randomWord(r.nextInt(20) + 5));
                }
                test.append(":");
                test.append(randomWord(r.nextInt(20) + 5));
                test.append(";");
            }
            assertOK(test.toString());
        }
    }

    private String randomWord(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char)(r.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    private void assertParseException(String s) {
        try {
            parser.parse(s);
        } catch (ParseException e) {
            return;
        }
        fail();
    }

    private void assertOK(String s) {
        try {
            parser.parse(s);
        } catch (ParseException e) {
            fail();
        }
    }

}
