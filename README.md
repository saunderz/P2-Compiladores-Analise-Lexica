# Lox — Análise Léxica, Expressões, Controle de Fluxo, Funções e Classes (jlox)

Interpretador da linguagem **Lox** implementado em **Java**, seguindo o livro _Crafting Interpreters_ (Robert Nystrom).

Este repositório reúne as entregas das unidades de **Compiladores**:

- **Unidade 2**: Análise Léxica — capítulo 4 (_Scanning_), até a seção **4.7 – Reserved Words and Identifiers**.
- **Unidade 3**: _Statements and State_ (capítulo 8) e _Control Flow_ (capítulo 9) — suporte a declarações, variáveis e fluxo de controle.
- **Unidade 4**: _Functions_ (cap. 10), _Resolving and Binding_ (cap. 11) e _Classes_ (cap. 12) — suporte a funções de primeira classe, resolução estática de variáveis e programação orientada a objetos com classes.

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
    - atribuição de variáveis,
    - **funções** (`fun`, chamada, retorno),
    - **classes, métodos, propriedades e inicializadores** (`class`, `this`, `init`).
  - Tratamento de **erros em tempo de execução** com `RuntimeError.java`.

### Unidade 4 — Functions, Resolving and Binding, Classes (caps. 10–12)

- **Funções e chamadas (`fun` / `return`)**:
  - Definição de funções nomeadas:
    ```lox
    fun add(a, b) {
      return a + b;
    }
    print add(2, 3); // 5
    ```
  - Funções como valores de primeira classe (armazenadas em variáveis e campos).
  - Retorno antecipado com `return`.

- **Resolução de variáveis e escopo léxico**:
  - `Resolver.java` faz uma passagem estática para determinar em qual escopo cada variável é resolvida.
  - O `Interpreter` usa um mapa de profundidades (`locals`) em conjunto com `Environment.getAt()` e `assignAt()` para buscar/atribuir variáveis no escopo correto.
  - Isso garante que capturas de variáveis por funções (closures) funcionem corretamente.

- **Funções como objetos chamáveis**:
  - `LoxCallable.java` define a interface para qualquer coisa invocável em Lox (`call()` e `arity()`).
  - `LoxFunction.java` implementa `LoxCallable` para representar funções definidas pelo usuário, incluindo:
    - fechamento léxico (`Environment closure`),
    - suporte a `return` via exceção interna `Return`,
    - suporte especial para inicializadores de classe (`isInitializer`).

- **Classes, instâncias e métodos (cap. 12)**:
  - Sintaxe de declaração de classes:
    ```lox
    class Person {
      init(name) {
        this.name = name;
      }

      sayName() {
        print this.name;
      }
    }
    ```
  - `LoxClass.java` representa classes em tempo de execução e implementa `LoxCallable`:
    - Chamar uma classe (`Person("Jane")`) cria uma nova instância (`LoxInstance`).
    - Se existir um método `init`, ele é chamado como inicializador.
  - `LoxInstance.java` representa instâncias, com:
    - mapa de campos (`fields`) para propriedades dinâmicas,
    - acesso a métodos da classe via `klass.findMethod(...)` e ligação de `this` (`bind`).
  - Suporte a:
    - acesso a propriedades: `obj.field`,
    - escrita de propriedades: `obj.field = valor`,
    - chamada de métodos: `obj.method(args)`,
    - uso de `this` dentro de métodos para acessar o objeto atual.

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
            ├─ RuntimeError.java
            ├─ LoxCallable.java
            ├─ LoxFunction.java
            ├─ Resolver.java
            ├─ Return.java
            ├─ LoxClass.java
            └─ LoxInstance.java
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

Exemplos adicionais envolvendo **funções** e **classes** (pode criar na pasta `examples/`):

```lox
// 05_functions.lox
fun greet(name) {
  print "Hello, " + name + "!";
}

greet("Lox");

fun makeCounter() {
  var count = 0;
  fun inc() {
    count = count + 1;
    print count;
  }
  return inc;
}

var c = makeCounter();
c(); // 1
c(); // 2
```

```lox
// 06_classes.lox
class Person {
  init(name) {
    this.name = name;
  }

  sayName() {
    print this.name;
  }
}

var jane = Person("Jane");
jane.sayName(); // Jane
``` 

---

## 🔗 Referências

- Robert Nystrom — [_Crafting Interpreters_](https://craftinginterpreters.com/)
  - Capítulo 4: **Scanning**
  - Capítulo 8: **Statements and State**
  - Capítulo 9: **Control Flow**
  - Capítulo 10: **Functions**
  - Capítulo 11: **Resolving and Binding**
  - Capítulo 12: **Classes** (incluindo *Design Note: Prototypes and Power*)

---

## 📄 Licença

Este projeto é distribuído sob a licença [MIT](LICENSE).
