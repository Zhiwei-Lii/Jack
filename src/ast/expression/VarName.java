package ast.expression;

public class VarName extends Expression {
    String varName;

    public VarName(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

}
