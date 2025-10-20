import java.util.Scanner;

/*
 * '.' (ponto) -> filho esquerdo
 * '-' (traço) -> filho direito
 */

class Nodo {
    public char valor;    // caractere armazenado (ex: 'A', '1', '\0' se vazio)
    public Nodo esquerdo; // caminho para ponto (.)
    public Nodo direito;  // caminho para traço (-)

    public Nodo(char v) {
        this.valor = v;
        this.esquerdo = null;
        this.direito = null;
    }
}

public class ArvoreBinariaMorse {
    private Nodo raiz;

    // Inicializa a árvore com raiz vazia
    public void inicializar() {
        this.raiz = new Nodo('\0'); // raiz sem valor
    }

    // Insere um caractere seguindo o código morse (ex: ".-" e 'A')
    public void inserir(String codigo, char c) {
        if (codigo == null) return;
        Nodo atual = raiz;
        int n = codigo.length();
        for (int i = 0; i < n; i++) {
            char s = codigo.charAt(i);
            if (s == '.') {
                if (atual.esquerdo == null) atual.esquerdo = new Nodo('\0');
                atual = atual.esquerdo;
            } else if (s == '-') {
                if (atual.direito == null) atual.direito = new Nodo('\0');
                atual = atual.direito;
            } else {
                // ignorar outros caracteres
            }
        }
        atual.valor = c;
    }

    // Busca um único código morse e retorna o caractere (ou '\0' se não tiver)
    public char buscar(String codigo) {
        if (codigo == null) return '\0';
        Nodo atual = raiz;
        int n = codigo.length();
        for (int i = 0; i < n; i++) {
            char s = codigo.charAt(i);
            if (s == '.') {
                if (atual.esquerdo == null) return '\0';
                atual = atual.esquerdo;
            } else if (s == '-') {
                if (atual.direito == null) return '\0';
                atual = atual.direito;
            } else {
                // caractere inesperado -> retorna vazio
                return '\0';
            }
        }
        return atual.valor;
    }

    // Busca uma mensagem em morse
    // ex: "... --- ..." -> "SOS"
    public String buscarMensagem(String mensagemMorse) {
        if (mensagemMorse == null) return "";
        String resultado = "";
        String token = "";
        int len = mensagemMorse.length();
        for (int i = 0; i < len; i++) {
            char ch = mensagemMorse.charAt(i);
            if (ch == ' ') {
                // se há token acumulado, buscar e adicionar
                if (token.length() > 0) {
                    char r = buscar(token);
                    if (r == '\0') resultado = resultado + '?';
                    else resultado = resultado + r;
                    token = "";
                } else {
                    // múltiplos espaços -> podemos adicionar espaço na saída para separar palavras
                    // Aqui mantemos um espaço simples entre palavras se houver mais de um espaço
                    // (duplo espaço significa separação de palavras)
                    // Implementação simples: se próximo caractere também é espaço, adiciona espaço na saída
                    // (verifica lookahead)
                    int j = i + 1;
                    if (j < len && mensagemMorse.charAt(j) == ' ') {
                        resultado = resultado + ' ';
                    }
                }
            } else {
                token = token + ch;
            }
        }
        // último token
        if (token.length() > 0) {
            char r = buscar(token);
            if (r == '\0') resultado = resultado + '?';
            else resultado = resultado + r;
        }
        return resultado;
    }

    // Remove o caractere no nó correspondente ao código morse
    // Apenas limpa o valor sem desmontar a estrutura de ramos
    public void remover(String codigo) {
        if (codigo == null) return;
        Nodo atual = raiz;
        int n = codigo.length();
        for (int i = 0; i < n; i++) {
            char s = codigo.charAt(i);
            if (s == '.') {
                if (atual.esquerdo == null) return;
                atual = atual.esquerdo;
            } else if (s == '-') {
                if (atual.direito == null) return;
                atual = atual.direito;
            } else {
                return;
            }
        }
        atual.valor = '\0';
    }

    // Busca o código morse correspondente a um caractere (busca inversa).
    // Retorna a string do código (ex: ".-") ou "" string vazio se não encontrado.
    public String buscarCodigoParaCaracter(char c) {
        // pesquisa recursiva pela árvore
        return buscarCodigoParaCaracterRec(raiz, c, "");
    }

    private String buscarCodigoParaCaracterRec(Nodo n, char c, String caminho) {
        if (n == null) return "";
        if (n.valor == c) {
            return caminho;
        }
        // buscar na esquerda (ponto)
        String res = buscarCodigoParaCaracterRec(n.esquerdo, c, caminho + ".");
        if (res.length() > 0) return res;
        // buscar na direita (traço)
        res = buscarCodigoParaCaracterRec(n.direito, c, caminho + "-");
        return res; // será "" sempre q não encontrado
    }

