package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CharStream {
    private Reader reader;
    private int current;

    public CharStream(String fileName) throws IOException {
	File file = new File(fileName);
	reader = new FileReader(file);
	consume();
    }

    public void consume() throws IOException {
	current = reader.read();
    }

    public char current() {
	return (char) current;
    }
    
    public boolean isEOF() {
	return current == -1;
    }

}
