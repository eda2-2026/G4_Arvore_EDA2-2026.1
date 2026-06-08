package menu;

import entidades.Refeicao;
import servicos.RefeicaoService;

import javax.swing.*;

public class TelaAdmin {

    private TelaAdmin() {}

    static void exibir() {
        while (true) {
            String[] opcoes = {"Gerenciar Alunos", "Forçar Turno", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Painel de Administração do Restaurante:",
                    "⚙️ Modo Administrador",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> TelaCadastro.exibir();
                case 1 -> menuForcarTurno();
                default -> {
                    return;
                }
            }
        }
    }

    private static void menuForcarTurno() {
        String[] opcoes = {"Desjejum", "Almoço", "Jantar", "Fechar RU", "Automático (Relógio)", "Voltar"};
        int escolha = JOptionPane.showOptionDialog(
                MenuUtil.getFrame(),
                "Forçar o sistema para qual refeição?",
                "Controle de Turno",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]
        );

        switch (escolha) {
            case 0 -> {
                RefeicaoService.forcarRefeicao(Refeicao.DESJEJUM);
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Turno forçado para: Desjejum.", "Turno Atualizado", JOptionPane.INFORMATION_MESSAGE);
            }
            case 1 -> {
                RefeicaoService.forcarRefeicao(Refeicao.ALMOCO);
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Turno forçado para: Almoço.", "Turno Atualizado", JOptionPane.INFORMATION_MESSAGE);
            }
            case 2 -> {
                RefeicaoService.forcarRefeicao(Refeicao.JANTAR);
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Turno forçado para: Jantar.", "Turno Atualizado", JOptionPane.INFORMATION_MESSAGE);
            }
            case 3 -> {
                RefeicaoService.forcarFechamento();
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "O RU foi fechado à força.", "Turno Atualizado", JOptionPane.INFORMATION_MESSAGE);
            }
            case 4 -> {
                RefeicaoService.limparRefeicaoForcada();
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "O sistema voltou a utilizar o horário do computador.", "Turno Atualizado", JOptionPane.INFORMATION_MESSAGE);
            }
            default -> {}
        }
    }
}
