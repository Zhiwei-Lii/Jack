package ast;

import java.util.List;

public class Class extends Node {
    String className;
    List<ClassVarDec> classVarDecs;
    List<Subroutine> subroutines;

    public Class(String className, List<ClassVarDec> classVarDecs, List<Subroutine> subroutines) {
        this.className = className;
        this.classVarDecs = classVarDecs;
        this.subroutines = subroutines;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ClassVarDec> getClassVarDecs() {
        return classVarDecs;
    }

    public void setClassVarDecs(List<ClassVarDec> classVarDecs) {
        this.classVarDecs = classVarDecs;
    }

    public List<Subroutine> getSubroutines() {
        return subroutines;
    }

    public void setSubroutines(List<Subroutine> subroutines) {
        this.subroutines = subroutines;
    }
    
}
