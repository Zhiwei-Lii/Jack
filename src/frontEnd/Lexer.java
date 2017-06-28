package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import frontEnd.CharStream;

public class Lexer {
    private CharStream charStream;
    private HashSet<String> keywords;

    public Lexer(String filePath) throws IOException {
	this.charStream = new CharStream(filePath);
	initKeywords();
    }

    public Token nextToken() {
	if(isWhiteSpace()){
	    skipWhiteSpace();
	}
	
	if(isComment()){
	    skipComment();
	}
	
	

    }

    public boolean hasNext() {
	return !charStream.isEOF();
    }

    private boolean isDigit() {
	char c = charStream.current();
	return c >= '0' && c <= '9';
    }

    private boolean isIdentifier() {
	char c = charStream.current();
	return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_');
    }
    
    private boolean isWhiteSpace(){
	char c = charStream.current();
	return c==' ' || c =='\t' || c=='\n' || c=='\r';
    }
    
    private void skipWhiteSpace(){
	while(isWhiteSpace()){
	    charStream.consume();
	}
    }
    
    private void initKeywords(){
	keywords = new HashSet<String>();
	keywords.add("class");
	keywords.add("constructor");
	keywords.add("function");
	keywords.add("method");
	keywords.add("field");
	keywords.add("static");
	keywords.add("var");
	keywords.add("int");
	keywords.add("char");
	keywords.add("boolean");
	keywords.add("void");
	keywords.add("true");
	keywords.add("false");
	keywords.add("null");
	keywords.add("this");
	keywords.add("let");
	keywords.add("do");
	keywords.add("if");
	keywords.add("else");
	keywords.add("while");
	keywords.add("return");
    }

}
