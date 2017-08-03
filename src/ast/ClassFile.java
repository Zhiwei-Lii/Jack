package ast;

import java.util.List;

public class ClassFile {
    List<ImportDec> importDecList;
    List<Class> classDecList;
    
    public ClassFile(List<ImportDec> importDecList, List<Class> classDecList){
        this.importDecList = importDecList;
        this.classDecList = classDecList;
    }

    public List<ImportDec> getImportDecList() {
        return importDecList;
    }

    public void setImportDecList(List<ImportDec> importDecList) {
        this.importDecList = importDecList;
    }

    public List<Class> getClassDecList() {
        return classDecList;
    }

    public void setClassDecList(List<Class> classDecList) {
        this.classDecList = classDecList;
    }

}
