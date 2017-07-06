package ast;

import java.util.List;

public class SubroutineCall {
    boolean isStatic;
    List<Expression> args;
    String className;
    String objectName;
    String subroutineName;
    
    public SubroutineCall(boolean isStatic, String name, String subroutineName, List<Expression> args){
	this.isStatic = isStatic;
	this.args = args;
	this.subroutineName = subroutineName;

	if(isStatic){
	    this.className = name;
	}
	else{
	    this.objectName = name;
	}
    }

}
