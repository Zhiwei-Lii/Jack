package evaluator;

import java.util.ArrayList;
import java.util.List;

import ast.Class;
import ast.ClassVarDec;
import ast.Subroutine;
import ast.Variable;
import ast.expression.*;
import ast.statement.*;

public class Evaluator {

    /**
     * 用于记录参数与局部变量的环境(subroutnie) -> object字段 -> 用于纪录字段与方法的环境(ClassInfo) -> 用于纪录全局变量的环境(name ->
     * ClassInfo)
     * 
     */

    Environment topEnv;

    public Evaluator(List<Class> classList, Environment topEnv) {
        eval(classList, topEnv);
        SubroutineCall mainCall = new SubroutineCall("Main", "main", new ArrayList());
        Object returnValue = eval(mainCall, (Environment) topEnv.get("Main"));
    }

    public void eval(List<Class> classList, Environment topEnv) {
        for (Class cl : classList) {
            eval(cl, topEnv);
        }
    }

    public void eval(Class cl, Environment topEnv) {
        Environment classEnv = new ClassInfo(topEnv, cl);

        for (ClassVarDec classVarDec : cl.getStaticVars()) {
            classEnv.put(classVarDec.getVarName(), classVarDec);
        }

        for (Subroutine subroutine : cl.getSubroutines()) {
            classEnv.put(subroutine.getName(), subroutine);
        }

        classEnv.put("", classEnv); // 为了f()的调用

        topEnv.put(cl.getClassName(), classEnv);
    }

    public Object eval(Subroutine subroutine, Environment env) {

        for (Statement s : subroutine.getBody()) {
            try {
                eval(s, env);
            } catch (ReturnValue e) {
                return e.getValue();
            }
        }

        return null;
    }

    // use the java reflection
    public Object eval(NativeSubroutine subroutine, Object[] args) {
        try {
            return subroutine.getMethod().invoke(null, args);
        } catch (Exception e) {
            System.out.println(e);
            throw new Error(
                    "unable to invoke the nativeSubroutine: " + subroutine.getSubroutineName());
        }
    }

    public Object eval(ReturnStatement returnStatement, Environment env) throws ReturnValue {
        Object value = eval(returnStatement.getExpr(), env);
        throw new ReturnValue(value);
    }

    public void eval(DoStatement doStatement, Environment env) {
        eval(doStatement.getSubroutineCall(), env);
    }

    public void eval(IfStatement ifStatement, Environment env) throws ReturnValue {
        Boolean condition = (Boolean) eval(ifStatement.getCondition(), env);
        if (condition.booleanValue() == true) {
            for (Statement stmt : ifStatement.getIfStmts()) {
                eval(stmt, env);
            }
        }
        else if (ifStatement.getElseStmts() != null) {
            for (Statement stmt : ifStatement.getElseStmts()) {
                eval(stmt, env);
            }
        }
    }

    public void eval(LetStatement letStatement, Environment env) {
        String varName = letStatement.getVarName();
        Object value = eval(letStatement.getValue(), env);

        if (!env.isDefined(varName)) {
            throw new Error(varName + " has not been defined");
        }

        /* find which environment the var is defined */
        Environment definedEnv = env.returnEnv(varName);

        if (letStatement.hasArrayRef()) {
            int index = (Integer) eval(letStatement.getIndex(), env);
            JackObject array = (JackObject) definedEnv.get(varName);
            array.put(index + "", value);
        }
        else {
            definedEnv.put(varName, value);
        }
    }

    public void eval(VarStatement varStatement, Environment env) {
        List<String> varNames = varStatement.getNames();

        for (String name : varNames) {
            if (env.isDefined(name)) {
                throw new Error("The var " + name + " has already been defined");
            }
            env.put(name, null);
        }
    }

    public void eval(WhileStatement whileStatement, Environment env) throws ReturnValue {
        Expression condition = whileStatement.getCondition();
        List<Statement> stmts = whileStatement.getStmts();

        while (((Boolean) eval(condition, env)).booleanValue()) {
            for (Statement stmt : stmts) {
                eval(stmt, env);
            }
        }
    }

    public Object eval(UnaryExpression unaryExpr, Environment env) {
        String op = unaryExpr.getOp();
        if (op.equals("-")) {
            return -(Integer) eval(unaryExpr.getExpr(), env);
        }
        else if (op.equals("~")) {
            return ~(Integer) eval(unaryExpr.getExpr(), env);
        }
        else {
            throw new Error("undefined op");
        }
    }

