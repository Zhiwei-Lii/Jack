package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import frontEnd.CharStream;

public class Scanner {
    CharStream charStream;
    
    public Scanner(String filePath) throws FileNotFoundException{
	CharStream charStream = new CharStream(filePath);
	

	
    }

}
