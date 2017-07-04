package ast;

import java.util.List;

public class Subroutine {
    String kind; // "constructor", "function", "method"
    Type type;
    String name;
    List<Parameter> parameters;
    List<Statement> body;

    public Subroutine(String kind, Type type, String name, List<Parameter> parameters, List<Statement> body){
	this.kind = kind;
	this.type = type;
	this.name = name;
	this.parameters = parameters;
	this.body = body;
    }

}
