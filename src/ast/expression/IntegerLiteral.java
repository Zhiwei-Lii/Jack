package ast.expression;

public class IntegerLiteral extends Literal {
    int val;

    public IntegerLiteral(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String toString() {
        return val + "";
    }

}
