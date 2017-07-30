package natives;

import ast.expression.IntegerLiteral;
import evaluator.ClassInfo;
import evaluator.Environment;
import evaluator.JackObject;
import evaluator.NativeSubroutine;

public class Array {

    public static JackObject construct (Integer integer) {
        JackObject newArray = new JackObject();
        newArray.put("size", integer);
        return newArray;
    }

    public static void appendToEnv(Environment env) {
        ClassInfo classInfo = new ClassInfo(env, null);

        try {
            NativeSubroutine construct = new NativeSubroutine("construct",
                    Array.class.getMethod("construct", Integer.class));
            classInfo.put("construct", construct);
        } catch (Exception e) {
            throw new Error("unable to append the native method");
        }

        env.put("Array", classInfo);
    }

}
