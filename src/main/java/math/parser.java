package math;

/**
 * Created by szhang026 on 4/4/2016.
 */
public class parser {

    static String str;
    static int pos = -1, ch;

    static void eatChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    static boolean eatChar(int _ch) {
        if (ch == _ch) {
            eatChar();
            return true;
        }
        return false;
    }

    static void eatSpace() {
        while (Character.isWhitespace(ch)) eatChar();
    }

    static double parse() {
        eatChar();
        double x = parseExpression();
        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)`
    //        | number | functionName factor | factor `^` factor

    static double parseExpression() {
        double x = parseTerm();
        for (; ; ) {
            eatSpace();
            if (eatChar('+')) x += parseTerm(); // addition
            else if (eatChar('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }

    static double parseTerm() {
        double x = parseFactor();
        for (; ; ) {
            eatSpace();
            if (eatChar('*')) x *= parseFactor(); // multiplication
            else if (eatChar('/')) x /= parseFactor(); // division
            else return x;
        }
    }

    static double parseFactor() {
        eatSpace();
        if (eatChar('+')) return parseFactor(); // unary plus
        if (eatChar('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = pos;
        if (eatChar('(')) { // parentheses
            x = parseExpression();
            eatChar(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') eatChar();
            x = Double.parseDouble(str.substring(startPos, pos));
        } else if (ch >= 'a' && ch <= 'z') { // functions
            while (ch >= 'a' && ch <= 'z') eatChar();
            String func = str.substring(startPos, pos);
            x = parseFactor();
            if (func.equals("sqrt")) x = Math.sqrt(x);
            else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
            else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
            else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) ch);
        }

        eatSpace();
        if (eatChar('^')) x = Math.pow(x, parseFactor()); // exponentiation

        return x;
    }

    public static String[] getTerms(String expression) {
    /*L0=R*5+R*0.01*P_1*5+R*0.01*(1-(P_0+P_1))*5+P_0*R*0.01*5+P_0*R*0.01*0.09*P_2*5+P_0*R*0.01*0.09*(1-(P_2))*5*/
        str=expression;
        parse();
        return null;
    }
}
