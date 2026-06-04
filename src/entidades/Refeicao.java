package entidades;

import java.time.LocalTime;

public enum Refeicao {

    DESJEJUM("Desjejum", LocalTime.of(7,0), LocalTime.of(9,30)),
    ALMOCO("Almoço", LocalTime.of(11,0), LocalTime.of(14,30)),
    JANTAR("Jantar", LocalTime.of(17,0), LocalTime.of(19,30));

    private final String nome;
    private final LocalTime horarioInicio;
    private final LocalTime horarioFim;

    Refeicao(String nome, LocalTime horarioInicio, LocalTime horarioFim) {
        this.nome = nome;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public String getNome() {
        return nome;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }
}