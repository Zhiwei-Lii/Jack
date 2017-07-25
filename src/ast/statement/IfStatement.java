package ast.statement;

import java.util.List;

import ast.expression.Expression;

public class IfStatement extends Statement {
    Expression condition;
    List<Statement> ifStmts;
    List<Statement> elseStmts;

    public IfStatement(Expression condition, List<Statement> ifStmts, List<Statement> elseStmts) {
        this.condition = condition;
        this.ifStmts = ifStmts;
        this.elseStmts = elseStmts;

    }

    public List<Statement> getIfStmts() {
        return ifStmts;
    }

    public void setIfStmts(List<Statement> ifStmts) {
        this.ifStmts = ifStmts;
    }

    public List<Statement> getElseStmts() {
        return elseStmts;
    }

    public void setElseStmts(List<Statement> elseStmts) {
        this.elseStmts = elseStmts;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

}
