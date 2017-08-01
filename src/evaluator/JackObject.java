package evaluator;

public class JackObject extends BasicEnv {
    
    public JackObject(){
        super();
    }

    public JackObject(Environment env) {
        super(env);
    }

    public ClassInfo getClassInfo() {
        return (ClassInfo) this.outer;
    }
    
    public String toString() {
        if(getClassInfo().getClassName().equals("String")){
            return (String) this.get("val");
        }
        return "JackObject";
    }

}
