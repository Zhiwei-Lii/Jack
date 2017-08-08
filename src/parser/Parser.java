package parser;

import java.util.ArrayList;
import java.util.List;

import ast.Class;
import ast.ClassVarDec;
import ast.Subroutine;
import ast.Type;
import ast.Variable;
import ast.expression.ArrayRef;
import ast.expression.BinaryExpression;
import ast.expression.CharLiteral;
import ast.expression.Expression;
import ast.expression.FalseLiteral;
import ast.expression.IntegerLiteral;
import ast.expression.NullLiteral;
import ast.expression.StringLiteral;
import ast.expression.SubroutineCall;
import ast.expression.ThisLiteral;
import ast.expression.TrueLiteral;
import ast.expression.UnaryExpression;
import ast.expression.VarName;
import ast.statement.DoStatement;
import ast.statement.IfStatement;
import ast.statement.LetStatement;
import ast.statement.ReturnStatement;
import ast.statement.Statement;
import ast.statement.VarStatement;
import ast.statement.WhileStatement;
import ast.ImportDec;
import ast.ClassFile;

public class Parser {
    TokenStream tokenStream;

    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    public ClassFile parse() {
        List<ImportDec> importDecList = importDecList();
        List<Class> classDecList = classDecList();
        return new ClassFile(importDecList, classDecList);
    }
    
    private List<ImportDec> importDecList() {
        List<ImportDec> importDecList = new ArrayList<ImportDec>();
        while(isImportDec()){
            match(TokenType.IMPORT);
            importDecList.add(new ImportDec(filePath()));
            match(TokenType.SEMI);
        }

        return importDecList;
    }
    
    private boolean isImportDec() {
        return checkType(TokenType.IMPORT);
    }
    
    private String currentClassName() {
        return this.currentClass.getClassName();
    }
    
    private void setCurrentClass(Class cl){
        this.currentClass = cl;
    }
    
    private String filePath() {
        String filePath = "";

        while(!checkType(TokenType.SEMI)){
            filePath += tokenStream.currentToken().image();
            tokenStream.consume();
        }
        
        return filePath;
    }

    private Expression arrayRef() {
        String arrayName;
        Expression index;

        arrayName = IDENTIFIER();
        match(TokenType.LBRACK);
        index = expression();
        match(TokenType.RBRACK);

        return new ArrayRef(arrayName, index);
    }

    private boolean checkType(int tokenType) {
        if (!tokenStream.hasNext()) {
            throw new Error("EOF!");
        }

        return tokenStream.currentToken().type() == tokenType;
    }

    private Class classDec() {
        String className;
        List<ClassVarDec> staticVars = new ArrayList<ClassVarDec>();
        List<ClassVarDec> fieldVars = new ArrayList<ClassVarDec>();
        List<Subroutine> subroutines = new ArrayList<Subroutine>();

        match(TokenType.CLASS);
        className = className();
        match(TokenType.LCURLY);

        while (isClassVarDec()) {
            List<ClassVarDec> list = classVarDec();

            if (list.get(0).isStatic()) {
                staticVars.addAll(list);
            }
            else {
                fieldVars.addAll(list);
            }
        }

        while (isSubroutineDec()) {
            subroutines.add(subroutineDec());
        }

        match(TokenType.RCURLY);

        return new Class(className, staticVars, fieldVars, subroutines);
    }

    private List<Class> classDecList() {
        List<Class> list = new ArrayList<Class>();
        while (tokenStream.currentToken().type() != TokenType.EOF) {
            Class cl = classDec();
            list.add(cl);
        }

        return list;
    }

    private String className() {
        return IDENTIFIER();
    }

    private List<ClassVarDec> classVarDec() {
        List<ClassVarDec> classVarDecs = new ArrayList<ClassVarDec>();
        boolean isStatic = false;

        if (checkType(TokenType.STATIC)) {
            match(TokenType.STATIC);
            isStatic = true;
        }

        if (checkType(TokenType.FIELD)) {
            match(TokenType.FIELD);
            isStatic = false;
        }

        Type type = type();
        String varName = varName();
        classVarDecs.add(new ClassVarDec(isStatic, type, varName));

        while (checkType(TokenType.COMMA)) {
            match(TokenType.COMMA);
            varName = varName();
            classVarDecs.add(new ClassVarDec(isStatic, type, varName));
        }

        match(TokenType.SEMI);

        return classVarDecs;
    }

