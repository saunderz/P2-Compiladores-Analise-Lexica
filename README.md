# P2 — Compiladores: Interpretador Lox (Scanner, AST, Parser e Expressões)

Disciplina: Compiladores - EECP0026  
Linguagem: Java  
Base: Livro **Crafting Interpreters** (Robert Nystrom)

> **Status da entrega:** concluído até _Parsing Expressions_
> (expressões apenas – ainda sem declarações/estatements)

---

## 👥 Dupla

- Luã Coimbra Santiago Saunders — [@saunderz](https://github.com/saunderz)
- Melissa Rodrigues Palhano — [@melissapalhano](https://github.com/melissapalhano)

---

## 📌 Escopo implementado

### Capítulo 4 — Scanning

- **4.4 — The Scanner Class**  
  - Implementação da classe `Scanner` com o laço principal `scanTokens()`, leitura do código fonte caractere a caractere e emissão do token `EOF`.

- **4.5 — Recognizing Lexemes**  
  - Reconhecimento de tokens de **1 caractere**: `(` `)` `{` `}` `,` `.` `-` `+` `;` `*`.  
  - Reconhecimento de operadores de **1–2 caracteres**:  
    - `!` `!=`, `=` `==`, `<` `<=`, `>` `>=`.  
  - Tratamento de erro léxico para caracteres inesperados, com reporte de linha.

- **4.6 — Longer Lexemes**  
  - Suporte a:
    - Comentários de linha `//` até o fim da linha;
    - Ignorar espaços em branco (`' '`, `\r`, `\t`, `\n`);
    - Strings entre aspas duplas `"..."` com suporte a múltiplos caracteres;
    - Números inteiros e fracionários (`123`, `45.67`).

- **4.7 — Reserved Words and Identifiers**  
  - Identificadores: letra/underscore seguido de letras, dígitos ou underscore.  
  - Mapeamento de palavras-reservadas para `TokenType` específico:  
    `and, class, else, false, for, fun, if, nil, or, print, return, super, this, true, var, while`.  
  - Demais sequências alfanuméricas são tratadas como identificadores (`IDENTIFIER`).

> Esta etapa corresponde ao **analisador léxico (scanner)** integrado ao restante do interpretador.

---

### Capítulo 5 — Representing Code (AST)

- **5.1 — Context-Free Grammars**  
  - Uso da gramática de expressões de Lox como base para a AST e para o parser.

- **5.2 — Implementing Syntax Trees**  
  - Implementação da **Abstract Syntax Tree (AST)** para expressões em `Expr.java`, com as variantes:
    - `Expr.Binary`
    - `Expr.Grouping`
    - `Expr.Literal`
    - `Expr.Unary`

- **5.2.2 — Metaprogramming the Trees**  
  - Ferramenta `tool/GenerateAst.java` para gerar automaticamente as classes de `Expr` com o padrão Visitor.

- **5.3 — Working with Trees**  
  - Implementação do padrão **Visitor** na AST:
    - Interface `Expr.Visitor<R>` com métodos `visitBinaryExpr`, `visitGroupingExpr`, `visitLiteralExpr`, `visitUnaryExpr`.
    - Método `accept(Visitor<R> visitor)` em cada classe concreta.

- **5.4 — A (Not Very) Pretty Printer**  
  - Implementação de `AstPrinter` para visualização da AST em formato estilo Lisp:
    - Ex.: expressão `1 + 2 * 3` gera algo como `(+ 1 (* 2 3))`.

---

### Capítulo 6 — Parsing Expressions

Baseado em **“Parsing Expressions”** (Crafting Interpreters).

- Implementação da classe `Parser` com um **parser recursivo descendente**, seguindo a gramática:

  ```text
  expression  → equality ;
  equality    → comparison ( ( "!=" | "==" ) comparison )* ;
  comparison  → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
  term        → factor ( ( "-" | "+" ) factor )* ;
  factor      → unary ( ( "/" | "*" ) unary )* ;
  unary       → ( "!" | "-" ) unary | primary ;
  primary     → NUMBER | STRING | "true" | "false" | "nil"
              | "(" expression ")" ;
  ```

- Métodos principais implementados em `Parser.java`:
  - `Expr parse()`
  - `expression()`, `equality()`, `comparison()`, `term()`, `factor()`, `unary()`, `primary()`

- **Suporte a:**
  - Literais: números, strings, `true`, `false`, `nil`;
  - Agrupamentos: `(` _expression_ `)`;
  - Operadores unários: `!` e `-`;
  - Operadores binários:
    - Aritméticos: `+`, `-`, `*`, `/`;
    - Comparações: `<`, `<=`, `>`, `>=`;
    - Igualdade: `==`, `!=`.

- **Precedência e associatividade:**
  - A cadeia de chamadas (`expression → equality → comparison → term → factor → unary → primary`) garante:
    - `*` e `/` com maior precedência que `+` e `-`;
    - Comparações e igualdade em níveis mais altos da árvore;
    - Associatividade **à esquerda** para operadores binários.

- **Tratamento de erros sintáticos:**
  - Classe interna `ParseError` em `Parser`;
  - Uso de `Lox.error(token, message)` para reportar erros;
  - Método `synchronize()` esqueleto para recuperação de erros (será utilizado quando forem adicionados statements).

---

### Em breve: Capítulo 7 — Evaluating Expressions (Interpretador de Expressões)

Baseado em **“Evaluating Expressions”** (Crafting Interpreters).

- Implementação da classe `Interpreter` que implementa `Expr.Visitor<Object>`:
  - `visitLiteralExpr(Expr.Literal expr)`
  - `visitGroupingExpr(Expr.Grouping expr)`
  - `visitUnaryExpr(Expr.Unary expr)`
  - `visitBinaryExpr(Expr.Binary expr)`

- Método central de avaliação:

  ```java
  private Object evaluate(Expr expr) {
      return expr.accept(this);
  }
  ```

- **Suporte de avaliação para:**
  - **Literais:** `1`, `"texto"`, `true`, `false`, `nil`;
  - **Agrupamentos:** `(1 + 2) * 3` → respeitando o valor interno;
  - **Operações unárias:**
    - `-5` (negação numérica);
    - `!false`, `!true`, `!nil` (semântica de verdade de Lox);
  - **Operações binárias:**
    - Aritméticas: `+`, `-`, `*`, `/` entre números (`Double`);
    - Comparações: `<`, `<=`, `>`, `>=` entre números;
    - Igualdade: `==`, `!=` entre quaisquer valores;
    - Concatenação de strings: `"ab" + "cd"` → `"abcd"`.

- **Semântica booleana (“truthiness”):**
  - `nil` é considerado `false`;
  - `false` é `false`;
  - Todo o resto é `true`.

- **Tratamento de erros em tempo de execução:**
  - Classe `RuntimeError` com referência ao `Token` que causou o erro;
  - Método `Lox.runtimeError(RuntimeError error)` para imprimir a mensagem e a linha;
  - Marcação de `Lox.hadRuntimeError` para sinalizar falhas durante a avaliação.

- **Integração no `Lox.run(...)`:**
  - Pipeline completo:
    ```text
    fonte → Scanner → tokens → Parser → AST (Expr) → Interpreter → valor impresso
    ```
  - O REPL e a execução de arquivos agora avaliam de fato as expressões, em vez de apenas imprimir a AST.

---

## 📁 Estrutura do projeto

```text
P2-Compiladores-Analise-Lexica/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ lox/
│  │  │     ├─ Lox.java          // ponto de entrada (REPL / arquivo)
│  │  │     ├─ Scanner.java      // analisador léxico
│  │  │     ├─ Token.java
│  │  │     ├─ TokenType.java
│  │  │     ├─ Expr.java         // AST de expressões + Visitor
│  │  │     ├─ AstPrinter.java   // impressor de AST (debug)
│  │  │     ├─ Parser.java       // parser recursivo descendente
│  │  │     ├─ Interpreter.java  // avaliador das expressões
│  │  │     ├─ RuntimeError.java // erro em tempo de execução
│  │  │     └─ tool/
│  │  │        └─ GenerateAst.java
│  │  └─ resources/
│  └─ test/
│     ├─ java/
│     └─ resources/
├─ pom.xml
├─ .gitignore
├─ LICENSE
└─ README.md
```

---

## ⚙️ Pré-requisitos

Antes de compilar e executar o projeto, certifique-se de ter instalado:

- **Java JDK 17+** (testado com Java 21)  
  Verifique:

  ```bash
  java -version
  javac -version
  ```

- **Apache Maven 3.6+**  
  Verifique:

  ```bash
  mvn -version
  ```

### Instalação (se necessário)

**Linux (Ubuntu/Debian):**

```bash
sudo apt update
sudo apt install -y openjdk-21-jdk maven
```

**macOS (Homebrew):**

```bash
brew install openjdk@21 maven
```

**Windows:**

- Baixar JDK (Oracle ou OpenJDK);
- Baixar Maven;
- Configurar variáveis de ambiente `JAVA_HOME` e `MAVEN_HOME`.

---

## ▶️ Compilação e Execução

Na raiz do projeto:

```bash
mvn clean package
mvn clean install
```

### Opção 1 — Executar via Maven (REPL interativo)

```bash
mvn exec:java -Dexec.mainClass="lox.Lox"
```

### Opção 2 — Executar o JAR diretamente (REPL interativo)

```bash
java -cp target/p2-compiladores-analise-lexica-1.0-SNAPSHOT.jar lox.Lox
```

### Opção 3 — Executar um arquivo Lox

```bash
java -cp target/p2-compiladores-analise-lexica-1.0-SNAPSHOT.jar lox.Lox programa.lox
```

---

## 🧪 Testes rápidos

### Tokens (scanner)

```lox
(){}.,-+;*!=====<===>
```

Comentários, strings e números:

```lox
// grouping
(( )){} // comment
"lox" 123 45.67
!= == <= >= /
```

Identificadores e palavras-reservadas:

```lox
var language = "Lox";
print language;
if (true) print "ok";
while (false) print 0;
orchid or
```

### Expressões (parser + interpretador)

No REPL:

```lox
> 123;
123

> "ab" + "cd";
abcd

> -5;
-5

> !false;
true

> (1 + 2) * 3;
9

> 1 + 2 * 3;
7

> 1 < 2 == true;
true
```

---

## 📄 Licença

Este projeto é licenciado sob a **MIT License**. Consulte o arquivo `LICENSE`.

---

## 📚 Referência

- Robert Nystrom — **Crafting Interpreters**  
  - Capítulo 4: Scanning  
  - Capítulo 5: Representing Code  
  - Capítulo 6: Parsing Expressions  
  - Capítulo 7: Evaluating Expressions
