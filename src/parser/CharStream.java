package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Queue;

public class CharStream {
    private Reader reader;
    private char[] buffer = new char[1024]; // 待优化成循环数组, 源代码过大时会产生bug
    private int pointer = 0;
    private boolean meetEOF = false;

    public CharStream(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        reader = new FileReader(file);

        try {
            reader.read(buffer);  // 这么读的时候, 0是eof
        } catch (Exception e) {

        }
    }

    public void consume() {
        /*
         * buffer[pointer] = -1; pointer = pointer >= buffer.length ? 0 : pointer + 1; if (!meetEOF)
         * { try { int c = reader.read(); buffer[pointer] = c; meetEOF = (c == -1 ? true : false); }
         * catch (Exception e) { System.out.println(e); } }
         */
        pointer++;
    }

    public char current() {
        return buffer[pointer];
    }

    public boolean isEOF() {
        return buffer[pointer] == 0;
    }

    public char lookAhead(int offset) {
        return buffer[pointer + offset]; // 可能out of bound
    }

}