    private Statement doStatement() {
        Expression subroutineCall;

        match(TokenType.DO);
        subroutineCall = subroutineCall();
        match(TokenType.SEMI);

        return new DoStatement(subroutineCall);
    }

    private Expression expression() {
        Expression left;
        String op;
        Expression right;

        left = term();

        if (isBinOp()) {
            op = op();
            right = term();
            return new BinaryExpression(left, op, right);
        }
        else {
            return left;
        }
    }

    private List<Expression> expressionList() {
        List<Expression> exprList = new ArrayList<Expression>();

        if (isExpression()) {
            exprList.add(expression());

            while (checkType(TokenType.COMMA)) {
                match(TokenType.COMMA);
                exprList.add(expression());
            }
        }

        return exprList;
    }

    private String IDENTIFIER() {
        if (!checkType(TokenType.IDENTIFIER)) {
            throw new Error("syntax error at line " + tokenStream.currentToken().getLineNo());
        }

        Token token = tokenStream.currentToken();
        tokenStream.consume();
        return token.image();
    }

    private Statement ifStatement() {
        Expression expr;
        List<Statement> ifStmts;
        List<Statement> elseStmts = null;

        match(TokenType.IF);

        match(TokenType.LPAREN);
        expr = expression();
        match(TokenType.RPAREN);

        match(TokenType.LCURLY);
        ifStmts = statements();
        match(TokenType.RCURLY);

        if (checkType(TokenType.ELSE)) {
            match(TokenType.ELSE);

            match(TokenType.LCURLY);
            elseStmts = statements();
            match(TokenType.RCURLY);
        }

        return new IfStatement(expr, ifStmts, elseStmts);
    }

    private int integerConstant() {
        String image = tokenStream.currentToken().image();
        tokenStream.consume();
        return Integer.parseInt(image);
    }

    private char charConstant() {
        String image = tokenStream.currentToken().image();
        tokenStream.consume();
        return image.charAt(0);
    }

    private boolean isArrayRef() {
        int lookaheadType = tokenStream.lookahead(1).type();
        return checkType(TokenType.IDENTIFIER) && lookaheadType == TokenType.LBRACK;
    }

    private boolean isClassVarDec() {
        return checkType(TokenType.STATIC) || checkType(TokenType.FIELD);
    }

    private boolean isExpression() {
        return checkType(TokenType.INTEGER_CONSTANT) || checkType(TokenType.STRING_CONSTANT)
                || isKeywordConstant() || checkType(TokenType.IDENTIFIER)
                || checkType(TokenType.LPAREN) || isUnaryOp() || checkType(TokenType.CHAR_CONSTANT);
    }

    private boolean isKeywordConstant() {
        return checkType(TokenType.TRUE) || checkType(TokenType.FALSE) || checkType(TokenType.NULL)
                || checkType(TokenType.THIS);
    }

    private boolean isBinOp() {
        return checkType(TokenType.PLUS) || checkType(TokenType.MINUS) || checkType(TokenType.STAR)
                || checkType(TokenType.DIV) || checkType(TokenType.AND) || checkType(TokenType.OR)
                || checkType(TokenType.LT) || checkType(TokenType.GT) || checkType(TokenType.ASSIGN)
                || checkType(TokenType.NE) || checkType(TokenType.LE) || checkType(TokenType.GE)
                || checkType(TokenType.EQ) || checkType(TokenType.NEG);
    }

    private boolean isStatement() {
        return checkType(TokenType.LET) || checkType(TokenType.IF) || checkType(TokenType.WHILE)
                || checkType(TokenType.DO) || checkType(TokenType.RETURN)
                || checkType(TokenType.VAR);
    }

    private boolean isSubroutineCall() {
        int lookaheadType = tokenStream.lookahead(1).type();
        return checkType(TokenType.IDENTIFIER)
                && (lookaheadType == TokenType.LPAREN || lookaheadType == TokenType.DOT);
    }

