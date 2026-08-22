package lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lox.TokenType.*;

class Scanner {
    private final String source;    //输入源码
    private final List<Token> tokens = new ArrayList<>();   //输出tokens列表

    private int start = 0;
    private int current = 0;    //指向字符串的偏移量
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    private boolean isAtEnd(){
        return current >= source.length();
    }
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
}
