package ast.expression;

public class VarName extends Expression {
    String varName;
    
    public VarName(String varName){
	this.varName = varName;
    }

}