    private boolean isSubroutineDec() {
        return checkType(TokenType.CONSTRUCTOR) || checkType(TokenType.FUNCTION)
                || checkType(TokenType.METHOD);
    }

    private boolean isType() {
        return checkType(TokenType.INT) || checkType(TokenType.CHAR) || checkType(TokenType.BOOLEAN)
                || checkType(TokenType.IDENTIFIER) || checkType(TokenType.VOID);
    }

    private boolean isVarName() {
        int lookaheadType = tokenStream.lookahead(1).type();
        return checkType(TokenType.IDENTIFIER) && lookaheadType != TokenType.LBRACK
                && lookaheadType != TokenType.LPAREN && lookaheadType != TokenType.DOT;
    }

    private Expression keywordConstant() {

        if (checkType(TokenType.TRUE)) {
            match(TokenType.TRUE);
            return new TrueLiteral();
        }
        else if (checkType(TokenType.FALSE)) {
            match(TokenType.FALSE);
            return new FalseLiteral();
        }
        else if (checkType(TokenType.NULL)) {
            match(TokenType.NULL);
            return new NullLiteral();
        }
        else if (checkType(TokenType.THIS)) {
            match(TokenType.THIS);
            return new ThisLiteral();
        }
        else {
            throw new Error("syntax error at line" + tokenStream.currentToken().getLineNo());
        }

    }

    private Statement letStatement() {
        String varName;
        Expression index = null;
        Expression value;

        match(TokenType.LET);
        varName = IDENTIFIER();

        if (checkType(TokenType.LBRACK)) {
            match(TokenType.LBRACK);
            index = expression();
            match(TokenType.RBRACK);
        }

        match(TokenType.ASSIGN);
        value = expression();
        match(TokenType.SEMI);

        return new LetStatement(varName, index, value);
    }

    private void match(int tokenType) {
        if (!checkType(tokenType)) {
            throw new Error("\n"+"syntax error at line " + tokenStream.currentToken().getLineNo() + "\n"
                    + "Except " + searchTokenName(tokenType) + "\n" + "Receive "
                    + searchTokenName(tokenStream.currentToken().type()));
        }

        tokenStream.consume();
    }

