package natives;

import evaluator.ClassInfo;
import evaluator.Environment;
import evaluator.NativeSubroutine;

public class Output {

    public static void printLn(Object s) {
        System.out.println(s.toString());
    }

    public static void appendToEnv(Environment env) {
        ClassInfo classInfo = new ClassInfo(env, null);

        try {
            NativeSubroutine printString = new NativeSubroutine("printLn",
                    Output.class.getMethod("printLn", Object.class));
            classInfo.put("printLn", printString);
        } catch (Exception e) {
            throw new Error("unable to append the native method");
        }
        

        env.put("Output", classInfo);
    }

}
