package menu;

import entidades.Dinheiro;
import entidades.Transacao;
import entidades.Usuario;
import servicos.CadastroService;
import servicos.RestauranteService;
import servicos.RefeicaoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRestaurante {

    private TelaRestaurante() {}

    static void exibir() {
        while (true) {
            String refeicaoAtual = RefeicaoService.getRefeicaoAtual().map(r -> r.getNome()).orElse("FECHADO");

            String[] opcoes = {"Passar na Catraca (Consumir)", "Recarregar Carteira", "Ver Extrato", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Terminal de Autoatendimento do Aluno\n\nTurno atual: " + refeicaoAtual + "\n\nEscolha uma operação:",
                    "💳 Totem do RU",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> simularCatraca();
                case 1 -> recarregarCarteira();
                case 2 -> verExtrato();
                default -> {
                    return;
                }
            }
        }
    }

    private static void simularCatraca() {
        long matricula = MenuUtil.pedirMatricula("--- SIMULAÇÃO DE CATRACA ---\nPasse o cartão (Digite a matrícula):");
        if (matricula == -1) return;

        Usuario u = CadastroService.buscarUsuario(matricula);
        if (u == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "❌ ACESSO NEGADO: Usuário não cadastrado.", "Catraca", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            RestauranteService.consumir(u);
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), 
                "✅ ACESSO LIBERADO!\n\nUsuário: " + u.getNome() + "\nSaldo Atualizado: " + u.getSaldo() + "\nBom apetite!", 
                "Catraca", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "❌ ACESSO NEGADO:\n\n" + e.getMessage(), "Catraca Bloqueada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void recarregarCarteira() {
        long matricula = MenuUtil.pedirMatricula("Digite a matrícula do usuário para recarga:");
        if (matricula == -1) return;

        Usuario u = CadastroService.buscarUsuario(matricula);
        if (u == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField campoReais = new JTextField("0", 5);
        JTextField campoCentavos = new JTextField("0", 5);

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.add(new JLabel("Valor a recarregar: R$"));
        painel.add(campoReais);
        painel.add(new JLabel(","));
        painel.add(campoCentavos);

        int result = JOptionPane.showOptionDialog(MenuUtil.getFrame(), painel, "Recarga - " + u.getNome(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int reais = Integer.parseInt(campoReais.getText().trim());
                int centavos = Integer.parseInt(campoCentavos.getText().trim());
                Dinheiro valor = new Dinheiro(reais, centavos);

                RestauranteService.adicionarFundos(u, valor);
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Recarga de " + valor + " realizada!\nNovo Saldo: " + u.getSaldo(), "✅ Recarga Concluída", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void verExtrato() {
        long matricula = MenuUtil.pedirMatricula("Digite a matrícula para ver o extrato:");
        if (matricula == -1) return;

        Usuario u = CadastroService.buscarUsuario(matricula);
        if (u == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Transacao> extrato = u.getExtrato();
        if (extrato.isEmpty()) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "O usuário " + u.getNome() + " ainda não possui transações.", "Extrato Vazio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Extrato do Usuário: ").append(u.getNome()).append("\n");
        sb.append("Matrícula: ").append(u.getMatricula()).append("\n");
        sb.append("Saldo Atual: ").append(u.getSaldo()).append("\n");
        sb.append("====================================================\n\n");

        for (Transacao t : extrato) {
            sb.append("Data: ").append(t.getData().toString().substring(0, 16).replace("T", " "));
            sb.append(" | Valor: ").append(t.getValor());
            if (t.getRefeicao() != null) {
                sb.append(" | Consumo: ").append(t.getRefeicao().getNome());
            } else {
                sb.append(" | Recarga");
            }
            sb.append("\n");
        }

        MenuUtil.exibirTextoRolavel(sb.toString(), "📄 Extrato");
    }
}
