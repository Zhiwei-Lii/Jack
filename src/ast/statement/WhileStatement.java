package ast.statement;

import java.util.List;

import ast.expression.Expression;

public class WhileStatement extends Statement {
    Expression condition;
    List<Statement> stmts;

    public WhileStatement(Expression condition, List<Statement> stmts) {
        this.condition = condition;
        this.stmts = stmts;
    }

    public List<Statement> getStmts() {
        return stmts;
    }

    public void setStmts(List<Statement> stmts) {
        this.stmts = stmts;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

}
