package ast;

public class ClassVarDec extends Node {
    boolean isStatic;
    Type type;
    String varName;
    
    public ClassVarDec(boolean isStatic, Type type, String varName){
	this.isStatic = isStatic;
	this.type = type;
	this.varName = varName;
    }
}
