package ast;

public class LetStatement extends Statement {
    String varName;
    Expression expr;
    
    public LetStatement(String varName, Expression expr){
	this.varName = varName;
	this.expr = expr;
    }

}
