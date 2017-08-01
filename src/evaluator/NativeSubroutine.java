package evaluator;

import java.lang.reflect.Method;

public class NativeSubroutine {
    boolean isStatic;
    String subroutineName;
    Method method;
    
    public NativeSubroutine(boolean isStatic, String subroutineName, Method method){
        this.isStatic = isStatic;
        this.subroutineName = subroutineName;
        this.method = method;
    }
    
    public boolean isStatic() {
        return isStatic;
    }
    
    public Method getMethod(){
        return method;
    }

    public String getSubroutineName() {
        return subroutineName;
    }

    public void setSubroutineName(String subroutineName) {
        this.subroutineName = subroutineName;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
