package natives;

import evaluator.ClassInfo;
import evaluator.Environment;
import evaluator.JackObject;
import evaluator.NativeSubroutine;

public class Array {
    static ClassInfo classInfo;

    public static JackObject construct (Integer integer) {
        JackObject newArray = new JackObject(classInfo);
        newArray.put("size", integer);
        return newArray;
    }

    public static void appendToEnv(Environment env) {
        classInfo = new ClassInfo(env, "Array", null);

        try {
            NativeSubroutine construct = new NativeSubroutine(true, "new",
                    Array.class.getMethod("construct", Integer.class));
            classInfo.put("new", construct);
        } catch (Exception e) {
            throw new Error("unable to append the native method");
        }

        env.put("Array", classInfo);
    }

}
