package ast;

public abstract class Variable {
    Type type;
    String varName;
    
    public Variable(Type type, String varName){
	this.type = type;
	this.varName = varName;
    }

}
