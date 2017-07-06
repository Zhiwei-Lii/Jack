package parser;

import java.util.ArrayList;
import java.util.List;

import ast.BinaryExpression;
import ast.Class;
import ast.ClassVarDec;
import ast.Expression;
import ast.IfStatement;
import ast.IntegerLiteral;
import ast.Parameter;
import ast.ReturnStatement;
import ast.Statement;
import ast.StringLiteral;
import ast.Subroutine;
import ast.Type;
import ast.VarStatement;
import ast.WhileStatement;
import ast.LetStatement;

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
	
	return new Class(className, classVars, subroutines);
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

	if(checkType(TokenType.VOID)){
	    System.out.println("<VOID>");
	    match(TokenType.VOID);
	    return new Type("void");
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
	
	String kind;
	Type type;
	String name;
	List<Parameter> paras;
	List<Statement> body;
	
	kind = tokenStream.currentToken().image();
	tokenStream.consume();
	
	type = type();
	
	name = subroutineName();
	match(TokenType.LPAREN);
	
	paras = parameterList();

	match(TokenType.RPAREN);
	
	body = subroutineBody();
	
	System.out.println("</subroutine>");
    }
    
    private List<Statement> subroutineBody() {
	List<Statement> body;
	System.out.println("<subroutineBody>");
	match(TokenType.LCURLY);
	
	body = statements();
	
	match(TokenType.RCURLY);
	System.out.println("</subroutineBody>");
	
	return body;
    }

    private List<Statement> statements() {
	List<Statement> body = new ArrayList<Statement>();
	System.out.println("<statements>");
	while(isStatement()){
	    body.add(statement());
	}
	System.out.println("</statements>");
	return body;
    }

    private Statement statement() {
	if(checkType(TokenType.LET)){
	    return letStatement();
	}
	else if(checkType(TokenType.IF)){
	    return ifStatement();
	}
	else if(checkType(TokenType.WHILE)){
	    return whileStatement();
	}
	else if(checkType(TokenType.DO)){
	    return doStatement();
	}
	else if(checkType(TokenType.RETURN)){
	    return returnStatement();
	}
	else if(checkType(TokenType.VAR)){
	    return varStatement();
	}
	else{
	    throw new Error("...");
	}
    }

    private boolean isStatement() {
	return checkType(TokenType.LET) || checkType(TokenType.IF) || checkType(TokenType.WHILE) || checkType(TokenType.DO) || checkType(TokenType.RETURN) || checkType(TokenType.VAR);
    }
    
    private Statement ifStatement(){
	System.out.println("<if-statement>");
	Expression expr;
	List<Statement> ifStmts;
	List<Statement> elseStmts;
	
	match(TokenType.IF);

	match(TokenType.LPAREN);
	expr = expression();
	match(TokenType.RPAREN);
	
	match(TokenType.LCURLY);
	ifStmts = statements();
	match(TokenType.RCURLY);
	
	if(checkType(TokenType.ELSE)){
	    match(TokenType.ELSE);

	    match(TokenType.LCURLY);
	    elseStmts = statements();
	    match(TokenType.RCURLY);
	}
	
	System.out.println("</if-statement>");
	
	return new IfStatement(expr, ifStmts, elseStmts);
    }
    
    private Statement whileStatement(){
	System.out.println("<while-statement>");
	Expression expr;
	List<Statement> stmts;

	match(TokenType.WHILE);
	
	match(TokenType.LPAREN);
	expr = expression();
	match(TokenType.RPAREN);
	
	match(TokenType.LCURLY);
	stmts = statements();
	match(TokenType.RCURLY);

	System.out.println("</while-statement>");
	
	return new WhileStatement(expr, stmts);
    }
    
    private Statement doStatement(){
	System.out.println("<do-statement>");
	SubroutineCall subroutineCall;

	match(TokenType.DO);
	subroutineCall = subroutineCall();
	match(TokenType.SEMI);

	System.out.println("</do-statement>");
	
	return new DoStatement(subroutineCall);
    }
    
    private Statement returnStatement(){
	System.out.println("<return-statement>");
	match(TokenType.RETURN);

	ReturnStatement returnStmt;
	
	if(isExpression()){
            Expression expr = expression();
            returnStmt = new ReturnStatement(expr);
	}
	else{
	    returnStmt = new ReturnStatement();
	}
	
	match(TokenType.SEMI);
	System.out.println("</return-statement>");

	return returnStmt;
    }
    
    private Statement letStatement() {
	System.out.println("<let-Statement>");
	String varName;
	Expression expr;
	
	match(TokenType.LET);
	varName = IDENTIFIER();
	match(TokenType.ASSIGN);
	expr = expression();
	match(TokenType.SEMI);
	
	System.out.println("</let-Statement>");
	
	return new LetStatement(varName, expr);
    }

    private Statement varStatement() {
	System.out.println("<var-Statement>");
	Type type;
	String name;
	List<String> names = new ArrayList<String>();
	
	match(TokenType.VAR);
	type = type();
	name = varName();
	names.add(name);

	while(checkType(TokenType.COMMA)){
	    match(TokenType.COMMA);
	    name = varName();
	    names.add(name);
	}
	
	match(TokenType.SEMI);
	System.out.println("</var-Statement>");
	
	return new VarStatement(type, names);
    }

    private List<Parameter> parameterList() {
	System.out.println("<parameterList>");
	List<Parameter> paras = new ArrayList<Parameter>();

	if(isType()){
	    Type type = type();
	    String name = varName();
	    paras.add(new Parameter(type, name));

            while(checkType(TokenType.COMMA)){
        	match(TokenType.COMMA);
        	type = type();
        	name = varName();
        	paras.add(new Parameter(type, name));
            }
	}
	System.out.println("</parameterList>");
	return paras;
    }

    private String subroutineName() {
	System.out.println("<subroutineName>");
	return IDENTIFIER();
    }

    private boolean isClassVarDec(){
	return checkType(TokenType.STATIC) || checkType(TokenType.FIELD);
    }
    
    private boolean isSubroutineDec(){
	return checkType(TokenType.CONSTRUCTOR) || checkType(TokenType.FUNCTION) || checkType(TokenType.METHOD);
    }
    
    private boolean isType(){
	return checkType(TokenType.INT) || checkType(TokenType.CHAR) || checkType(TokenType.BOOLEAN) || checkType(TokenType.IDENTIFIER) || checkType(TokenType.VOID);
    }
    
    private boolean isOp(){
	return checkType(TokenType.PLUS) || checkType(TokenType.MINUS) || checkType(TokenType.STAR) || checkType(TokenType.DIV) || checkType(TokenType.AND) || checkType(TokenType.OR) 
		|| checkType(TokenType.LT) || checkType(TokenType.GT) || checkType(TokenType.ASSIGN) || checkType(TokenType.NE) || checkType(TokenType.LE) || checkType(TokenType.GE)
		|| checkType(TokenType.EQ) || checkType(TokenType.NEG);
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
    
    private Expression expression(){
	System.out.println("<expression>");
	Expression left;
	String op;
	Expression right;
	
	left = term();
	
        System.out.println("</expression>");
	if(isOp()){
	    op = op();
	    right = term();
	    return new BinaryExpression(left, op, right);
	}
	else{
	    return left;
	}
    }
    
    private Expression term(){
	if(checkType(TokenType.INTEGER_CONSTANT)){
	    int val = integerConstant();
	    return new IntegerLiteral(val);
	}
	else if(checkType(TokenType.STRING_CONSTANT)){
	    String val = stringConstant();
	    return new StringLiteral(val);
	}
	else if(isKeywordConstant()){
	    return keywordConsant();
	}
	else if(checkType(TokenType.IDENTIFIER)){ 
	    

	    
	}
	
    }
    
    private boolean isKeywordConstant(){
	return checkType(TokenType.TRUE) || checkType(TokenType.FALSE) || checkType(TokenType.NULL) || checkType(TokenType.THIS);
    }
    
    private int integerConstant(){
	String image = tokenStream.currentToken().image();
	tokenStream.consume();
	return Integer.parseInt(image);
    }
    
    private String stringConstant(){
	String image = tokenStream.currentToken().image();
	tokenStream.consume();
	return image;
    }
    
    private String op() {
	if(!isOp()){
	    throw new Error("Parser:: op");
	}
	
	String op = tokenStream.currentToken().image();
	tokenStream.consume();
	return op;
    }

}
