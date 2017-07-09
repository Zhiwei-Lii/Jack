package ast.statement;

import java.util.List;

import ast.expression.Expression;

public class IfStatement extends Statement {
    Expression expr;
    List<Statement> ifStmts;
    List<Statement> elseStmts;
    
    public IfStatement(Expression expr, List<Statement> ifStmts, List<Statement> elseStmts){
	this.expr = expr;
	this.ifStmts = ifStmts;
	this.elseStmts = elseStmts;
	
    }

}
