package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CharStream {
    private Reader reader;
    private List<Character> buffer;
    private int pointer = 0;

    public CharStream(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        reader = new FileReader(file);
        buffer = new ArrayList<Character>();

        try {
            int c = reader.read();
            while (c != -1) {
                buffer.add((char) c);
                c = reader.read();
            }
            buffer.add((char) c);

        } catch (Exception e) {

        }
    }

    public void consume() {
        pointer++;
    }

    public char current() {
        return buffer.get(pointer);
    }

    public boolean isEOF() {
        return current() == (char) -1;
    }

    public char lookAhead(int offset) {
        return buffer.get(pointer + offset);
    }

}
