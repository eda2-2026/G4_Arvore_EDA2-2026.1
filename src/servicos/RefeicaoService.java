package servicos;

import entidades.Refeicao;
import java.time.LocalTime;
import java.util.Optional;

public class RefeicaoService {

    private static Refeicao refeicaoForcada = null;
    private static boolean ruFechadoForcado = false;

    public static void forcarRefeicao(Refeicao refeicao) {
        ruFechadoForcado = false;
        refeicaoForcada = refeicao;
    }

    public static void limparRefeicaoForcada() {
        ruFechadoForcado = false;
        refeicaoForcada = null;
    }

    public static void forcarFechamento() {
        ruFechadoForcado = true;
        refeicaoForcada = null;
    }

    public static Optional<Refeicao> getRefeicaoAtual() {
        if (ruFechadoForcado) {
            return Optional.empty();
        }

        if (refeicaoForcada != null) {
            return Optional.of(refeicaoForcada);
        }

        LocalTime agora = LocalTime.now();

        for (Refeicao r : Refeicao.values()) {
            if (!agora.isBefore(r.getHorarioInicio()) && !agora.isAfter(r.getHorarioFim())) {
                return Optional.of(r);
            }
        }

        return Optional.empty();
    }
}