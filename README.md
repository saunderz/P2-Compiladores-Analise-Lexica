# P2 — Compiladores: Análise Léxica (jlox)

**Disciplina:** Compiladores  
**Etapa:** 4.4 — *The Scanner Class*  
**Linguagem:** Java
**Base:** Livro *Crafting Interpreters* — Capítulo 4 (*Scanning*)  

---

## 👥 Dupla
- **Luã Coimbra Santiago Saunders** — @saunderz  
- **Melissa Palhano** — @melissapalhano  

---

## 🧠 Descrição
Implementação inicial do **interpretador jlox** até a seção **4.4 – The Scanner Class** do livro *Crafting Interpreters*.  
O projeto estabelece a base do analisador léxico, contendo as estruturas fundamentais para a análise de tokens e o framework principal do interpretador.

---

## 📁 Estrutura do Projeto
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
- **Java JDK 17+** instalado e configurado no PATH  
  Verifique:
  ```bash
  java -version
  javac -version
  ```

---

## 🚀 Como Compilar e Executar

### Compilação
No diretório raiz do projeto:
```bash
javac -d out src/main/java/lox/*.java
```

### Execução (modo REPL)
```bash
java -cp out lox.Lox
```
> O prompt `>` aparecerá para digitação interativa.

### Execução (arquivo)
```bash
java -cp out lox.Lox examples/hello.lox
```
---

## 🧾 Licença
Projeto licenciado sob a [MIT License](LICENSE).

---

## 🏁 Conclusão
Entrega referente à primeira etapa prática do projeto de Compiladores, implementando o **analisador léxico (Scanner)** até o ponto definido no livro.  
> “Toda linguagem começa com um bom scanner.” — *Crafting Interpreters*
