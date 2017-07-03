package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Queue;

public class CharStream {
    private Reader reader;
    private int[] buffer = new int[256]; // buffer是循环数组, 为了支持lookahead而设计
    private int pointer = 0;
    private boolean meetEOF = false;

    public CharStream(String fileName) throws FileNotFoundException {
	File file = new File(fileName);
	reader = new FileReader(file);
    }

    public void consume() {
	buffer[pointer] = -1; 
	pointer = pointer >= buffer.length ? 0 : pointer + 1;
	if (!meetEOF) {
	    try {
		int c = reader.read();
		buffer[pointer - 1] = c;
		meetEOF = (c == -1 ? true : false);
	    } catch (Exception e) {
		System.out.println(e);
	    }
	}
    }

    public char current() {
	return (char) buffer[pointer];
    }

    public boolean isEOF() {
	return buffer[pointer] == -1;
    }

    public char lookAhead(int offset) {
	int index = (pointer + offset) % buffer.length;
	return (char) buffer[index];
    }

}
