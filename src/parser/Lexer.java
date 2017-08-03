package parser;

import java.util.HashMap;

import parser.CharStream;

public class Lexer {
    private CharStream charStream;
    private HashMap<String, Integer> keywords; // Keyword -> TokenType

    public Lexer(CharStream charStream) {
        this.charStream = charStream;
        initKeywords();
    }

    public boolean hasNext() {
        return charStream.hasNext();
    }

    public Token nextToken() {
        while (isComment() || isWhiteSpace()) {
            if (isComment()) {
                skipComment();
            }
            else if (isWhiteSpace()) {
                skipWhiteSpace();
            }
        }

        int lineNo = charStream.getLineNo();

        if (isLetter() || charStream.current() == '_') {
            String image = identifier();

            if (isKeyword(image)) {
                return new Token(image, keywordType(image), lineNo);
            }
            else {
                return new Token(image, TokenType.IDENTIFIER, lineNo);
            }
        }
        else if (isDigit()) {
            String image = integerConstant();
            return new Token(image, TokenType.INTEGER_CONSTANT, lineNo);

        }
        else if (isString()) {
            String image = stringConstant();
            return new Token(image, TokenType.STRING_CONSTANT, lineNo);
        }
        else if(charStream.current()=='\''){
            String image = charConstant();
            return new Token(image, TokenType.CHAR_CONSTANT, lineNo);
        }
        else if (charStream.isEOF()) {
            charStream.consume();
            return new Token("EOF", TokenType.EOF, lineNo);
        }
        else if (charStream.current() == '(') {
            charStream.consume();
            return new Token("(", TokenType.LPAREN, lineNo);
        }
        else if (charStream.current() == ')') {
            charStream.consume();
            return new Token(")", TokenType.RPAREN, lineNo);
        }
        else if (charStream.current() == '{') {
            charStream.consume();
            return new Token("{", TokenType.LCURLY, lineNo);
        }
        else if (charStream.current() == '}') {
            charStream.consume();
            return new Token("}", TokenType.RCURLY, lineNo);
        }
        else if (charStream.current() == '[') {
            charStream.consume();
            return new Token("[", TokenType.LBRACK, lineNo);
        }
        else if (charStream.current() == ']') {
            charStream.consume();
            return new Token("]", TokenType.RBRACK, lineNo);
        }
        else if (charStream.current() == '.') {
            charStream.consume();
            return new Token(".", TokenType.DOT, lineNo);
        }
        else if (charStream.current() == ',') {
            charStream.consume();
            return new Token(",", TokenType.COMMA, lineNo);
        }
        else if (charStream.current() == ';') {
            charStream.consume();
            return new Token(";", TokenType.SEMI, lineNo);
        }
        else if (charStream.current() == '+') {
            charStream.consume();
            return new Token("+", TokenType.PLUS, lineNo);
        }
        else if (charStream.current() == '-') {
            charStream.consume();
            return new Token("-", TokenType.MINUS, lineNo);
        }
        else if (charStream.current() == '*') {
            charStream.consume();
            return new Token("*", TokenType.STAR, lineNo);
        }
        else if (charStream.current() == '/') {
            charStream.consume();
            return new Token("/", TokenType.DIV, lineNo);
        }
        else if (charStream.current() == '&' && charStream.lookAhead(1) == '&') {
            charStream.consume();
            charStream.consume();
            return new Token("&", TokenType.AND, lineNo);
        }
        else if (charStream.current() == '&') {
            charStream.consume();
            return new Token("&", TokenType.AND, lineNo);
        }
        else if (charStream.current() == '|' && charStream.lookAhead(1) == '|') {
            charStream.consume();
            charStream.consume();
            return new Token("|", TokenType.OR, lineNo);
        }
        else if (charStream.current() == '|') {
            charStream.consume();
            return new Token("|", TokenType.OR, lineNo);
        }
        else if (charStream.current() == '<') {
            charStream.consume();
            return new Token("<", TokenType.LT, lineNo);
        }
        else if (charStream.current() == '<' && charStream.lookAhead(1) == '=') {
            charStream.consume();
            charStream.consume();
            return new Token("<=", TokenType.LE, lineNo);
        }
        else if (charStream.current() == '>') {
            charStream.consume();
            return new Token(">", TokenType.GT, lineNo);
        }
        else if (charStream.current() == '>' && charStream.lookAhead(1) == '=') {
            charStream.consume();
            charStream.consume();
            return new Token(">=", TokenType.GE, lineNo);
        }
        else if (charStream.current() == '=' && charStream.lookAhead(1) == '=') {
            charStream.consume();
            charStream.consume();
            return new Token("==", TokenType.EQ, lineNo);
        }
        else if (charStream.current() == '=') {
            charStream.consume();
            return new Token("=", TokenType.ASSIGN, lineNo);
        }
        else if (charStream.current() == '!' && charStream.lookAhead(1) == '=') {
            charStream.consume();
            charStream.consume();
            return new Token("!=", TokenType.NE, lineNo);
        }
        else if (charStream.current() == '~') {
            charStream.consume();
            return new Token("~", TokenType.NEG, lineNo);
        }
        else {
            throw new Error("illegal token at line " + lineNo);
        }
    }

