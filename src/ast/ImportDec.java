package ast;

public class ImportDec {
    String classPath;
    
    public ImportDec(String classPath){
        this.classPath = classPath;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

}
