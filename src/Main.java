import java.io.FileNotFoundException;

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
        
        //parser.parse();
        Environment topEnv = new BasicEnv();
        Evaluator v = new Evaluator(parser.parse(), topEnv);
    }

}
