package menu;

import entidades.Dinheiro;
import entidades.Grupo;
import entidades.Usuario;
import servicos.CadastroService;
import excecoes.UsuarioJaCadastradoException;
import excecoes.UsuarioNaoCadastradoException;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro {

    private TelaCadastro() {
    }

    static void exibir() {
        while (true) {
            String[] opcoes = {"Cadastrar Usuário", "Remover Usuário", "Buscar Usuário", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Módulo de Cadastro (Adição e Remoção):",
                    "📋 Cadastro de Usuários",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> cadastrarUsuario();
                case 1 -> removerUsuario();
                case 2 -> buscarUsuario();
                default -> {
                    return;
                }
            }
        }
    }

    private static void cadastrarUsuario() {
        JTextField campoMatricula = new JTextField(15);
        JTextField campoNome = new JTextField(25);
        JComboBox<Grupo> comboGrupo = new JComboBox<>(Grupo.values());
        JTextField campoReais = new JTextField("0", 5);
        JTextField campoCentavos = new JTextField("0", 5);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        painel.add(campoMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painel.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Grupo:"), gbc);
        gbc.gridx = 1;
        painel.add(comboGrupo, gbc);

        JPanel painelSaldo = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        painelSaldo.add(new JLabel("R$"));
        painelSaldo.add(campoReais);
        painelSaldo.add(new JLabel(","));
        painelSaldo.add(campoCentavos);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Saldo Inicial:"), gbc);
        gbc.gridx = 1;
        painel.add(painelSaldo, gbc);

        while (true) {
            String[] opcoesPainel = {"Confirmar", "Cancelar"};
            int result = JOptionPane.showOptionDialog(MenuUtil.getFrame(), painel, "Cadastrar Novo Usuário",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opcoesPainel, opcoesPainel[0]);
            
            if (result != 0) return; // Cancelou

            try {
                long matricula = Long.parseLong(campoMatricula.getText().replaceAll("[^0-9]", ""));
                String nome = campoNome.getText().trim();
                Grupo grupo = (Grupo) comboGrupo.getSelectedItem();
                int reais = Integer.parseInt(campoReais.getText().trim());
                int centavos = Integer.parseInt(campoCentavos.getText().trim());

                if (nome.isBlank()) {
                    JOptionPane.showMessageDialog(MenuUtil.getFrame(), "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Usuario novoUser = new Usuario(matricula, nome, new Dinheiro(reais, centavos), grupo);
                CadastroService.cadastrarUsuario(novoUser);

                JOptionPane.showMessageDialog(MenuUtil.getFrame(), 
                        "Usuário cadastrado com sucesso na Árvore AVL!\n\nNome: " + nome + "\nMatrícula: " + matricula,
                        "✅ Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Verifique se a matrícula e os saldos são números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (UsuarioJaCadastradoException e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), e.getMessage(), "Usuário Existente", JOptionPane.WARNING_MESSAGE);
                break;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void removerUsuario() {
        long matricula = MenuUtil.pedirMatricula("Digite a matrícula do usuário para remover:");
        if (matricula == -1) return;

        int confirmar = JOptionPane.showConfirmDialog(MenuUtil.getFrame(), 
            "Tem certeza que deseja remover permanentemente o usuário de matrícula " + matricula + "?", 
            "Confirmar Remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                CadastroService.removerUsuario(matricula);
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Usuário removido da Árvore AVL.", "✅ Remoção Concluída", JOptionPane.INFORMATION_MESSAGE);
            } catch (UsuarioNaoCadastradoException e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void buscarUsuario() {
        long matricula = MenuUtil.pedirMatricula("Digite a matrícula para buscar na AVL:");
        if (matricula == -1) return;

        Usuario u = CadastroService.buscarUsuario(matricula);
        if (u == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), "Nenhum usuário encontrado com a matrícula: " + matricula, "Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String info = "<html><body style='width: 250px;'>" +
                          "<b>Usuário Encontrado!</b><br><br>" +
                          "<b>Nome:</b> " + u.getNome() + "<br>" +
                          "<b>Matrícula:</b> " + u.getMatricula() + "<br>" +
                          "<b>Grupo:</b> " + u.getGrupo().getNome() + " - " + u.getGrupo().getDescricao() + "<br><br>" +
                          "<b>Saldo Atual:</b> " + u.getSaldo() + "</body></html>";
            JOptionPane.showMessageDialog(MenuUtil.getFrame(), info, "🔍 Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
