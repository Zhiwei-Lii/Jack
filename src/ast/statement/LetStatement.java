package ast.statement;

import ast.expression.Expression;

public class LetStatement extends Statement {
    String varName;
    Expression expr;
    
    public LetStatement(String varName, Expression expr){
	this.varName = varName;
	this.expr = expr;
    }

}
