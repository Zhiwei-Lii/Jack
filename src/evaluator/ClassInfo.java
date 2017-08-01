package evaluator;
import ast.Class;
import ast.ClassVarDec;

public class ClassInfo extends BasicEnv {
    /*
     * 保存静态变量, 私有变量, 函数定义, 是class的一个精化(因为我不愿意动AST)
     * 继承自BasicEnv的map里放着静态变量以及函数定义
     */
    
    String className;
    Class cl;
    
    public ClassInfo(Environment env, String className, Class cl){
        super(env);
        this.className = className;
        this.cl = cl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }

}
