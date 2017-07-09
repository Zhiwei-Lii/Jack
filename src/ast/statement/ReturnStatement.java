package ast.statement;

import ast.expression.Expression;

public class ReturnStatement extends Statement {
    Expression expr;
    
    public ReturnStatement(){
	
    }

    public ReturnStatement(Expression expr){
	this.expr = expr;
    }
    

}