    public Object eval(BinaryExpression binaryExpr, Environment env) {
        Expression left = binaryExpr.getLeft();
        String op = binaryExpr.getOp();
        Expression right = binaryExpr.getRight();

        if (op.equals("+")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l + r;
        }
        else if (op.equals("-")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l - r;
        }
        else if (op.equals("*")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l * r;
        }
        else if (op.equals("/")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l / r;
        }
        else if (op.equals("&")) {
            Boolean l = (Boolean) eval(left, env);
            Boolean r = (Boolean) eval(right, env);

            return l & r;
        }
        else if (op.equals("|")) {
            Boolean l = (Boolean) eval(left, env);
            Boolean r = (Boolean) eval(right, env);

            return l | r;
        }
        else if (op.equals("<")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l < r;
        }
        else if (op.equals(">")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l > r;
        }
        else if (op.equals("=")) {
            Object r = eval(right, env);

            return r;
        }
        else if (op.equals("!=")) {
            Object l = eval(left, env);
            Object r = eval(right, env);

            return l != r;
        }
        else if (op.equals("<=")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l <= r;
        }
        else if (op.equals(">=")) {
            Integer l = (Integer) eval(left, env);
            Integer r = (Integer) eval(right, env);

            return l >= r;
        }
        else if (op.equals("==")) {
            Object l = eval(left, env);
            Object r = eval(right, env);

            return l == r;
        }
        else {
            throw new Error("Undefined binary operator");
        }
    }

    public Object eval(FalseLiteral falseLiteral, Environment env) {
        return false;
    }

    public Object eval(IntegerLiteral integerLiteral, Environment env) {
        return integerLiteral.getVal();
    }

    public Object eval(NullLiteral nullLiteral, Environment env) {
        return null;
    }

    public Object eval(StringLiteral stringLiteral, Environment env) {
        return stringLiteral.getVal();
    }

    //  这里可以加入更多错误处理
    private Object findSubroutine(SubroutineCall subroutineCall, Environment env){

        if(subroutineCall.prefixIsBlank()){
            String subroutineName = subroutineCall.getSubroutineName();

            if (!(env.get(subroutineName) instanceof Subroutine)) {
                throw new Error("Unable to call the subroutine, the name may has been used");
            }

            Object subroutine = (Subroutine) env.get(subroutineName);
            return subroutine;
        }
        else if(subroutineCall.prefixIsLower()){
            JackObject jackObject = (JackObject) env.get(subroutineCall.getPrefixName());
            ClassInfo classInfo = (ClassInfo) jackObject.getClassInfo();
            Object subroutine = classInfo.get(subroutineCall.getSubroutineName());
            return subroutine;
        }
        else if(subroutineCall.prefixIsUpper()){
            ClassInfo classInfo = (ClassInfo) env.get(subroutineCall.getPrefixName());
            Object subroutine = classInfo.get(subroutineCall.getSubroutineName());
            return subroutine;
        }
        
        return null;
    }

    /**
     * 处理面向对象的调用 包括构造函数的调用 构造函数视作一般的静态函数处理, 但是要额外处理this(一般的静态调用不会有this) 放在运行时去判断是否是static 构造器很有意思,
     * 形式上是静态方法调用, 实际调用的环境是和method一样
     * 
     */
    public Object eval(SubroutineCall subroutineCall, Environment env) {
        if (isNative(subroutineCall, env)) {
            NativeSubroutine nativeSubroutine = (NativeSubroutine) findSubroutine(subroutineCall, env);

            Object[] args = new Object[subroutineCall.getArgs().size()];

            for (int i = 0; i < subroutineCall.getArgs().size(); i++) {
                Expression arg = subroutineCall.getArgs().get(i);
                args[i] = eval(arg, env);
            }

            return eval(nativeSubroutine, args);
        }

        Subroutine subroutine = (Subroutine)findSubroutine(subroutineCall, env);

        Environment localEnv = null;

        if (subroutine.isConstructor()) {
            ClassInfo classInfo = (ClassInfo) env.get(subroutineCall.getPrefixName());

            if (!(classInfo.get(subroutineCall.getSubroutineName()) instanceof Subroutine)) {
                throw new Error(
                        "unable to find the subroutine " + subroutineCall.getSubroutineName());
            }

            Environment jackObject = newObject(classInfo);
            localEnv = new BasicEnv(jackObject);
        }
        else if (subroutine.isStatic()) {
            if (!(env.get(subroutineCall.getPrefixName()) instanceof ClassInfo)) {
                throw new Error("unable to find the class " + subroutineCall.getPrefixName());
            }

            localEnv = new BasicEnv(env);
        }
        else if (subroutine.isMethod()) {
            if (subroutineCall.prefixIsBlank()) {
                subroutineCall.setPrefixName("this");
            }

            if (!(env.get(subroutineCall.getPrefixName()) instanceof JackObject)) {
                throw new Error("unable to find the object " + subroutineCall.getPrefixName());
            }

            JackObject jackObject = (JackObject) env.get(subroutineCall.getPrefixName());

            if (!(jackObject.get(subroutineCall.getSubroutineName()) instanceof Subroutine)) {
                throw new Error(
                        "unable to find the subroutine " + subroutineCall.getSubroutineName());
            }

            localEnv = new BasicEnv(jackObject);
        }
        else {
            throw new Error();
        }

        pushArguments(subroutineCall.getArgs(), subroutine.getParameters(), localEnv);
        Object returnValue = eval(subroutine, localEnv);
        return returnValue;
    }

