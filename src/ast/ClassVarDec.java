package ast;

public class ClassVarDec extends Variable {
    boolean isStatic;
    
    public ClassVarDec(boolean isStatic, Type type, String varName){
	super(type, varName);
	this.isStatic = isStatic;
    }
}
