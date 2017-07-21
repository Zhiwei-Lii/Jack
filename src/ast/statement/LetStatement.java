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

}
