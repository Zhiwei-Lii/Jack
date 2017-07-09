package ast.statement;

import java.util.List;

import ast.expression.Expression;

public class WhileStatement extends Statement {
    Expression expr;
    List<Statement> stmts;
    
    public WhileStatement(Expression expr, List<Statement> stmts){
	this.expr = expr;
	this.stmts = stmts;
    }

}
