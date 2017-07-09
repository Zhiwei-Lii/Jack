package ast.expression;

public class BinaryExpression extends Expression {
    Expression left;
    String op;
    Expression right;
    
    public BinaryExpression(Expression left, String op, Expression right){
	this.left = left;
	this.op = op;
	this.right = right;
    }

}
