package ast.expression;

public class CharLiteral extends Literal {
    char val;
    
    public CharLiteral(char val){
        this.val = val;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }

}
