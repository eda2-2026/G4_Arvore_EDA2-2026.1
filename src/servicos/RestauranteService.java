package servicos;

import entidades.Dinheiro;
import entidades.Refeicao;
import entidades.Transacao;
import entidades.Usuario;
import excecoes.RestauranteFechadoException;
import excecoes.SaldoInsuficienteException;
import excecoes.UsuarioJaComeuException;
import excecoes.UsuarioNaoCadastradoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class RestauranteService {
    public static Dinheiro consumir(Usuario usuario) {
        Optional<Refeicao> refeicaoAtual = RefeicaoService.getRefeicaoAtual();
        if (refeicaoAtual.isEmpty())
            throw new RestauranteFechadoException();
        Refeicao refeicao = refeicaoAtual.get();

        if (!usuarioCadastrado(usuario))
            throw new UsuarioNaoCadastradoException();
        if (!usuarioPodeComer(usuario, refeicao))
            throw new UsuarioJaComeuException();

        Dinheiro preco = obterPreco(usuario, refeicao);
        if (!saldoSuficiente(usuario, preco))
            throw new SaldoInsuficienteException();

        return subtrairFundos(usuario, preco, refeicao);
    }

    public static Dinheiro adicionarFundos(Usuario usuario, Dinheiro valor) {
        if (!usuarioCadastrado(usuario))
            throw new UsuarioNaoCadastradoException();

        Dinheiro saldo = usuario.getSaldo().somar(valor);
        usuario.adicionarTransacao(new Transacao(valor, LocalDateTime.now()));
        return usuario.setSaldo(saldo);
    }

    private static Dinheiro subtrairFundos(Usuario usuario, Dinheiro valor, Refeicao refeicao) {
        Dinheiro saldo = usuario.getSaldo().subtrair(valor);
        usuario.adicionarTransacao(new Transacao(valor, LocalDateTime.now(), refeicao));
        return usuario.setSaldo(saldo);
    }

    private static boolean usuarioCadastrado(Usuario usuario) {
        return true; //TODO
    }

    private static boolean usuarioPodeComer(Usuario usuario, Refeicao refeicao) {
        LocalDate hoje = LocalDate.now();

        LocalDateTime inicioTurnoHoje = hoje.atTime(refeicao.getHorarioInicio());
        LocalDateTime fimTurnoHoje = hoje.atTime(refeicao.getHorarioFim());

        for (Transacao transacao : usuario.getExtrato(0)) {
            LocalDateTime dataTransacao = transacao.getData();

            if (!dataTransacao.isBefore(inicioTurnoHoje) && !dataTransacao.isAfter(fimTurnoHoje))
                return false;
        }

        return true;
    }

    private static Dinheiro obterPreco(Usuario usuario, Refeicao refeicao) {
        if (refeicao == Refeicao.DESJEJUM)
            return usuario.getGrupo().getPrecoDesjejum();
        else
            return usuario.getGrupo().getPrecoAlmocoJantar(); // Almoço ou Jantar
    }

    private static boolean saldoSuficiente(Usuario usuario, Dinheiro preco) {
        return (usuario.getSaldo().maiorIgual(preco));
    }
}
