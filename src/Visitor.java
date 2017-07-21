import ast.ClassVarDec;
import ast.Class;

public interface Visitor<T> {
    
    public T visit(Class cl);
    
    public T visit(ClassVarDec classVarDec);

}