    // Exibição simples: imprime cada nó com seu caminho (código morse) e o caractere.
    // Exemplo de linha: ".- => A"
    public void exibirTodosCaminhos() {
        System.out.println("Caminhos (codigo -> caractere):");
        exibirTodosCaminhosRec(raiz, "");
    }

    private void exibirTodosCaminhosRec(Nodo n, String caminho) {
        if (n == null) return;
        if (n.valor != '\0') {
            System.out.println(caminho + " => " + n.valor);
        }
        exibirTodosCaminhosRec(n.esquerdo, caminho + ".");
        exibirTodosCaminhosRec(n.direito, caminho + "-");
    }

    // Exibição hierárquica: imprime a estrutura com indentação
    public void exibirHierarquica() {
        System.out.println("Exibição hierárquica (indent = nível):");
        exibirHierarquicaRec(raiz, 0, "raiz");
    }

    private void exibirHierarquicaRec(Nodo n, int nivel, String rotulo) {
        if (n == null) {
            return;
        }
        // criar indent manualmente
        String indent = "";
        for (int i = 0; i < nivel; i++) indent = indent + "  ";

        String valorMostrado = (n.valor == '\0') ? "-" : "" + n.valor;
        System.out.println(indent + rotulo + " : " + valorMostrado);

        if (n.esquerdo != null) exibirHierarquicaRec(n.esquerdo, nivel + 1, "dot(.)");
        if (n.direito != null) exibirHierarquicaRec(n.direito, nivel + 1, "dash(-)");
    }

    // Inicializa a árvore com as letras A-Z e dígitos 0-9 automaticamente
    public void inicializarPadrao() {
        inicializar();
        // Definimos arrays paralelos com códigos e caracteres
        String[] codigos = {
                ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                "..-", "...-", ".--", "-..-", "-.--", "--..",
                ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"
        };
        char[] chars = {
                'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z',
                '1','2','3','4','5','6','7','8','9','0'
        };
        // inserir todos
        int n = codigos.length;
        for (int i = 0; i < n; i++) {
            inserir(codigos[i], chars[i]);
        }
    }

    // Método de teste rápido: popula e faz buscas de exemplo
    public static void main(String[] args) {
        ArvoreBinariaMorse arvore = new ArvoreBinariaMorse();
        arvore.inicializarPadrao();

        System.out.println("Árvore Morse inicializada com A-Z e 0-9.");
        System.out.println();

        // Exemplo de buscas
        System.out.println("Exemplos de busca por código:");
        System.out.println("... -> " + arvore.buscar("...")); // S
        System.out.println("--- -> " + arvore.buscar("---")); // O
        System.out.println("... --- ... -> " + arvore.buscarMensagem("... --- ...")); // SOS
        System.out.println();

        // Exibir todos caminhos
        arvore.exibirTodosCaminhos();
        System.out.println();
        arvore.exibirHierarquica();
        System.out.println();

        // Interação simples via console
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("Opções: 1=buscar codigo, 2=buscar char->codigo, 3=inserir, 4=remover, 5=mostrar caminhos, 6=sair");
            System.out.print("Escolha opção: ");
            String op = sc.nextLine();
            if (op.equals("1")) {
                System.out.print("Digite código morse (use espaço entre letras): ");
                String entrada = sc.nextLine();
                String res = arvore.buscarMensagem(entrada);
                System.out.println("Resultado: " + res);
            } else if (op.equals("2")) {
                System.out.print("Digite caractere (ex: S): ");
                String e = sc.nextLine();
                if (e.length() > 0) {
                    char c = e.charAt(0);
                    String codigo = arvore.buscarCodigoParaCaracter(c);
                    if (codigo.length() == 0) System.out.println("Caractere não encontrado.");
                    else System.out.println("Código de " + c + " = " + codigo);
                }
            } else if (op.equals("3")) {
                System.out.print("Digite código morse (ex: ...): ");
                String cod = sc.nextLine();
                System.out.print("Digite caractere a inserir (ex: @): ");
                String chs = sc.nextLine();
                if (chs.length() > 0) {
                    char c = chs.charAt(0);
                    arvore.inserir(cod, c);
                    System.out.println("Inserido " + c + " em " + cod);
                }
            } else if (op.equals("4")) {
                System.out.print("Digite código morse a remover: ");
                String cod = sc.nextLine();
                arvore.remover(cod);
                System.out.println("Removido valor em " + cod + " (se existia)");
            } else if (op.equals("5")) {
                arvore.exibirTodosCaminhos();
            } else if (op.equals("6")) {
                System.out.println("Saindo.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}
