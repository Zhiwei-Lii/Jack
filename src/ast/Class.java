package ast;

import java.util.List;

public class Class extends Node {
    String className;
    List<ClassVarDec> staticVars;
    List<ClassVarDec> fieldVars;
    List<Subroutine> subroutines;

    public Class(String className, List<ClassVarDec> staticVars, List<ClassVarDec> fieldVars, List<Subroutine> subroutines) {
        this.className = className;
        this.staticVars = staticVars;
        this.fieldVars = fieldVars;
        this.subroutines = subroutines;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Subroutine> getSubroutines() {
        return subroutines;
    }

    public void setSubroutines(List<Subroutine> subroutines) {
        this.subroutines = subroutines;
    }

    public List<ClassVarDec> getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(List<ClassVarDec> staticVars) {
        this.staticVars = staticVars;
    }

    public List<ClassVarDec> getFieldVars() {
        return fieldVars;
    }

    public void setFieldVars(List<ClassVarDec> fieldVars) {
        this.fieldVars = fieldVars;
    }

}
