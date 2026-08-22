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

    private char advance()  {
        current++;
        return source.charAt(current - 1);
    }
    private void addToken(TokenType type){
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected)return false;

        current++;
        return true;
    }
    private char peek() {   //看下一个字符，不像advance会消费字符
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')){
                    while (peek() != '\n' && !isAtEnd()) advance(); //peak直到换行，消费掉中间全部字符(换行留到后面匹配)
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;  //跳过空白字符
            case '\n':
                line++;
                break;
            default:
                Lox.error(line, "Unexpected character.");
                break;
        }
    }
}
