package parser;

import java.util.LinkedList;

public class TokenStream {
    // 可以优化成循环数组
    private LinkedList<Token> tokens;

    public TokenStream(Lexer lexer) {
        tokens = new LinkedList<Token>();
        while (lexer.hasNext()) {
            tokens.add(lexer.nextToken());
        }
    }

    public Token lookahead(int k) {
        if (k >= tokens.size()) {
            System.out.println("TokenStream::lookahead -> error");
        }
        return tokens.get(k);
    }

    public Token currentToken() {
        return lookahead(0);
    }

    public void consume() {
        tokens.removeFirst();
    }

    public boolean hasNext() {
        return tokens.size() != 0;
    }

}
