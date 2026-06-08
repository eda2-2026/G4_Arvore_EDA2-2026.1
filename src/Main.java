import menu.TelaPrincipal;
import servicos.CadastroService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entidades.Dinheiro;
import entidades.Grupo;
import entidades.Usuario;

public class Main {
    public static void main(String[] args) {
        // Carrega o banco de dados inicial silenciosamente
        carregarUsuariosDoCSV("usuarios.csv");
        carregarTransacoesDoCSV("transacoes.csv");
        
        // Inicia a Interface Gráfica
        TelaPrincipal.iniciar();
    }

    private static void carregarUsuariosDoCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); // Pula o cabeçalho
            if (linha == null) return;
            
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if(dados.length < 5) continue; 
                
                long matricula = Long.parseLong(dados[0]);
                String nome = dados[1];
                int reais = Integer.parseInt(dados[2]);
                int centavos = Integer.parseInt(dados[3]);
                Grupo grupo = Grupo.valueOf(dados[4]);

                Usuario u = new Usuario(matricula, nome, new Dinheiro(reais, centavos), grupo);
                CadastroService.cadastrarUsuario(u);
            }
        } catch (IOException e) {
            System.err.println("Arquivo CSV não encontrado. O sistema iniciará com a árvore vazia.");
        } catch (Exception e) {
            System.err.println("Erro ao processar arquivo CSV: " + e.getMessage());
        }
    }

    private static void carregarTransacoesDoCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); // Pula o cabeçalho
            if (linha == null) return;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length < 5) continue;

                long matricula = Long.parseLong(dados[0]);
                int reais = Integer.parseInt(dados[1]);
                int centavos = Integer.parseInt(dados[2]);
                java.time.LocalDateTime dataHora = java.time.LocalDateTime.parse(dados[3]);
                entidades.Refeicao refeicao = dados[4].equals("RECARGA") ? null : entidades.Refeicao.valueOf(dados[4]);

                Usuario u = CadastroService.buscarUsuario(matricula);
                if (u != null) {
                    entidades.Transacao t = new entidades.Transacao(new Dinheiro(reais, centavos), dataHora, refeicao);
                    u.adicionarTransacao(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo transacoes.csv não encontrado. Nenhum extrato prévio carregado.");
        } catch (Exception e) {
            System.err.println("Erro ao processar arquivo transacoes.csv: " + e.getMessage());
        }
    }
}
