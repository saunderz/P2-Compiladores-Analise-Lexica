package lox;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static lox.TokenType.*;

class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private int start = 0;
  private int current = 0;
  private int line = 1;

  // 4.7 — palavras-reservadas
  private static final Map<String, TokenType> keywords = new HashMap<>();
  static {
    keywords.put("and", AND);
    keywords.put("class", CLASS);
    keywords.put("else", ELSE);
    keywords.put("false", FALSE);
    keywords.put("for", FOR);
    keywords.put("fun", FUN);
    keywords.put("if", IF);
    keywords.put("nil", NIL);
    keywords.put("or", OR);
    keywords.put("print", PRINT);
    keywords.put("return", RETURN);
    keywords.put("super", SUPER);
    keywords.put("this", THIS);
    keywords.put("true", TRUE);
    keywords.put("var", VAR);
    keywords.put("while", WHILE);
  }

  Scanner(String source) { this.source = source; }

  List<Token> scanTokens() {
    while (!isAtEnd()) {
      start = current; // início do próximo lexema
      scanToken();
    }
    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }

  private void scanToken() {
    char c = advance();
    switch (c) {
      // 1 caractere
      case '(' -> addToken(LEFT_PAREN);
      case ')' -> addToken(RIGHT_PAREN);
      case '{' -> addToken(LEFT_BRACE);
      case '}' -> addToken(RIGHT_BRACE);
      case ',' -> addToken(COMMA);
      case '.' -> addToken(DOT);
      case '-' -> addToken(MINUS);
      case '+' -> addToken(PLUS);
      case ';' -> addToken(SEMICOLON);
      case '*' -> addToken(STAR);

      // 1–2 caracteres
      case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
      case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
      case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
      case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);

      // divisão e comentários de linha
      case '/' -> {
        if (match('/')) {
          while (peek() != '\n' && !isAtEnd()) advance(); // ignora até o fim da linha
        } else {
          addToken(SLASH);
        }
      }

      // ignorar whitespace
      case ' ', '\r', '\t' -> { /* ignore */ }
      case '\n' -> line++;

      // strings
      case '"' -> string();

      default -> {
        if (isDigit(c)) {
          number();
        } else if (isAlpha(c)) {     // 4.7 — identificadores/keywords
          identifier();
        } else {
          Lox.error(line, "Unexpected character.");
        }
      }
    }
  }

  // ----- handlers -----

  private void string() {
    while (peek() != '"' && !isAtEnd()) {
      if (peek() == '\n') line++;
      advance();
    }
    if (isAtEnd()) {
      Lox.error(line, "Unterminated string.");
      return;
    }
    advance(); // fecha aspas
    String value = source.substring(start + 1, current - 1);
    addToken(STRING, value);
  }

  private void number() {
    while (isDigit(peek())) advance();
    if (peek() == '.' && isDigit(peekNext())) {
      advance(); // consome '.'
      while (isDigit(peek())) advance();
    }
    String lex = source.substring(start, current);
    addToken(NUMBER, Double.parseDouble(lex));
  }

  private void identifier() {
    while (isAlphaNumeric(peek())) advance();
    String text = source.substring(start, current);
    TokenType type = keywords.get(text); // minúsculas → keywords
    if (type == null) type = IDENTIFIER;
    addToken(type);
  }

  // ----- helpers -----

  private boolean isAtEnd() { return current >= source.length(); }

  private char advance() { return source.charAt(current++); }

  private boolean match(char expected) {
    if (isAtEnd()) return false;
    if (source.charAt(current) != expected) return false;
    current++;
    return true;
  }

  private char peek() {
    if (isAtEnd()) return '\0';
    return source.charAt(current);
  }

  private char peekNext() {
    if (current + 1 >= source.length()) return '\0';
    return source.charAt(current + 1);
  }

  private boolean isDigit(char c) { return c >= '0' && c <= '9'; }

  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') ||
           (c >= 'A' && c <= 'Z') ||
            c == '_';
  }

  private boolean isAlphaNumeric(char c) { return isAlpha(c) || isDigit(c); }

  private void addToken(TokenType type) { addToken(type, null); }

  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }
}
