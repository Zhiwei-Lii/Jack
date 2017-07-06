package ast;

import java.util.List;

public class WhileStatement extends Statement {
    Expression expr;
    List<Statement> stmts;
    
    public WhileStatement(Expression expr, List<Statement> stmts){
	this.expr = expr;
	this.stmts = stmts;
    }

}
