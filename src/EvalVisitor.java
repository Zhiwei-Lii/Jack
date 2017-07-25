import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ast.Class;
import ast.ClassVarDec;
import ast.Parameter;
import ast.Subroutine;
import ast.expression.*;
import ast.statement.*;

public class EvalVisitor implements Visitor {

    /**
     * 暂时先只有一个class, 实现class内部的函数调用, 就是说暂不支持面向对象 要处理类型问题的话建立符号表, env是用来模拟内存的, symbolTable是纪录类型等信息的
     * 或者就建立一个类, 同时纪录value和type
     * 
     * 这里的visitor模式实现的有些问题, 我把遍历子类的工作也抽取到这个类中了, 实际不该这样的 实际上这个类改名叫evaluator比较好
     * 
     * 利用异常机制实现return语句
     */

    Stack<Environment> envs = new Stack<Environment>();

    public EvalVisitor(Class cl) {
        visit(cl);
    }

    public Object visit(Class cl) {
        Environment classEnv = new BasicEnv();

        // 这里处理的是this class的属性, 扩展成面向对象要得改
        for (ClassVarDec classVarDec : cl.getClassVarDecs()) {
            classEnv.put(classVarDec.getVarName(), classVarDec);
        }

        for (Subroutine subroutine : cl.getSubroutines()) {
            classEnv.put(subroutine.getName(), subroutine);
        }

        envs.push(classEnv);

        // do main call
        SubroutineCall mainCall = new SubroutineCall(false, "this", "main", new ArrayList<Expression>());
        Object result = visit(mainCall);
        System.out.println((Integer)result);

        return null;
    }

    public Object visit(Subroutine subroutine) {

        for (Statement s : subroutine.getBody()) {
            try{
                visit(s);
            }
            catch(ReturnException e){
                return e.getValue();
            }
        }

        return null;
    }

    public Object visit(ReturnStatement s) throws ReturnException {
        Object value = visit(s.getExpr());
        throw new ReturnException(value);
    }

    public Object visit(DoStatement s) {
        visit(s.getSubroutineCall());
        return null;
    }

    // condition恐怕要变
    public Object visit(IfStatement s) throws ReturnException {
        Boolean condition = (Boolean) visit(s.getCondition());
        if (condition.booleanValue() == true) {
            for (Statement stmt : s.getIfStmts()) {
                visit(stmt);
            }
        }
        else if(s.getElseStmts()!=null){
            for (Statement stmt : s.getElseStmts()) {
                visit(stmt);
            }
        }

        return null;
    }

    // 暂时没有处理数组引用!!!!!!!!!
    public Object visit(LetStatement s) {
        String varName = s.getVarName();
        Object value = visit(s.getValue());
        Environment env = getCurrentEnv();

        if (!env.isDefined(varName)) {
            throw new Error(varName + " has not been defined");
        }
        env.put(varName, value);

        return null;
    }

    public Object visit(VarStatement s) {
        List<String> varNames = s.getNames();
        Environment env = getCurrentEnv();

        for (String name : varNames) {
            env.put(name, null);
        }

        return null;
    }

    public Object visit(WhileStatement s) throws ReturnException {
        Expression condition = s.getCondition();
        List<Statement> stmts = s.getStmts();

        while (((Boolean) visit(condition)).booleanValue()) {
            for (Statement stmt : stmts) {
                visit(stmt);
            }
        }

        return null;
    }

    public Object visit(BinaryExpression e) {
        Expression left = e.getLeft();
        String op = e.getOp();
        Expression right = e.getRight();

        if (op.equals("+")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l + r;
        }
        else if (op.equals("-")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l - r;
        }
        else if (op.equals("*")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l * r;
        }
        else if (op.equals("/")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l / r;
        }
        else if (op.equals("&")) {
            Boolean l = (Boolean) visit(left);
            Boolean r = (Boolean) visit(right);

            return l & r;
        }
        else if (op.equals("|")) {
            Boolean l = (Boolean) visit(left);
            Boolean r = (Boolean) visit(right);

            return l | r;
        }
        else if (op.equals("<")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l < r;
        }
        else if (op.equals(">")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l > r;
        }
        else if (op.equals("=")) {
            Object r = visit(right);

            return r;
        }
        else if (op.equals("!=")) {
            Object l = visit(left);
            Object r = visit(right);

            return l != r;
        }
        else if (op.equals("<=")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l <= r;
        }
        else if (op.equals(">=")) {
            Integer l = (Integer) visit(left);
            Integer r = (Integer) visit(right);

            return l >= r;
        }
        else if (op.equals("==")) {
            Object l = visit(left);
            Object r = visit(right);

            return l == r;
        }
        else {
            throw new Error("Undefined binary operator");
        }
    }

