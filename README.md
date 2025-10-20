# Árvore Binária - Código Morse

## Descrição
Implementação de uma **árvore binária** onde cada nó representa um símbolo do **Código Morse**.
- Pontos (`.`) são filhos à esquerda.
- Traços (`-`) são filhos à direita.

O projeto suporta:
- Inserção de novos símbolos.
- Busca por código Morse ou por caractere.
- Remoção de símbolos (limpa o nó).
- Exibição hierárquica da árvore.
- Tradução de mensagens (códigos separados por espaço).

---

Use o menu interativo para:

Buscar códigos Morse.

Inserir novos símbolos.

Remover símbolos.

Exibir a árvore.

---

Observações importantes / Restrições

A árvore é inicializada automaticamente com letras A–Z e números 0–9.

Remover um caractere apenas limpa o valor do nó (não deleta ramos), mantendo a estrutura de caminhos intacta.

Não são utilizadas classes especiais como StringBuilder, Vector, Integer, etc.

Apenas java.util.Scanner é usado para entrada simples.

---

## Arquivos
- **ArvoreBinariaMorse.java** — Código fonte completo.

---

## Como foi executado

IntelliJ IDEA

Um único arquivo ArvoreBinariaMorse.java

---

## Exemplos de uso

Buscar ... → retorna S

Buscar ... --- ... → retorna SOS

Buscar caractere A → retorna .-
