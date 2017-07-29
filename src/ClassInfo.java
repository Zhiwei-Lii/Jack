import ast.Class;
import ast.ClassVarDec;

public class ClassInfo extends BasicEnv {
    /*
     * 保存静态变量, 私有变量, 函数定义, 是class的一个精化(因为我不愿意动AST)
     * 继承自BasicEnv的map里放着静态变量以及函数定义
     */
    
    Class cl;
    
    public ClassInfo(Environment env, Class cl){
        super(env);
        this.cl = cl;
    }
    

}
