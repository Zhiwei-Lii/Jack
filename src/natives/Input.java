package natives;

import java.util.Scanner;

import ast.expression.IntegerLiteral;
import ast.expression.StringLiteral;
import evaluator.ClassInfo;
import evaluator.Environment;
import evaluator.NativeSubroutine;

public class Input {
    public static StringLiteral readLn() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return new StringLiteral(line);
    }

    public static IntegerLiteral readInt() {
        Scanner scanner = new Scanner(System.in);
        int intVal = scanner.nextInt();
        return new IntegerLiteral(intVal);
    }

    public static void appendToEnv(Environment env) {
        ClassInfo classInfo = new ClassInfo(env, null);

        try {
            NativeSubroutine readLn = new NativeSubroutine("readLn",
                    Input.class.getMethod("readLn"));
            classInfo.put("readLn", readLn);

            NativeSubroutine readInt = new NativeSubroutine("readInt",
                    Input.class.getMethod("readInt"));
            classInfo.put("readInt", readInt);
        } catch (Exception e) {
            throw new Error("unable to append the native method");
        }
        

        env.put("Input", classInfo);
    }


}
