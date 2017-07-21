import java.util.Stack;

import ast.Class;
import ast.ClassVarDec;
import ast.Subroutine;

public class EvalVisitor implements Visitor {
    
    /**
     * 暂时先只有一个class, 实现class内部的函数调用, 就是说暂不支持面向对象
     */
    
    Stack<Environment> envs = new Stack<Environment>();

    @Override
    public Object visit(Class cl) {
        Environment classEnv = new BasicEnv();
        
        for(ClassVarDec classVarDec: cl.getClassVarDecs()){
            classEnv.put(classVarDec.getVarName(), classVarDec);
        }

        for(Subroutine subroutine: cl.getSubroutines()){
            classEnv.put(subroutine.getName(), subroutine);
        }
        
        envs.push(classEnv);
        
        Subroutine main = (Subroutine) classEnv.get("main");

        visit(main);

        return null;
    }

    @Override
    public Object visit(ClassVarDec classVarDec) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private Environment currentEnv(){
        return envs.peek();
    }

}
