package servicos;

import entidades.Dinheiro;
import entidades.Refeicao;
import entidades.Transacao;
import entidades.Usuario;
import servicos.CadastroService;
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
        if (valor.reais() < 0 || (valor.reais() == 0 && valor.centavos() < 0)) {
            throw new IllegalArgumentException("O valor da recarga não pode ser negativo.");
        }

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
        return CadastroService.buscarUsuario(usuario.getMatricula()) != null;
    }

    private static boolean usuarioPodeComer(Usuario usuario, Refeicao refeicao) {
        LocalDate hoje = LocalDate.now();

        for (Transacao transacao : usuario.getExtrato(0)) {
            // Se houver uma transação de consumo hoje para o MESMO turno, bloqueia
            if (transacao.getData().toLocalDate().equals(hoje) && transacao.getRefeicao() == refeicao) {
                return false;
            }
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