    private boolean isNative(SubroutineCall subroutineCall, Environment env) {
        Object subroutine = findSubroutine(subroutineCall, env);
        return subroutine instanceof NativeSubroutine;
    }

    private void pushArguments(List<Expression> args, List<Variable> paras, Environment env) {
        if (args.size() != paras.size()) {
            throw new Error("Unable to call the function");
        }

        for (int i = 0; i < args.size(); i++) {
            Object value = eval(args.get(i), env);
            String name = paras.get(i).getVarName();
            env.put(name, value);
        }
    }

    public Object eval(ThisLiteral thisLiteral, Environment env) {
        return env.get("this");
    }

    public Object eval(TrueLiteral trueLiteral, Environment env) {
        return true;
    }

    public Object eval(VarName varName, Environment env) {
        return env.get(varName.getVarName());
    }

    public Object eval(ArrayRef arrayRef, Environment env) {
        String arrayName = arrayRef.getArrayName();
        int index = (Integer) eval(arrayRef.getIndex(), env);

        JackObject array = (JackObject) env.get(arrayName);
        return array.get(index + "");
    }
    
    public Object eval(CharLiteral charLiteral, Environment env){
        return charLiteral.getVal();
    }

    public Object eval(Expression e, Environment env) {
        if (e instanceof ArrayRef) {
            return eval((ArrayRef) e, env);
        }
        else if (e instanceof BinaryExpression) {
            return eval((BinaryExpression) e, env);
        }
        else if (e instanceof FalseLiteral) {
            return eval((FalseLiteral) e, env);
        }
        else if (e instanceof IntegerLiteral) {
            return eval((IntegerLiteral) e, env);
        }
        else if(e instanceof CharLiteral){
            return eval((CharLiteral) e, env);
        }
        else if (e instanceof NullLiteral) {
            return eval((NullLiteral) e, env);
        }
        else if (e instanceof StringLiteral) {
            return eval((StringLiteral) e, env);
        }
        else if (e instanceof SubroutineCall) {
            return eval((SubroutineCall) e, env);
        }
        else if (e instanceof ThisLiteral) {
            return eval((ThisLiteral) e, env);
        }
        else if (e instanceof TrueLiteral) {
            return eval((TrueLiteral) e, env);
        }
        else if (e instanceof VarName) {
            return eval((VarName) e, env);
        }
        else if (e instanceof UnaryExpression) {
            return eval((UnaryExpression) e, env);
        }
        else {
            throw new Error();
        }
    }

    private Object eval(Statement s, Environment env) throws ReturnValue {
        if (s instanceof DoStatement) {
            eval((DoStatement) s, env);
            return null;
        }
        else if (s instanceof IfStatement) {
            eval((IfStatement) s, env);
            return null;
        }
        else if (s instanceof LetStatement) {
            eval((LetStatement) s, env);
            return null;
        }
        else if (s instanceof ReturnStatement) {
            return eval((ReturnStatement) s, env);
        }
        else if (s instanceof VarStatement) {
            eval((VarStatement) s, env);
            return null;
        }
        else if (s instanceof WhileStatement) {
            eval((WhileStatement) s, env);
            return null;
        }
        else {
            throw new Error();
        }
    }

    private Environment newObject(ClassInfo classInfo) {
        Environment newObject = new JackObject(classInfo);

        for (ClassVarDec fieldVar : classInfo.cl.getFieldVars()) {
            newObject.put(fieldVar.getVarName(), null); // initial value is null
        }

        newObject.put("this", newObject);

        return newObject;
    }

}
