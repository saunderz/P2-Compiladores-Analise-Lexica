# P2 — Compiladores: Análise Léxica (jlox)

Disciplina: **Compiladores**  
Linguagem: **Java**  
Base: Livro **Crafting Interpreters** — Capítulo 4 (*Scanning*)

> **Status da entrega**: concluído até **4.7 — Reserved Words and Identifiers** (inclui 4.5 e 4.6).

---

## 👥 Dupla
- **Luã Coimbra Santiago Saunders** — [@saunderz](https://github.com/saunderz)  
- **Melissa Rodrigues Palhano** — [@melissapalhano](https://github.com/melissapalhano)

---

## 🧠 Escopo implementado (Cap. 4)
- **4.4 — The Scanner Class**: esqueleto do `Scanner` e laço de varredura (`scanTokens()`), emissão de `EOF`.
- **4.5 — Recognizing Lexemes**: tokens de **1 caractere** `(){}.,-+;*` e operadores **1–2 chars** `! !=, = ==, < <=, > >=`, com **tratativa de erro léxico** para demais caracteres.
- **4.6 — Longer Lexemes**: suporte a **comentários de linha** `//`, **ignorar whitespace** (`' '`, `\r`, `\t`, `\n`), **strings** entre aspas duplas e **números** (inteiros e fracionários).
- **4.7 — Reserved Words and Identifiers**: **identificadores** (letras/underscore seguidos de letras/dígitos/underscore) e **palavras‑reservadas** mapeadas para `TokenType` específico (`and, class, else, false, for, fun, if, nil, or, print, return, super, this, true, var, while`).

> **Observação:** esta etapa é apenas o **analisador léxico** (scanner). 

---

## 📁 Estrutura do projeto
```
P2-Compiladores-Analise-Lexica/
├─ src/
│  └─ main/
│     └─ java/
│        └─ lox/
│           ├─ Lox.java
│           ├─ Scanner.java
│           ├─ Token.java
│           └─ TokenType.java
├─ .gitignore
├─ LICENSE
└─ README.md
```

---

## ⚙️ Requisitos
- **Java JDK 17+** (ou compatível) instalado e no `PATH`  
  Verifique:
  ```bash
  java -version
  javac -version
  ```

---

## ▶️ Como compilar e executar

### Opção A — Windows PowerShell (sem usar wildcard `*.java`)
> O PowerShell não expande `*.java` para executáveis externos. Use um *response file* ou liste os arquivos.

**Usando response file (recomendado):**
```powershell
# na raiz do projeto
$files = Get-ChildItem -Path src\main\java\lox -Filter *.java | ForEach-Object FullName
$files | Set-Content sources.txt
mkdir out -Force
javac -d out @sources.txt
java -cp out lox.Lox
```

**Ou listando explicitamente:**
```powershell
mkdir out -Force
javac -d out src\main\java\lox\Lox.java src\main\java\lox\Scanner.java src\main\java\lox\Token.java src\main\java\lox\TokenType.java
java -cp out lox.Lox
```

### Opção B — Bash (Linux/macOS)
```bash
mkdir -p out
javac -d out src/main/java/lox/*.java
java -cp out lox.Lox
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
- Robert Nystrom — **Crafting Interpreters**, Capítulo 4: *Scanning*.
