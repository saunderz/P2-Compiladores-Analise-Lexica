# Lox — Análise Léxica (jlox)

**Status:** etapa 1 do projeto — até _4.4 · The Scanner Class_ (Crafting Interpreters)  

> Implementação em **Java (sem Maven)** do analisador léxico da linguagem **Lox**, com REPL simples e execução por arquivo. 
> Foco: tokens básicos, literais, palavras‑chave, operadores de 1–2 caracteres, comentários (// e /* */) e relatório de erros.

---

## 👥 Dupla
- Nome 1 — Luã Coimbra Santiago Saunders @saunderz  
- Nome 2 — Melissa Rodrigues Palhano @melissapalhano  

---

## 📁 Estrutura do projeto
```
lox-lexer/
├─ README.md
├─ .gitignore
├─ examples/
│  └─ hello.lox
└─ src/
   └─ main/
      └─ java/
         └─ lox/
            ├─ Lox.java
            ├─ Scanner.java
            ├─ Token.java
            └─ TokenType.java
```

---

## 🧰 Requisitos
- **Java JDK 17+** no PATH  
  Verifique:
  ```bash
  java -version
  javac -version
  ```

---

## 🔗 Referências
- **Crafting Interpreters — Chapter 4: Scanning**: https://craftinginterpreters.com/scanning.html  
- Repositórios educacionais similares (para estudo).

---

## 📄 Licença
[MIT](https://choosealicense.com/licenses/mit/).  
