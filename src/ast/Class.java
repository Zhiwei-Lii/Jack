package ast;

import java.util.List;

public class Class extends Node {
    String className;
    List<ClassVarDec> classVars;
    List<Subroutine> subroutines;
    
    public Class(String className, List<ClassVarDec> classVars, List<Subroutine> subroutines){
	this.className = className;
	this.classVars = classVars;
	this.subroutines = subroutines;
    }

}
