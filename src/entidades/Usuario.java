package entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private long matricula;
    private String nome;
    private Dinheiro saldo;
    private Grupo grupo;
    private List<Transacao> extrato = new ArrayList<>();


    public Grupo getGrupo() {
        return grupo;
    }

    public Dinheiro getSaldo() {
        return saldo;
    }

    public List<Transacao> getExtrato(int dias) {
        if (dias < 0) return getExtrato();

        LocalDate semanaPassada = LocalDate.now().minusDays(dias);

        List<Transacao> filtrado = new ArrayList<>();
        for (Transacao t : extrato) {
            if (!t.getData().toLocalDate().isBefore(semanaPassada))
                filtrado.add(t);
        }

        return filtrado;
    }

    public List<Transacao> getExtrato() {
        return new ArrayList<>(extrato);
    }

    public void adicionarTransacao(Transacao transacao) {
        this.extrato.add(transacao);
    }

    public Dinheiro setSaldo(Dinheiro saldo) {
        this.saldo = saldo;
        return saldo;
    }
}
