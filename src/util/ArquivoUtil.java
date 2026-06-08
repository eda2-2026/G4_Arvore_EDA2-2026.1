package util;

import entidades.Usuario;
import servicos.CadastroService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArquivoUtil {

    private ArquivoUtil() {}

    private static void salvarUsuariosCSV(String caminhoArquivo, List<Usuario> usuarios) {
        
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            // Escreve o cabeçalho sem a coluna de sexo
            writer.write("matricula,nome,reais,centavos,grupo\n");
            
            for (Usuario u : usuarios) {
                writer.write(u.getMatricula() + "," +
                             u.getNome() + "," +
                             u.getSaldo().getReais() + "," +
                             u.getSaldo().getCentavos() + "," +
                             u.getGrupo().name() + "\n");
            }
            
            System.out.println("Banco de dados usuarios.csv salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao tentar salvar o usuarios.csv: " + e.getMessage());
        }
    }

    private static void salvarTransacoesCSV(String caminhoArquivo, List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write("matricula,reais,centavos,dataHora,refeicao\n");

            for (Usuario u : usuarios) {
                for (entidades.Transacao t : u.getExtrato(-1)) { // -1 para pegar todo o extrato
                    String refeicaoNome = (t.getRefeicao() != null) ? t.getRefeicao().name() : "RECARGA";
                    writer.write(u.getMatricula() + "," +
                                 t.getValor().getReais() + "," +
                                 t.getValor().getCentavos() + "," +
                                 t.getData().toString() + "," +
                                 refeicaoNome + "\n");
                }
            }
            System.out.println("Banco de dados transacoes.csv salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao tentar salvar o transacoes.csv: " + e.getMessage());
        }
    }

    public static void salvarTodosOsDados() {
        List<Usuario> usuarios = CadastroService.obterTodosUsuarios();
        salvarUsuariosCSV("usuarios.csv", usuarios);
        salvarTransacoesCSV("transacoes.csv", usuarios);
    }
}
