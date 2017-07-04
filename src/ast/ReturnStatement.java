package ast;

public class ReturnStatement extends Statement {
    Expression expr;
    
    public ReturnStatement(){
	
    }

    public ReturnStatement(Expression expr){
	this.expr = expr;
    }
    

}
