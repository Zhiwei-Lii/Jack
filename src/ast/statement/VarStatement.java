package ast.statement;

import java.util.List;

import ast.Type;

public class VarStatement extends Statement {
    Type type;
    List<String> names;

    public VarStatement(Type type, List<String> names) {
        this.type = type;
        this.names = names;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

}
