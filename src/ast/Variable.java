package ast;

public class Variable {
    Type type;
    String varName;

    public Variable(Type type, String varName) {
        this.type = type;
        this.varName = varName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
    

}
