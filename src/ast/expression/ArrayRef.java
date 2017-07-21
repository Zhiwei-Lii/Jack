package ast.expression;

public class ArrayRef extends Expression {
    String arrayName;
    Expression index;
    
    public ArrayRef(String arrayName, Expression index){
        this.arrayName = arrayName;
        this.index = index;
    }

}
