package ast.expression;

import java.util.List;

public class SubroutineCall extends Expression {
    boolean isStatic;
    List<Expression> args;
    String className;
    String objectName;
    String subroutineName;

    public SubroutineCall(boolean isStatic, String name, String subroutineName,
            List<Expression> args) {
        this.isStatic = isStatic;
        this.args = args;
        this.subroutineName = subroutineName;

        if (isStatic) {
            this.className = name;
        }
        else {
            this.objectName = name;
        }
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public List<Expression> getArgs() {
        return args;
    }

    public void setArgs(List<Expression> args) {
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSubroutineName() {
        return subroutineName;
    }

    public void setSubroutineName(String subroutineName) {
        this.subroutineName = subroutineName;
    }

}
