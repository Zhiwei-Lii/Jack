package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CharStream {
    Reader reader;

    public CharStream(String fileName) throws FileNotFoundException {
	File file = new File(fileName);
	reader = new FileReader(file);
    }
    
    public char next() throws IOException{
	return (char)reader.read();
    }

}
