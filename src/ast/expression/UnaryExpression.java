package ast.expression;

public class UnaryExpression extends Expression {
    String op;
    Expression expr;
    
    public UnaryExpression(String op, Expression expr){
        this.op = op;
        this.expr = expr;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

}
