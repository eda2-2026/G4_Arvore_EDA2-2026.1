package entidades;

import java.time.LocalDateTime;

public class Transacao {
    Dinheiro valor;
    LocalDateTime data;
    Refeicao refeicao;

    public Transacao(Dinheiro valor, LocalDateTime data, Refeicao refeicao) {
        this.valor = valor;
        this.data = data;
        this.refeicao = refeicao;
    }

    public Transacao(Dinheiro valor, LocalDateTime data) {
        this.valor = valor;
        this.data = data;
        this.refeicao = null;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Dinheiro getValor() {
        return valor;
    }

    public Refeicao getRefeicao() {
        return refeicao;
    }
}
