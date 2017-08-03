package parser;

public class TokenType {
    public static final int EOF = -1;

    public static final int IDENTIFIER = 0;
    public static final int STRING_CONSTANT = 1;
    public static final int INTEGER_CONSTANT = 2;
    public static final int CHAR_CONSTANT = 47;

    public static final int CLASS = 3;
    public static final int CONSTRUCTOR = 4;
    public static final int FUNCTION = 5;
    public static final int METHOD = 6;
    public static final int FIELD = 7;
    public static final int STATIC = 8;
    public static final int VAR = 9;
    public static final int INT = 10;
    public static final int CHAR = 11;
    public static final int BOOLEAN = 12;
    public static final int VOID = 13;
    public static final int TRUE = 14;
    public static final int FALSE = 15;
    public static final int NULL = 16;
    public static final int THIS = 17;
    public static final int LET = 18;
    public static final int DO = 19;
    public static final int IF = 20;
    public static final int ELSE = 21;
    public static final int WHILE = 22;
    public static final int RETURN = 23;
    public static final int IMPORT = 48;

    public static final int LCURLY = 24; // '{'
    public static final int RCURLY = 25; // '}'
    public static final int LPAREN = 26; // '('
    public static final int RPAREN = 27; // ')'
    public static final int LBRACK = 28; // '['
    public static final int RBRACK = 29; // ']'
    public static final int DOT = 30; 
    public static final int COMMA = 31; 
    public static final int SEMI = 32; // ';'
    public static final int PLUS = 33; // 
    public static final int MINUS = 34; // 
    public static final int STAR = 35; // '*'
    public static final int DIV = 36; // '/' 
    public static final int AND = 37; // '&' '&&'
    public static final int OR = 38; // '|' '||'
    public static final int LT = 39; // '<' 
    public static final int GT = 40; // '>' 
    public static final int LE = 41; // '<=' 
    public static final int GE = 42; // '>=' 
    public static final int ASSIGN = 43 ; // '=' 
    public static final int EQ = 44; // '==' 
    public static final int NE = 45; // '!=' 
    public static final int NEG = 46; // '~' 
    

}
