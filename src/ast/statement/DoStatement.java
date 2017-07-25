package ast.statement;

import ast.expression.Expression;

public class DoStatement extends Statement {
    Expression subroutineCall;

    public DoStatement(Expression subroutineCall) {
        this.subroutineCall = subroutineCall;
    }

    public Expression getSubroutineCall() {
        return subroutineCall;
    }

    public void setSubroutineCall(Expression subroutineCall) {
        this.subroutineCall = subroutineCall;
    }

}
