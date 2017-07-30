package ast.expression;

public class ArrayRef extends Expression {
    String arrayName;
    Expression index;
    
    public ArrayRef(String arrayName, Expression index){
        this.arrayName = arrayName;
        this.index = index;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

}