    public Object visit(FalseLiteral e) {
        return false;
    }

    public Object visit(IntegerLiteral e) {
        return e.getVal();
    }

    public Object visit(NullLiteral e) {
        return null;
    }

    public Object visit(StringLiteral e) {
        return e.getVal();
    }

    /*
     * 待改
     */
    private Environment getClassEnv(String className) {
        return null;
    }

    /**
     * 这里还需要仔细推敲, 先处理过程式函数调用
     * 
     * @param e
     * @return
     */
    public Object visit(SubroutineCall e) {
        if (e.isStatic()) {
            Environment newEnv = new BasicEnv(getClassEnv(e.getClassName()));
            // wlejflewjklfwj

        }
        else {
            Environment newEnv = new BasicEnv(envs.firstElement()); // root env, class env

            Subroutine subroutine = (Subroutine) getCurrentEnv().get(e.getSubroutineName()); // 找不到该怎么办,
                                                                                             // 处理逻辑未添加
            pushArguments(e.getArgs(), subroutine.getParameters(), newEnv);
            pushEnv(newEnv);

            Object returnValue = visit(subroutine);

            popEnv();

            return returnValue;
        }

        return null;
    }

    private void pushArguments(List<Expression> args, List<Parameter> paras, Environment env) {
        if (args.size() != paras.size()) {
            throw new Error("Unable to call the function");
        }

        for (int i = 0; i < args.size(); i++) {
            Object value = visit(args.get(i));
            String name = paras.get(i).getVarName();
            env.put(name, value);
        }
    }

    public Object visit(ThisLiteral e) {
        return getCurrentEnv().get("this");
    }

    public Object visit(TrueLiteral e) {
        return true;
    }

    public Object visit(VarName e) {
        return getCurrentEnv().get(e.getVarName());
    }

    public Object visit(Expression e) {
        if (e instanceof ArrayRef) {
            //
            return null;
        }
        else if (e instanceof BinaryExpression) {
            return visit((BinaryExpression) e);
        }
        else if (e instanceof FalseLiteral) {
            return visit((FalseLiteral) e);
        }
        else if (e instanceof IntegerLiteral) {
            return visit((IntegerLiteral) e);
        }
        else if (e instanceof NullLiteral) {
            return visit((NullLiteral) e);
        }
        else if (e instanceof StringLiteral) {
            return visit((StringLiteral) e);
        }
        else if (e instanceof SubroutineCall) {
            return visit((SubroutineCall) e);
        }
        else if (e instanceof ThisLiteral) {
            return visit((ThisLiteral) e);
        }
        else if (e instanceof TrueLiteral) {
            return visit((TrueLiteral) e);
        }
        else if (e instanceof VarName) {
            return visit((VarName) e);
        }
        else {
            throw new Error();
        }
    }

    private Object visit(Statement s) throws ReturnException {
        if (s instanceof DoStatement) {
            return visit((DoStatement) s);
        }
        else if (s instanceof IfStatement) {
            return visit((IfStatement) s);
        }
        else if (s instanceof LetStatement) {
            return visit((LetStatement) s);
        }
        else if (s instanceof ReturnStatement) {
            return visit((ReturnStatement) s);
        }
        else if (s instanceof VarStatement) {
            return visit((VarStatement) s);
        }
        else if (s instanceof WhileStatement) {
            return visit((WhileStatement) s);
        }
        else {
            throw new Error();

        }
    }

    private void pushEnv(Environment env) {
        envs.push(env);
    }

    private void popEnv() {
        envs.pop();
    }

    private Environment getCurrentEnv() {
        return envs.peek();
    }

}
