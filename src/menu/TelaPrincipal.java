package menu;

import javax.swing.*;

public class TelaPrincipal {

    private TelaPrincipal() {}

    public static void iniciar() {
        MenuUtil.inicializarFrame();

        while (true) {
            String[] opcoes = {"Modo Usuário (Totem)", "Modo Administrador (Gerência)", "Sair"};

            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Selecione o módulo de acesso:",
                    "🏫 Sistema do Restaurante Universitário",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> TelaRestaurante.exibir();
                case 1 -> TelaAdmin.exibir();
                default -> {
                    // Salva todos os dados alterados da AVL no CSV antes de fechar!
                    util.ArquivoUtil.salvarTodosOsDados();
                    
                    JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                            "Sistema encerrado. Todos os dados foram salvos!\nAté logo!",
                            "Encerramento", JOptionPane.INFORMATION_MESSAGE);
                    MenuUtil.destruirFrame();
                    return;
                }
            }
        }
    }
}