    private String searchTokenName(int tokenType) {
        try {
            java.lang.Class tokenTypeClass = java.lang.Class.forName("parser.TokenType");

            for (java.lang.reflect.Field field : tokenTypeClass.getDeclaredFields()) {
                if (field.getInt(null) == tokenType) {
                    return field.getName();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        throw new Error("unfound tokenType");
    }

    private String op() {
        if (!isBinOp() && !isUnaryOp()) {
            throw new Error("syntax error at line" + tokenStream.currentToken().getLineNo());
        }

        String op = tokenStream.currentToken().image();
        tokenStream.consume();
        return op;
    }

    private List<Variable> parameterList() {
        List<Variable> paras = new ArrayList<Variable>();

        if (isType()) {
            Type type = type();
            String name = varName();
            paras.add(new Variable(type, name));

            while (checkType(TokenType.COMMA)) {
                match(TokenType.COMMA);
                type = type();
                name = varName();
                paras.add(new Variable(type, name));
            }
        }

        return paras;
    }


    private Statement returnStatement() {
        match(TokenType.RETURN);

        ReturnStatement returnStmt;

        if (isExpression()) {
            Expression expr = expression();
            returnStmt = new ReturnStatement(expr);
        }
        else {
            returnStmt = new ReturnStatement();
        }

        match(TokenType.SEMI);

        return returnStmt;
    }

    private Statement statement() {
        if (checkType(TokenType.LET)) {
            return letStatement();
        }
        else if (checkType(TokenType.IF)) {
            return ifStatement();
        }
        else if (checkType(TokenType.WHILE)) {
            return whileStatement();
        }
        else if (checkType(TokenType.DO)) {
            return doStatement();
        }
        else if (checkType(TokenType.RETURN)) {
            return returnStatement();
        }
        else if (checkType(TokenType.VAR)) {
            return varStatement();
        }
        else {
            throw new Error("syntax error at line" + tokenStream.currentToken().getLineNo());
        }
    }

    private boolean isUnaryOp() {
        return checkType(TokenType.NEG) || checkType(TokenType.MINUS);
    }

    private List<Statement> statements() {
        List<Statement> body = new ArrayList<Statement>();

        while (isStatement()) {
            body.add(statement());
        }

        return body;
    }

    private String stringConstant() {
        String image = tokenStream.currentToken().image();
        tokenStream.consume();
        return image;
    }

    private List<Statement> subroutineBody() {
        List<Statement> body;

        match(TokenType.LCURLY);
        body = statements();
        match(TokenType.RCURLY);

        return body;
    }

    private Expression subroutineCall() {
        String prefixName = "";
        String subroutineName;
        List<Expression> args;

        int lookaheadType = tokenStream.lookahead(1).type();

        if (lookaheadType == TokenType.DOT) {
            prefixName = IDENTIFIER();
            match(TokenType.DOT);
        }

        subroutineName = IDENTIFIER();
        match(TokenType.LPAREN);
        args = expressionList();
        match(TokenType.RPAREN);

        return new SubroutineCall(prefixName, subroutineName, args);
    }

    private Subroutine subroutineDec() {
        String kind;
        Type type;
        String name;
        List<Variable> paras;
        List<Statement> body;

        kind = tokenStream.currentToken().image();
        tokenStream.consume();

        type = type();

        name = subroutineName();
        match(TokenType.LPAREN);

        paras = parameterList();

        match(TokenType.RPAREN);

        body = subroutineBody();

        return new Subroutine(kind, type, name, paras, body);
    }

    private String subroutineName() {
        return IDENTIFIER();
    }

    private Expression term() {
        if (checkType(TokenType.INTEGER_CONSTANT)) {
            int val = integerConstant();
            return new IntegerLiteral(val);
        }
        else if (checkType(TokenType.STRING_CONSTANT)) {
            String val = stringConstant();
            return new StringLiteral(val);
        }
        else if (checkType(TokenType.CHAR_CONSTANT)) {
            char val = charConstant();
            return new CharLiteral(val);
        }
        else if (isKeywordConstant()) {
            return keywordConstant();
        }
        else if (isVarName()) {
            return new VarName(IDENTIFIER());
        }
        else if (isArrayRef()) {
            return arrayRef();
        }
        else if (isSubroutineCall()) {
            return subroutineCall();
        }
        else if (checkType(TokenType.LPAREN)) {
            Expression expr;

            match(TokenType.LPAREN);
            expr = expression();
            match(TokenType.RPAREN);

            return expr;
        }
        else if (isUnaryOp()) {
            String op = op();
            return new UnaryExpression(op, term());
        }
        else {
            throw new Error("syntax error at line" + tokenStream.currentToken().getLineNo());
        }
    }

    private Type type() {

        if (checkType(TokenType.INT)) {
            match(TokenType.INT);
            return new Type("int");
        }

        if (checkType(TokenType.VOID)) {
            match(TokenType.VOID);
            return new Type("void");
        }

        if (checkType(TokenType.CHAR)) {
            match(TokenType.CHAR);
            return new Type("char");
        }

        if (checkType(TokenType.BOOLEAN)) {
            match(TokenType.BOOLEAN);
            return new Type("boolean");
        }

        if (checkType(TokenType.IDENTIFIER)) {
            String typeName = className();
            return new Type(typeName);
        }

        return null;
    }

    private String varName() {
        return IDENTIFIER();
    }

    private Statement varStatement() {
        Type type;
        String name;
        List<String> names = new ArrayList<String>();

        match(TokenType.VAR);
        type = type();
        name = varName();
        names.add(name);

        while (checkType(TokenType.COMMA)) {
            match(TokenType.COMMA);
            name = varName();
            names.add(name);
        }

        match(TokenType.SEMI);

        return new VarStatement(type, names);
    }

    private Statement whileStatement() {
        Expression expr;
        List<Statement> stmts;

        match(TokenType.WHILE);

        match(TokenType.LPAREN);
        expr = expression();
        match(TokenType.RPAREN);

        match(TokenType.LCURLY);
        stmts = statements();
        match(TokenType.RCURLY);

        return new WhileStatement(expr, stmts);
    }

}
