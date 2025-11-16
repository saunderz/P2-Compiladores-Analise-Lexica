# P2 — Compiladores: Análise Léxica (jlox)

Disciplina: **Compiladores - EECP0026**  
Linguagem: **Java**  
Base: Livro **Crafting Interpreters**

> **Status da entrega**: concluído até **5.4 — A (Not Very) Pretty Printer** 

---

## 👥 Dupla
- **Luã Coimbra Santiago Saunders** — [@saunderz](https://github.com/saunderz)  
- **Melissa Rodrigues Palhano** — [@melissapalhano](https://github.com/melissapalhano)

---

## 🧠 Escopo implementado

### Capítulo 4 — Scanning
- **4.4 — The Scanner Class**: esqueleto do `Scanner` e laço de varredura (`scanTokens()`), emissão de `EOF`.
- **4.5 — Recognizing Lexemes**: tokens de **1 caractere** `(){}.,-+;*` e operadores **1–2 chars** `! !=, = ==, < <=, > >=`, com **tratativa de erro léxico** para demais caracteres.
- **4.6 — Longer Lexemes**: suporte a **comentários de linha** `//`, **ignorar whitespace** (`' '`, `\r`, `\t`, `\n`), **strings** entre aspas duplas e **números** (inteiros e fracionários).
- **4.7 — Reserved Words and Identifiers**: **identificadores** (letras/underscore seguidos de letras/dígitos/underscore) e **palavras‑reservadas** mapeadas para `TokenType` específico (`and, class, else, false, for, fun, if, nil, or, print, return, super, this, true, var, while`).

> **Observação:** esta etapa é apenas o **analisador léxico** (scanner). 

### Capítulo 5 — Representing Code
- **5.1 — Context-Free Grammars**: teoria de gramáticas livres de contexto, notação BNF e gramática para expressões Lox.
- **5.2 — Implementing Syntax Trees**: implementação de **Abstract Syntax Tree (AST)** com classes `Expr`, `Binary`, `Grouping`, `Literal` e `Unary`.
- **5.2.2 — Metaprogramming the trees**: ferramenta `GenerateAst.java` para gerar automaticamente as classes da AST.
- **5.3 — Working with Trees**: implementação do **padrão Visitor** para operações sobre a árvore sintática.
- **5.4 — A (Not Very) Pretty Printer**: implementação de `AstPrinter` para visualização da estrutura da AST em formato Lisp-like.

---

## 📁 Estrutura do projeto
```
P2-Compiladores-Analise-Lexica/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ lox/
│  │  │     ├─ Lox.java
│  │  │     ├─ Scanner.java
│  │  │     ├─ Token.java
│  │  │     ├─ TokenType.java
│  │  │     ├─ Expr.java          
│  │  │     ├─ AstPrinter.java
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

- **Java JDK 17+** (ou compatível) instalado e no `PATH`  
  O projeto foi testado com **Java 21**. Verifique:
  ```bash
  java -version
  javac -version
  ```

- **Apache Maven 3.6+** instalado e no `PATH`  
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
- Baixe o JDK de [Oracle](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://adoptium.net/)
- Baixe o Maven de [Apache Maven](https://maven.apache.org/download.cgi)
- Configure as variáveis de ambiente `JAVA_HOME` e `MAVEN_HOME`

---

## ▶️ Compilação e Execuçap


Na raiz do projeto, execute:

```bash
mvn clean package
```

```bash
mvn clean install
```

#### Opção 1 — Executar via Maven (REPL interativo)
```bash
mvn exec:java -Dexec.mainClass="lox.Lox"
```

#### Opção 2 — Executar o JAR diretamente (REPL interativo)
```bash
java -cp target/p2-compiladores-analise-lexica-1.0-SNAPSHOT.jar lox.Lox
```

#### Opção 3 — Executar um arquivo Lox
```bash
java -cp target/p2-compiladores-analise-lexica-1.0-SNAPSHOT.jar lox.Lox arquivo.lox
```

---

## 🧪 Testes rápidos no REPL

**4.5 — operadores/tokens básicos (sem espaços necessários a partir de 4.6):**
```
(){}.,-+;**!=====<===>=
```
**4.6 — comentários, strings e números:**
```
// grouping
(( )){} // comment
"lox" 123 45.67
!= == <= >= /
```
**4.7 — identificadores e palavras‑reservadas:**
```
var language = "Lox";
print language;
if (true) print "ok";
while (false) print 0;
orchid or
```

---

## 🪪 Licença
Este projeto é licenciado sob a **MIT License**. Consulte o arquivo [`LICENSE`](LICENSE).

---

## 📚 Referência
- Robert Nystrom — **Crafting Interpreters**.
  - Capítulo 4: *Scanning*
  - Capítulo 5: *Representing Code*
