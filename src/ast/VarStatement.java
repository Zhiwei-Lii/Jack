package ast;

import java.util.List;

public class VarStatement extends Statement {
    Type type;
    List<String> names;
    
    public VarStatement(Type type, List<String> names){
	this.type = type;
	this.names = names;
    }

}
