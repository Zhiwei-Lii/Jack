package natives;

import ast.expression.CharLiteral;
import evaluator.ClassInfo;
import evaluator.Environment;
import evaluator.JackObject;
import evaluator.NativeSubroutine;

public class String {
    static ClassInfo classInfo; // 解释器一运行, classInfo就被初始化了

    public static JackObject construct(java.lang.String val) {
        JackObject newString = new JackObject(classInfo);
        newString.put("val", val);
        return newString;
    }

    public static CharLiteral charAt(JackObject stringObject, Integer i) {
        return new CharLiteral(((java.lang.String) stringObject.get("val")).charAt(i));
    }

    public Integer length(JackObject stringObject) {
        return ((java.lang.String) stringObject.get("val")).length();
    }

    public static void appendToEnv(Environment env) {
        classInfo = new ClassInfo(env, "String", null);

        try {
            NativeSubroutine charAt =
                    new NativeSubroutine(false, "charAt", String.class.getMethod("charAt", JackObject.class, Integer.class));
            classInfo.put("charAt", charAt);

            NativeSubroutine length =
                    new NativeSubroutine(false, "length", String.class.getMethod("length", JackObject.class));
            classInfo.put("length", length);
        } catch (Exception e) {
            throw new Error("unable to append the native method");
        }

        env.put("String", classInfo);
    }

}