    private String charConstant() {
        charStream.consume();
        String image = charStream.current()+"";
        charStream.consume();
        charStream.consume();
        return image;
    }

    private String identifier() {
        String image = "";

        if (isLetter()) {
            image += charStream.current();
            charStream.consume();
        }

        while (isLetter() || isDigit()) {
            image += charStream.current();
            charStream.consume();
        }

        return image;
    }

    private void initKeywords() {
        keywords = new HashMap<String, Integer>();
        keywords.put("class", TokenType.CLASS);
        keywords.put("constructor", TokenType.CONSTRUCTOR);
        keywords.put("function", TokenType.FUNCTION);
        keywords.put("method", TokenType.METHOD);
        keywords.put("field", TokenType.FIELD);
        keywords.put("static", TokenType.STATIC);
        keywords.put("var", TokenType.VAR);
        keywords.put("int", TokenType.INT);
        keywords.put("char", TokenType.CHAR);
        keywords.put("boolean", TokenType.BOOLEAN);
        keywords.put("void", TokenType.VOID);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("null", TokenType.NULL);
        keywords.put("this", TokenType.THIS);
        keywords.put("let", TokenType.LET);
        keywords.put("do", TokenType.DO);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("import", TokenType.IMPORT);
    }

    private String integerConstant() {
        String image = "";
        while (isDigit()) {
            image += charStream.current();
            charStream.consume();
        }
        return image;
    }

    private boolean isComment() {
        return charStream.current() == '/' && charStream.lookAhead(1) == '/';
    }

    private boolean isDigit() {
        char c = charStream.current();
        return c >= '0' && c <= '9';
    }

    private boolean isLetter() {
        char c = charStream.current();
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private boolean isKeyword(String image) {
        return keywords.containsKey(image);
    }

    private boolean isString() {
        return charStream.current() == '"';
    }

    private boolean isWhiteSpace() {
        char c = charStream.current();
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private int keywordType(String image) {
        return keywords.get(image);
    }

    private void skipComment() {
        while (charStream.current() != '\n' && charStream.current() != '\r') {
            charStream.consume();
        }

        while (charStream.current() == '\n' || charStream.current() == '\r') {
            charStream.consume();
        }
    }

    private void skipWhiteSpace() {
        while (isWhiteSpace()) {
            charStream.consume();
        }
    }

    private String stringConstant() { // 不支持转义双引号
        String image = "";

        charStream.consume(); // consume第一个引号
        while (charStream.current() != '"') {
            image += charStream.current();
            charStream.consume();
        }
        charStream.consume();

        return image;
    }

}
