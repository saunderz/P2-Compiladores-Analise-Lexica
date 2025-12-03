# Lox — Análise Léxica, Expressões e Controle de Fluxo (jlox)

Interpretador da linguagem **Lox** implementado em **Java**, seguindo o livro _Crafting Interpreters_ (Robert Nystrom).

Este repositório reúne as entregas das unidades de **Compiladores**:

- **Unidade 2**: Análise Léxica — capítulo 4 (_Scanning_), até a seção **4.7 – Reserved Words and Identifiers**.
- **Unidade 3**: _Statements and State_ (capítulo 8) e _Control Flow_ (capítulo 9) — suporte a declarações, variáveis e fluxo de controle.

---

## 👥 Dupla

- **Luã Coimbra Santiago Saunders** — [@saunderz](https://github.com/saunderz)
- **Melissa Rodrigues Palhano** — [@melissapalhano](https://github.com/melissapalhano)

---

## 📚 Escopo Implementado

### Unidade 2 — Scanning (cap. 4)

- Implementação completa do **Scanner**:
  - Tokens de 1 caractere: `(){}.,-+;/*`.
  - Operadores compostos: `!=`, `==`, `<=`, `>=`.
  - Literais: **número**, **string**, **booleanos** (`true`, `false`) e `nil`.
  - Identificadores e **palavras reservadas** (keywords).
  - Ignora comentários `//` e espaços em branco.
  - Tratamento de **erros léxicos** (caractere inesperado, string não terminada).

Arquivos principais:

- `Lox.java` — *entrypoint* do interpretador, shell (arquivo e REPL) + tratamento de erros léxicos/sintáticos.
- `Scanner.java` — analisador léxico.
- `Token.java` — representação de token.
- `TokenType.java` — enumeração dos tipos de token.

### Unidade 3 — Statements and State, Control Flow (caps. 8–9)

Extensão do interpretador para:

- **AST (Árvore de Sintaxe Abstrata)**:
  - `Expr.java` — expressões:
    - literais, unários, binários, agrupamento, variáveis, atribuição.
  - `Stmt.java` — declarações:
    - `print`, `var`, `block` (`{ ... }`), `if`/`else`, expressão.

- **Parser recursivo descendente (`Parser.java`)**:
  - Mantém suporte às expressões da unidade anterior.
  - Adiciona:
    - Declarações `var` e `print`.
    - Blocos `{ ... }` com múltiplas declarações.
    - Condicionais `if` / `else`.
    - Atribuição `=` com verificação de alvo válido.

- **Ambiente de execução (`Environment.java`)**:
  - Tabela de símbolos em tempo de execução.
  - Suporte a escopos aninhados (blocos).

- **Interpreter (`Interpreter.java`)**:
  - Avaliação de expressões.
  - Execução de:
    - `print` (saída padrão),
    - `var` (declaração e inicialização),
    - `block` (novo escopo léxico),
    - `if` / `else` (controle de fluxo),
    - atribuição de variáveis.
  - Tratamento de **erros em tempo de execução** com `RuntimeError.java`.

---

## 📁 Estrutura do Projeto

```text
P2-Compiladores-Analise-Lexica/
├─ README.md
├─ .gitignore
├─ LICENSE
├─ examples/
│  ├─ 01_print_var.lox
│  ├─ 02_blocks.lox
│  ├─ 03_if.lox
│  └─ 04_assign.lox
└─ src/
   └─ main/
      └─ java/
         └─ lox/
            ├─ Lox.java
            ├─ Scanner.java
            ├─ Token.java
            ├─ TokenType.java
            ├─ Expr.java
            ├─ Stmt.java
            ├─ Parser.java
            ├─ Interpreter.java
            ├─ Environment.java
            └─ RuntimeError.java
```

> Obs.: a estrutura pode conter branches específicos (por exemplo, `develop`, `feature/expression-interpreter`, `feature/statements-and-control-flow`) de acordo com o fluxo Git adotado na disciplina.

---

## 🧰 Requisitos

- **Java JDK 17+** instalado e configurado no `PATH`.

Verifique:

```bash
java -version
javac -version
```

---

## 🔧 Compilação e Execução

### 1. Compilar (PowerShell — Windows)

No diretório raiz do projeto:

```powershell
# Gera lista de arquivos Java (PowerShell não expande *.java diretamente no javac)
$files = Get-ChildItem -Path src\main\java\lox -Filter *.java | ForEach-Object FullName
$files | Set-Content sources.txt

mkdir out -Force
javac -d out @sources.txt
```

### 2. Executar em modo REPL

```bash
java -cp out lox.Lox
```

Exemplos para testar diretamente no prompt:

```lox
// Variável + print
var msg = "hello lox";
print msg;

// Blocos e escopo
var a = "global";
{
  var a = 123;
  print a; // 123
}
print a;   // global

// If/else
if (true) print "ok"; else print "no";

// Atribuição
var x = 10;
x = x + 5;
print x; // 15
```

### 3. Executar arquivos `.lox`

```bash
java -cp out lox.Lox examples/01_print_var.lox
java -cp out lox.Lox examples/02_blocks.lox
java -cp out lox.Lox examples/03_if.lox
java -cp out lox.Lox examples/04_assign.lox
``` 

---

## 🔗 Referências

- Robert Nystrom — [_Crafting Interpreters_](https://craftinginterpreters.com/)
  - Capítulo 4: **Scanning**
  - Capítulo 8: **Statements and State**
  - Capítulo 9: **Control Flow**

---

## 📄 Licença

Este projeto é distribuído sob a licença [MIT](LICENSE).
