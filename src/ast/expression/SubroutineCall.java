package ast.expression;

import java.util.List;

public class SubroutineCall extends Expression {
    List<Expression> args;
    String prefixName;
    String subroutineName;

    public SubroutineCall(String prefixName, String subroutineName, List<Expression> args) {
        this.args = args;
        this.prefixName = prefixName;
        this.subroutineName = subroutineName;
    }

    public boolean prefixIsUpper() {
        return !prefixIsBlank() && prefixName.charAt(0) <= 'Z' && prefixName.charAt(0) >= 'A';
    }

    public boolean prefixIsLower() {
        return !prefixIsBlank() && prefixName.charAt(0) <= 'z' && prefixName.charAt(0) >= 'a';
    }

    public boolean prefixIsBlank() {
        return prefixName.equals("");
    }

    public List<Expression> getArgs() {
        return args;
    }

    public void setArgs(List<Expression> args) {
        this.args = args;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getSubroutineName() {
        return subroutineName;
    }

    public void setSubroutineName(String subroutineName) {
        this.subroutineName = subroutineName;
    }
}
