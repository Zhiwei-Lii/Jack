package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

import frontEnd.CharStream;

public class Lexer {
    private CharStream charStream;
    private HashMap<String, Integer> keywords; // Keyword -> TokenType

    public Lexer(String filePath) throws IOException {
	this.charStream = new CharStream(filePath);
	initKeywords();
    }

    public Token nextToken() {
	if (isWhiteSpace()) {
	    skipWhiteSpace();
	}

	if (isComment()) {
	    skipComment();
	}

	if (isIdentifier()) {
	    String image = identifier();

	    if (isKeyword(image)) {
		return new Token(image, keywordType(image));
	    } else {
		return new Token(image, TokenType.IDENTIFIER);
	    }

	} else if (isDigit()) {
	    String image = integerConstant();
	    return new Token(image, TokenType.INTEGER_CONSTANT);

	} else if (isString()) {
	    String image = stringConstant();
	    return new Token(image, TokenType.STRING_CONSTANT);

	} else if (charStream.isEOF()) {
	    return new Token("EOF", TokenType.EOF);

	} else if (charStream.current() == '(') {
	    charStream.consume();
	    return new Token("(", TokenType.LPAREN);

	} else if (charStream.current() == ')') {
	    charStream.consume();
	    return new Token(")", TokenType.RPAREN);
	    
	} else if (charStream.current() == '{') {
	    charStream.consume();
	    return new Token("{", TokenType.LCURLY);
	    
	} else if (charStream.current() == '}') {
	    charStream.consume();
	    return new Token("}", TokenType.RCURLY);
	    
	} else if (charStream.current() == '[') {
	    charStream.consume();
	    return new Token("[", TokenType.LBRACK);
	    
	} else if (charStream.current() == ']') {
	    charStream.consume();
	    return new Token("]", TokenType.RBRACK);
	    
	} else if (charStream.current() == '.') {
	    charStream.consume();
	    return new Token(".", TokenType.DOT);
	    
	} else if (charStream.current() == ',') {
	    charStream.consume();
	    return new Token(",", TokenType.COMMA);
	    
	} else if (charStream.current() == ';') {
	    charStream.consume();
	    return new Token(";", TokenType.SEMI);
	    
	} else if (charStream.current() == '+') {
	    charStream.consume();
	    return new Token("+", TokenType.PLUS);
	    
	} else if (charStream.current() == '-') {
	    charStream.consume();
	    return new Token("-", TokenType.MINUS);
	    
	} else if (charStream.current() == '*') {
	    charStream.consume();
	    return new Token("*", TokenType.STAR);
	    
	} else if (charStream.current() == '/') {
	    charStream.consume();
	    return new Token("/", TokenType.DIV);
	    
	} else if (charStream.current() == '&' && charStream.lookAhead(1) == '&') {
	    charStream.consume();
	    charStream.consume();
	    return new Token("&", TokenType.AND);
	    
	} else if (charStream.current() == '&') {
	    charStream.consume();
	    return new Token("&", TokenType.AND);
	    
	} else if (charStream.current() == '|' && charStream.lookAhead(1) == '|') {
	    charStream.consume();
	    charStream.consume();
	    return new Token("|", TokenType.OR);
	    
	} else if (charStream.current() == '<') {
	    charStream.consume();
	    return new Token("<", TokenType.LT);
	    
	} else if (charStream.current() == '<' && charStream.lookAhead(1) == '=') {
	    charStream.consume();
	    charStream.consume();
	    return new Token("<=", TokenType.LE);
	    
	} else if (charStream.current() == '>') {
	    charStream.consume();
	    return new Token(">", TokenType.GT);
	    
	} else if (charStream.current() == '>' && charStream.lookAhead(1) == '=') {
	    charStream.consume();
	    charStream.consume();
	    return new Token(">=", TokenType.GE);
	    
	} else if (charStream.current() == '=' && charStream.lookAhead(1) == '=') {
	    charStream.consume();
	    charStream.consume();
	    return new Token("==", TokenType.EQUAL);
	    
	} else if (charStream.current() == '=') {
	    charStream.consume();
	    return new Token("=", TokenType.ASSIGN);
	    
	} else if (charStream.current() == '!' && charStream.lookAhead(1) == '=') {
	    charStream.consume();
	    charStream.consume();
	    return new Token("!=", TokenType.NE);
	    
	} else if (charStream.current() == '~') {
	    charStream.consume();
	    return new Token("~", TokenType.NEG);
	}
	else{
	    System.out.println("Lexer::nextToken -> error");
	    return null;
	}
    }

    public boolean hasNext() {
	return !charStream.isEOF();
    }

    private boolean isDigit() {
	char c = charStream.current();
	return c >= '0' && c <= '9';
    }

    private String integerConstant() {
	String image = "";
	while (isDigit()) {
	    image += charStream.current();
	    charStream.consume();
	}
	return image;
    }

    private boolean isIdentifier() {
	char c = charStream.current();
	return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_');
    }

    private boolean isString() {
	return charStream.current() == '"';
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

    private boolean isKeyword(String image) {
	return keywords.containsKey(image);
    }

    private boolean isWhiteSpace() {
	char c = charStream.current();
	return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private void skipWhiteSpace() {
	while (isWhiteSpace()) {
	    charStream.consume();
	}
    }

    private boolean isComment() {
	// TODO Auto-generated method stub
	return false;
    }

    private void skipComment() {

    }

    private String identifier() {
	String image = "";

	while (isIdentifier()) {
	    image += charStream.current();
	    charStream.consume();
	}

	return image;
    }

    private int keywordType(String image) {
	return keywords.get(image);
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
    }

}
