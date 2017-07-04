package ast;

public class DoStatement extends Statement {
    SubroutineCall subroutineCall;
    
    public DoStatement(SubroutineCall subroutineCall){
	this.subroutineCall = subroutineCall;
    }

}
