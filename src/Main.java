import java.io.FileNotFoundException;

import evaluator.BasicEnv;
import evaluator.Environment;
import evaluator.Evaluator;
import natives.Array;
import natives.Input;
import natives.Output;
import parser.CharStream;
import parser.Lexer;
import parser.Parser;
import parser.TokenStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{
        CharStream charStream = new CharStream(args[0]);
        Lexer lexer = new Lexer(charStream);
        TokenStream tokenStream = new TokenStream(lexer);
        Parser parser = new Parser(tokenStream);
        
        Environment topEnv = new BasicEnv();
        registerNatives(topEnv);
        Evaluator v = new Evaluator(parser.parse(), topEnv);
    }
    
    public static void registerNatives(Environment env){
        Output.appendToEnv(env);
        Input.appendToEnv(env);
        Array.appendToEnv(env);
    }

}
