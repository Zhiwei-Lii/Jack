package ast.expression;

public class StringLiteral extends Literal {
    String val;

    public StringLiteral(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
    
    public String toString(){
        return val;
    }

}
