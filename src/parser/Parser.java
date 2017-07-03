package parser;

import java.util.ArrayList;
import java.util.List;

import ast.Class;
import ast.ClassVarDec;
import ast.Subroutine;
import ast.Type;

public class Parser {
    TokenStream tokenStream;

    public Parser(TokenStream tokenStream) {
	this.tokenStream = tokenStream;
    }

    public Class parse() {
	return classDec();
    }

    private Class classDec() {
	String className;
	List<ClassVarDec> classVars = new ArrayList<ClassVarDec>();
	List<Subroutine> subroutines = new ArrayList<Subroutine>();

	match(TokenType.CLASS); System.out.println("<class>");
	className = className();
	match(TokenType.LCURLY);
	
	while(isClassVarDec()){
	    classVars.addAll(classVarDec());
	}
	
	while(isSubroutineDec()){
	    subroutines.add(subroutineDec());
	}

	match(TokenType.RCURLY); System.out.println("</class>");
    }
    
    private String className(){
	return IDENTIFIER();
    }
    
    private String IDENTIFIER() {
	if(!checkType(TokenType.IDENTIFIER)){
	    throw new Error("parse error!");
	}
	
	Token token = tokenStream.currentToken();
	tokenStream.consume();
	return token.image();
    }

    private List<ClassVarDec> classVarDec() {
	List<ClassVarDec> classVarDecs = new ArrayList<ClassVarDec>();
	boolean isStatic = false;
	
	System.out.println("<static-field>");
	if(checkType(TokenType.STATIC)){
	    match(TokenType.STATIC);
	    isStatic = true;
	}

	if(checkType(TokenType.FIELD)){
	    match(TokenType.FIELD);
	    isStatic = false;
	}
	
	Type type = type();
	String varName = varName();
	classVarDecs.add(new ClassVarDec(isStatic, type, varName));
	
	while(checkType(TokenType.COMMA)){
	    match(TokenType.COMMA);
	    varName = varName();
	    classVarDecs.add(new ClassVarDec(isStatic, type, varName));
	}
	
	match(TokenType.SEMI);
	System.out.println("</static-field>");
	
	return classVarDecs;
    }
    
    private String varName() {
	return IDENTIFIER();
    }

    private Type type(){
	System.out.println("<type>");
	
	if(checkType(TokenType.INT)){
	    System.out.println("<int>");
	    match(TokenType.INT);
	    return new Type("int");
	}

	if(checkType(TokenType.CHAR)){
	    System.out.println("<char>");
	    match(TokenType.CHAR);
	    return new Type("char");
	}

	if(checkType(TokenType.BOOLEAN)){
	    System.out.println("<boolean>");
	    match(TokenType.BOOLEAN);
	    return new Type("boolean");
	}
	
	if(checkType(TokenType.IDENTIFIER)){
	    String typeName = className();
	    return new Type(typeName);
	}

	System.out.println("</type>");
	return null;
    }

    private Subroutine subroutineDec(){
	System.out.println("<subroutine>");
	
	tokenStream.consume(); // consume "constructor|method|function"
	if(checkType(TokenType.VOID)){
	    System.out.println("<void>");
	}
	else if(isType()){
	    type();
	}
	else{
	    throw new Error("error");
	}
	
	subroutineName();
	match(TokenType.LPAREN);
	
	parameterList();

	match(TokenType.RPAREN);
	
	subroutineBody();
	
	System.out.println("</subroutine>");
    }
    
    private void subroutineBody() {
	System.out.println("<subroutineBody>");
	match(TokenType.LCURLY);
	
	while(checkType(TokenType.VAR)){
            varDec();
	}
	
	statements();
	
	match(TokenType.RCURLY);
	System.out.println("</subroutineBody>");
    }

    private void statements() {
	System.out.println("<statements>");
	while(isStatement()){
	    statement();
	}
	System.out.println("</statements>");
    }

    private void statement() {
	if(checkType(TokenType.LET)){
	    letStatement();
	}
	else if(checkType(TokenType.IF)){
	    ifStatement();
	}
	else if(checkType(TokenType.WHILE)){
	    whileStatement();
	}
	else if(checkType(TokenType.DO)){
	    doStatement();
	}
	else if(checkType(TokenType.RETURN)){
	    returnStatement();
	}
	else{
	    throw new Error("...");
	}
    }

    private boolean isStatement() {
	return checkType(TokenType.LET) || checkType(TokenType.IF) || checkType(TokenType.WHILE) || checkType(TokenType.DO) || checkType(TokenType.RETURN);
    }

    private void varDec() {
	match(TokenType.VAR);
	type();
	varName();
	while(checkType(TokenType.COMMA)){
	    varName();
	}
	
	match(TokenType.SEMI);
    }

    private void parameterList() {
	System.out.println("<parameterList>");
	if(isType()){
	    type();
	    varName();
            while(checkType(TokenType.COMMA)){
        	type();
        	varName();
            }
	}
	System.out.println("</parameterList>");
    }

    private void subroutineName() {
	System.out.println("<subroutineName>");
	IDENTIFIER();
	System.out.println("</subroutineName>");
    }

    private boolean isClassVarDec(){
	return checkType(TokenType.STATIC) || checkType(TokenType.FIELD);
    }
    
    private boolean isSubroutineDec(){
	return checkType(TokenType.CONSTRUCTOR) || checkType(TokenType.FUNCTION) || checkType(TokenType.METHOD);
    }
    
    private boolean isType(){
	return checkType(TokenType.INT) || checkType(TokenType.CHAR) || checkType(TokenType.BOOLEAN) || checkType(TokenType.IDENTIFIER);
    }

    private void match(int tokenType) {
	if (!checkType(tokenType)) {
	    throw new Error(
		    "Except TokenType -> " + tokenType + "Receive TokenType ->" + tokenStream.currentToken().type());
	}

	tokenStream.consume();
    }

    private boolean checkType(int tokenType) {
	if (!tokenStream.hasNext()) {
	    throw new Error("EOF!");
	}

	return tokenStream.currentToken().type() == tokenType;
    }

}
