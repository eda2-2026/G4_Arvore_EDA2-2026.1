package servicos;

import entidades.Refeicao;
import java.time.LocalTime;
import java.util.Optional;

public class RefeicaoService {

    public static Optional<Refeicao> getRefeicaoAtual() {
        LocalTime agora = LocalTime.now();

        for (Refeicao r : Refeicao.values()) {
            if (!agora.isBefore(r.getHorarioInicio()) && !agora.isAfter(r.getHorarioFim())) {
                return Optional.of(r);
            }
        }

        return Optional.empty();
    }
}