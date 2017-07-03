package parser;

import ast.AST;

public class Parser {
    TokenStream tokenStream;

    public Parser(TokenStream tokenStream) {
	this.tokenStream = tokenStream;
    }

    public void parse() {
	classDec();
    }

    public AST getAST() {
	return null;
    }

    private void classDec() {
	match(TokenType.CLASS); System.out.println("<class>");
	className();
	match(TokenType.LCURLY);
	
	while(isClassVarDec()){
	    classVarDec();
	}
	
	while(isSubroutineDec()){
	    subroutineDec();
	}

	match(TokenType.RCURLY); System.out.println("</class>");
    }
    
    private void className(){
	IDENTIFIER();
    }
    
    private void IDENTIFIER() {
	if(!checkType(TokenType.IDENTIFIER)){
	    throw new Error("parse error!");
	}
	
	Token token = tokenStream.currentToken();
	System.out.println(token.image());
	tokenStream.consume();
    }

    private void classVarDec() {
	System.out.println("<static-field>");
	if(checkType(TokenType.STATIC)){
	    match(TokenType.STATIC);
	}

	if(checkType(TokenType.FIELD)){
	    match(TokenType.FIELD);
	}
	
	type();
	varName();
	
	while(checkType(TokenType.COMMA)){
	    match(TokenType.COMMA);
	    varName();
	}
	
	match(TokenType.SEMI);
	System.out.println("</static-field>");
    }
    
    private void varName() {
	IDENTIFIER();
    }

    private void type(){
	System.out.println("<type>");
	
	if(checkType(TokenType.INT)){
	    System.out.println("<int>");
	    match(TokenType.INT);
	}

	if(checkType(TokenType.CHAR)){
	    System.out.println("<char>");
	    match(TokenType.CHAR);
	}

	if(checkType(TokenType.BOOLEAN)){
	    System.out.println("<boolean>");
	    match(TokenType.BOOLEAN);
	}
	
	if(checkType(TokenType.IDENTIFIER)){
	    className();
	}

	System.out.println("</type>");
    }

    private void subroutineDec(){
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
