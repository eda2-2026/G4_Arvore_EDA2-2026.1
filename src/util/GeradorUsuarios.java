package util;

import entidades.Grupo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeradorUsuarios {

    private static final Random random = new Random();

    private static final String[] nomesMasculinos = {
            "Lucas", "Miguel", "Arthur", "Davi", "Theo", "Gabriel", "Samuel", "Pedro", "João", "Rafael",
            "Mateus", "Enzo", "Bernardo", "Guilherme", "Gustavo", "Felipe", "Nicolas", "Henrique",
            "Murilo", "Eduardo", "Victor", "Cauã", "Antônio", "Vicente", "Daniel", "Thiago", "Caleb",
            "Igor", "Marcelo", "Leonardo", "Bruno", "Diego", "Alexandre", "Caio", "Renato", "Ricardo",
            "Fernando", "Roberto", "André", "Rodrigo", "Julio", "Breno", "Vitor", "Vinicius", "Francisco",
            "Marcos", "Paulo", "Carlos", "Augusto", "Benício", "Catarino", "Erick", "Yuri", "Otávio"
    };

    private static final String[] nomesFemininos = {
            "Maria", "Sophia", "Helena", "Laura", "Alice", "Valentina", "Julia", "Manuela", "Beatriz",
            "Camila", "Isabella", "Mariana", "Lara", "Letícia", "Luiza", "Cecília", "Lívia", "Isadora",
            "Lorena", "Ana", "Clara", "Giovanna", "Yasmin", "Melissa", "Marina", "Rafaela", "Carolina",
            "Gabriela", "Amanda", "Fernanda", "Bruna", "Juliana", "Aline", "Ayla", "Mirella", "Bianca",
            "Natália", "Catarina", "Elisa", "Maitê", "Emanuelly", "Lavínia", "Luna", "Stella",
            "Agatha", "Rebeca", "Talita", "Priscila", "Débora", "Bárbara", "Renata", "Simone", "Márcia"
    };

    private static final String[] sobrenomes = {
            "Silva", "Souza", "Oliveira", "Santos", "Lima", "Ferreira", "Rodrigues", "Almeida", "Cardoso",
            "Barreto", "Freitas", "Costa", "Carvalho", "Martins", "Araújo", "Melo", "Barbosa", "Ribeiro",
            "Alves", "Pinto", "Teixeira", "Cavalcanti", "Dias", "Castro", "Rocha", "Mendes", "Nunes",
            "Peixoto", "Monteiro", "Moura", "Vieira", "Ramos", "Machado", "Borges", "Gomes", "Correia",
            "Macedo", "Cunha", "Moraes", "Guimarães", "Azevedo", "Coelho", "Lopes", "Andrade", "Farias",
            "Batista", "Pacheco", "Xavier", "Franco", "Neves", "Duarte", "Pires", "Reis", "Fonseca"
    };

    private static class UsuarioGerado implements Comparable<UsuarioGerado> {
        long matricula;
        String nome;
        char sexo;
        int saldoReais;
        int saldoCentavos;
        String grupo;

        @Override
        public int compareTo(UsuarioGerado outro) {
            return Long.compare(this.matricula, outro.matricula);
        }
    }

    public static void main(String[] args) {
        int quantidadeGerar = 500;
        List<UsuarioGerado> listaUsuarios = new ArrayList<>();
        Set<Long> matriculasUsadas = new HashSet<>();

        System.out.println("Gerando " + quantidadeGerar + " usuários com matrículas únicas...");

        for (int i = 0; i < quantidadeGerar; i++) {
            UsuarioGerado u = new UsuarioGerado();
            
            u.sexo = random.nextBoolean() ? 'M' : 'F';
            u.nome = gerarNome(u.sexo);
            
            // Garantir matrícula única
            do {
                u.matricula = gerarMatriculaValida();
            } while (matriculasUsadas.contains(u.matricula));
            matriculasUsadas.add(u.matricula);

            // Gerar Grupo Aleatório
            Grupo[] grupos = Grupo.values();
            u.grupo = grupos[random.nextInt(grupos.length)].name();

            // Saldo realista com base no grupo
            if (u.grupo.equals("G1")) {
                // Estudantes isentos DEVEM ESTAR SEMPRE com a carteirinha zerada para evitar bugs
                u.saldoReais = 0;
                u.saldoCentavos = 0;
            } else {
                u.saldoReais = random.nextInt(50); // de R$ 0 a R$ 49
                u.saldoCentavos = random.nextInt(100); // de 0 a 99 centavos
            }

            listaUsuarios.add(u);
        }

        Collections.sort(listaUsuarios);

        try (FileWriter writer = new FileWriter("usuarios.csv")) {
            writer.write("matricula,nome,reais,centavos,grupo\n");
            for (UsuarioGerado u : listaUsuarios) {
                writer.write(u.matricula + "," + u.nome + "," + u.saldoReais + "," + u.saldoCentavos + "," + u.grupo + "\n");
            }
            System.out.println("✅ Arquivo usuarios.csv gerado e ordenado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo: " + e.getMessage());
        }

        // Limpar transacoes antigas já que os usuários foram resetados
        java.io.File arquivoTransacoes = new java.io.File("transacoes.csv");
        if (arquivoTransacoes.exists()) {
            if (arquivoTransacoes.delete()) {
                System.out.println("Banco de transacoes.csv antigo apagado para evitar inconsistências!");
            } else {
                System.err.println("Não foi possível apagar o arquivo transacoes.csv antigo.");
            }
        }
    }

    private static String gerarNome(char sexo) {
        String primeiroNome = (sexo == 'M')
                ? nomesMasculinos[random.nextInt(nomesMasculinos.length)]
                : nomesFemininos[random.nextInt(nomesFemininos.length)];

        String sobrenome1 = sobrenomes[random.nextInt(sobrenomes.length)];
        String sobrenome2;
        do {
            sobrenome2 = sobrenomes[random.nextInt(sobrenomes.length)];
        } while (sobrenome1.equals(sobrenome2));

        return primeiroNome + " " + sobrenome1 + " " + sobrenome2;
    }

    private static long gerarMatriculaValida() {
        // Exemplo de matrícula no formato clássico da UnB (Ex: 211001234)
        // Começando de 15 (ano 2015) a 26 (ano 2026)
        int anoGeracao = 15 + random.nextInt(12);
        int semestre = random.nextBoolean() ? 1 : 2;
        int sufixo = random.nextInt(100000); // 5 dígitos
        
        String matriculaStr = String.format("%d%d%05d", anoGeracao, semestre, sufixo);
        return Long.parseLong(matriculaStr);
    }
}
