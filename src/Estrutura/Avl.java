package Estrutura;
import entidades.Usuario;

public class Avl {

    private Node raiz;

    // Implementação do Nó, Classe interna pois apenas a AVL utilizará
    private static class Node {
        Usuario usuario;
        int altura;
        Node dir;
        Node esq;

        Node(Usuario usuario) {
            this.usuario = usuario;
            this.altura = 1; // Nó folha começa com altura 1
        }
    }

    public Avl() {
        this.raiz = null;
    }

    // Retorna a altura do nó
    private int altura(Node N) {
        if (N == null)
            return 0;
        return N.altura;
    }

    // Retorna o maior entre dois números
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Retorna o fator de balanceamento do nó
    private int obterBalanceamento(Node N) {
        if (N == null)
            return 0;
        return altura(N.esq) - altura(N.dir);
    }

    // Rotação à direita
    private Node rotacaoDireita(Node y) {
        Node x = y.esq;
        Node T2 = x.dir;

        // Realiza a rotação
        x.dir = y;
        y.esq = T2;

        // Atualiza as alturas
        y.altura = max(altura(y.esq), altura(y.dir)) + 1;
        x.altura = max(altura(x.esq), altura(x.dir)) + 1;

        // Retorna a nova raiz
        return x;
    }

    // Rotação à esquerda
    private Node rotacaoEsquerda(Node x) {
        Node y = x.dir;
        Node T2 = y.esq;

        // Realiza a rotação
        y.esq = x;
        x.dir = T2;

        // Atualiza as alturas
        x.altura = max(altura(x.esq), altura(x.dir)) + 1;
        y.altura = max(altura(y.esq), altura(y.dir)) + 1;

        // Retorna a nova raiz
        return y;
    }

    // Busca um usuário pela matrícula
    public Usuario buscarAvl(long matricula) {
        return buscarRec(matricula, raiz);
    }

    private Usuario buscarRec(long matricula, Node no) {
        // Caso Base 1: Encontrou um Nó Nulo, ou seja, a matrícula não se encontra na árvore
        if (no == null) {
            return null;
        }
        // Caso Base 2: Usuário encontrado
        if (matricula == no.usuario.getMatricula()) {
            return no.usuario;
        }
        // matricula > nó
        if (matricula > no.usuario.getMatricula()) {
            return buscarRec(matricula, no.dir);
        }
        // matricula < nó
        return buscarRec(matricula, no.esq);
    }

    // Insere um usuário na árvore AVL
    public void inserirAvl(Usuario usuario) {
        if (usuario != null) {
            raiz = inserirRec(raiz, usuario);
        }
    }

    private Node inserirRec(Node no, Usuario usuario) {
        // 1. Inserção normal de BST
        if (no == null) {
            return new Node(usuario);
        }

        long matricula = usuario.getMatricula();

        if (matricula < no.usuario.getMatricula()) {
            no.esq = inserirRec(no.esq, usuario);
        } else if (matricula > no.usuario.getMatricula()) {
            no.dir = inserirRec(no.dir, usuario);
        } else {
            // Matrículas iguais não são permitidas, apenas retorna o nó inalterado
            return no;
        }

        // 2. Atualiza a altura do ancestral
        no.altura = 1 + max(altura(no.esq), altura(no.dir));

        // 3. Obtém o fator de balanceamento para verificar se tornou-se desbalanceado
        int balanceamento = obterBalanceamento(no);

        // Se desbalanceado, 4 casos possíveis:

        // Caso Esquerda Esquerda
        if (balanceamento > 1 && matricula < no.esq.usuario.getMatricula()) {
            return rotacaoDireita(no);
        }

        // Caso Direita Direita
        if (balanceamento < -1 && matricula > no.dir.usuario.getMatricula()) {
            return rotacaoEsquerda(no);
        }

        // Caso Esquerda Direita
        if (balanceamento > 1 && matricula > no.esq.usuario.getMatricula()) {
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }

        // Caso Direita Esquerda
        if (balanceamento < -1 && matricula < no.dir.usuario.getMatricula()) {
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }

        // Retorna o ponteiro do nó (inalterado se já estiver balanceado)
        return no;
    }

    // Encontra o nó com o menor valor (para sucessor na remoção)
    private Node noValorMinimo(Node no) {
        Node atual = no;
        // Encontra a folha mais à esquerda
        while (atual.esq != null) {
            atual = atual.esq;
        }
        return atual;
    }

    // Remove um usuário pela matrícula
    public void removerAvl(long matricula) {
        raiz = removerRec(raiz, matricula);
    }

    private Node removerRec(Node raiz, long matricula) {
        // 1. Remoção normal de BST
        if (raiz == null) {
            return raiz;
        }

        // Se a matrícula a ser deletada é menor que a do nó, está à esquerda
        if (matricula < raiz.usuario.getMatricula()) {
            raiz.esq = removerRec(raiz.esq, matricula);
        }
        // Se a matrícula a ser deletada é maior que a do nó, está à direita
        else if (matricula > raiz.usuario.getMatricula()) {
            raiz.dir = removerRec(raiz.dir, matricula);
        }
        // Se a matrícula é a mesma do nó raiz, este é o nó a ser deletado
        else {
            // Nó com apenas um filho ou sem filhos
            if ((raiz.esq == null) || (raiz.dir == null)) {
                Node temp = null;
                if (temp == raiz.esq) {
                    temp = raiz.dir;
                } else {
                    temp = raiz.esq;
                }

                // Sem filhos
                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else {
                    // Um filho
                    raiz = temp; // Copia o filho
                }
            } else {
                // Nó com dois filhos: pega o sucessor inorder (menor da subárvore direita)
                Node temp = noValorMinimo(raiz.dir);

                // Copia os dados do sucessor inorder para este nó
                raiz.usuario = temp.usuario;

                // Remove o sucessor inorder
                raiz.dir = removerRec(raiz.dir, temp.usuario.getMatricula());
            }
        }

        // Se a árvore tinha apenas um nó, então retorne
        if (raiz == null) {
            return raiz;
        }

        // 2. Atualiza a altura do nó atual
        raiz.altura = max(altura(raiz.esq), altura(raiz.dir)) + 1;

        // 3. Obtém o fator de balanceamento deste nó (para verificar se tornou-se desbalanceado)
        int balanceamento = obterBalanceamento(raiz);

        // Se desbalanceado, 4 casos possíveis:

        // Caso Esquerda Esquerda
        if (balanceamento > 1 && obterBalanceamento(raiz.esq) >= 0) {
            return rotacaoDireita(raiz);
        }

        // Caso Esquerda Direita
        if (balanceamento > 1 && obterBalanceamento(raiz.esq) < 0) {
            raiz.esq = rotacaoEsquerda(raiz.esq);
            return rotacaoDireita(raiz);
        }

        // Caso Direita Direita
        if (balanceamento < -1 && obterBalanceamento(raiz.dir) <= 0) {
            return rotacaoEsquerda(raiz);
        }

        // Caso Direita Esquerda
        if (balanceamento < -1 && obterBalanceamento(raiz.dir) > 0) {
            raiz.dir = rotacaoDireita(raiz.dir);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    // Retorna todos os usuários da árvore AVL ordenados pela matrícula (Travessia In-Order)
    public java.util.List<Usuario> obterTodos() {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        obterTodosRec(raiz, lista);
        return lista;
    }

    private void obterTodosRec(Node no, java.util.List<Usuario> lista) {
        if (no != null) {
            obterTodosRec(no.esq, lista);
            lista.add(no.usuario);
            obterTodosRec(no.dir, lista);
        }
    }
}
