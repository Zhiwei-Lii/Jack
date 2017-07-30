package evaluator;
import ast.Class;

public class JackObject extends BasicEnv {

    public JackObject(Environment env) {
        super(env);
    }

    public ClassInfo getClassInfo() {
        return (ClassInfo) this.outer;
    }

}
