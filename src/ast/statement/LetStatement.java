package ast.statement;

import ast.expression.Expression;

public class LetStatement extends Statement {
    String varName;
    Expression index; // null, if it's not an ArrayRef
    Expression value;

    public LetStatement(String varName, Expression index, Expression value) {
        this.varName = varName;
        this.index = index;
        this.value = value;
    }

    public boolean isArrayRef() {
        return index != null;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

}
